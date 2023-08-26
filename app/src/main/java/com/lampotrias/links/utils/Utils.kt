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
}