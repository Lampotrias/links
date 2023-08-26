package com.lampotrias.links.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri


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
}