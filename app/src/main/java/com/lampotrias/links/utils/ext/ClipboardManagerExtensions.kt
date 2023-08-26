package com.lampotrias.links.utils.ext

import android.content.ClipDescription
import android.content.ClipboardManager

fun ClipboardManager.getPlainText(): String? {
	if (hasPrimaryClip()
			&& primaryClip != null
			&& primaryClipDescription != null
			&& primaryClipDescription!!.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
		return primaryClip?.getItemAt(0)?.text?.toString()
	}

	return null
}