package com.lampotrias.links.utils

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File
import java.util.Locale


object Utils {
	fun openUrl(activity: Activity, url: String) {
		val intent = Intent(Intent.ACTION_VIEW)
		intent.data = Uri.parse(url)
		activity.startActivity(intent)
	}

	fun shareUrl(activity: Activity, url: String) {
		val sharingIntent = Intent(Intent.ACTION_SEND)
		sharingIntent.type = "text/plain"
		sharingIntent.putExtra(Intent.EXTRA_TEXT, url)
		activity.startActivity(Intent.createChooser(sharingIntent, "adadasd"))
	}

	fun systemSendFile(activity: Activity, file: File) {
		systemSendFiles(activity, listOf(file))
	}

	private fun systemSendFiles(activity: Activity, files: List<File>) {
		val intent = createSendFilesIntent(activity, files)

		if (intent.resolveActivity(activity.packageManager) != null) {
			activity.startActivity(
				Intent.createChooser(intent, null)
			)
		}
	}

	private fun createSendFilesIntent(context: Context, files: List<File>): Intent {
		val intent = Intent()
		intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
		if (files.size > 1) {
			intent.action = Intent.ACTION_SEND_MULTIPLE
			intent.type = "*/*"
			var clipData: ClipData? = null
			for (i in files.indices) {
				val fileUri = getExportedUriFromFile(context, files[i])
				if (i == 0) {
					clipData = ClipData.newRawUri(null, fileUri)
				} else {
					clipData?.addItem(ClipData.Item(fileUri))
				}
			}
			intent.clipData = clipData
		} else if (files.size == 1) {
			intent.action = Intent.ACTION_SEND
			val file = files[0]
			val mimeTypeMap = MimeTypeMap.getSingleton()
			val extension = file.extension
			val mimeType =
				mimeTypeMap.getMimeTypeFromExtension(extension.lowercase(Locale.getDefault()))
			val fileUri = getExportedUriFromFile(context, file)
			intent.setDataAndType(fileUri, "*/*")
			intent.putExtra(Intent.EXTRA_STREAM, fileUri)
		}
		return intent
	}

	private fun getExportedUriFromFile(context: Context, file: File): Uri {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			FileProvider.getUriForFile(
				context,
				context.applicationContext.packageName + ".provider",
				file
			)
		} else {
			Uri.fromFile(file)
		}
	}

}