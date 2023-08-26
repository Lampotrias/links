package com.lampotrias.links

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class LinksApplication: Application() {
	override fun onCreate() {
		super.onCreate()

		Fresco.initialize(this)

		Timber.plant(Timber.DebugTree())
	}
}