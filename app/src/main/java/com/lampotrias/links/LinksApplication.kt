package com.lampotrias.links

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.facebook.drawee.backends.pipeline.Fresco
import com.lampotrias.links.domain.cases.PreferencesUseCase
import com.lampotrias.links.ui.settings.AppThemeColor
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class LinksApplication : Application(), Configuration.Provider {
	@Inject
	lateinit var workerFactory: HiltWorkerFactory

	@Inject
	lateinit var preferencesUseCase: PreferencesUseCase

	override fun onCreate() {
		super.onCreate()

		Fresco.initialize(this)
		applyAppTheme()
		Timber.plant(Timber.DebugTree())
	}

	override fun getWorkManagerConfiguration(): Configuration {
		return Configuration.Builder()
			.setWorkerFactory(workerFactory)
			.build()
	}

	@OptIn(DelicateCoroutinesApi::class)
	private fun applyAppTheme() {
		GlobalScope.launch {
			when (preferencesUseCase.getCurrentColorTheme()) {
				AppThemeColor.System -> {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
				}

				AppThemeColor.Dark -> {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				}

				AppThemeColor.Light -> {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
				}
			}
		}
	}
}