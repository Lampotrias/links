package com.lampotrias.links.data.workmanager

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.lampotrias.links.R

object WorkManagerNotification {
	fun create(context: Context, notificationTitle: String): Notification {
		val channelId = context.getString(R.string.notification_channel_id)
		val channelName = context.getString(R.string.channel_name)

		val builder = NotificationCompat.Builder(context, channelId)
			.setContentTitle(notificationTitle)
			.setTicker(notificationTitle)
			.setSmallIcon(R.drawable.ic_launcher_foreground)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			createNotificationChannel(context, channelId, channelName).also {
				builder.setChannelId(it.id)
			}
		}
		return builder.build()
	}

	/**
	 * Create the required notification channel for O+ devices.
	 */
	@TargetApi(Build.VERSION_CODES.O)
	fun createNotificationChannel(
		context: Context,
		channelId: String,
		name: String,
		notificationImportance: Int = NotificationManager.IMPORTANCE_HIGH
	): NotificationChannel {
		val notificationManager =
			context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		return NotificationChannel(channelId, name, notificationImportance).also { channel ->
			notificationManager.createNotificationChannel(channel)
		}
	}
}