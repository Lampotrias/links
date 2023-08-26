package com.lampotrias.links.utils.ext

import android.content.Context
import android.util.DisplayMetrics

fun Context.dpToPx(number: Float): Float =
	number * resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT