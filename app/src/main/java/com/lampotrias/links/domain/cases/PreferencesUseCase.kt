package com.lampotrias.links.domain.cases

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.ui.settings.AppThemeColor
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesUseCase @Inject constructor(
	private val context: Context,
	private val dispatcherProvider: DispatcherProvider,
) {

	private val sharedPreferences: SharedPreferences by lazy {
		context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
	}

	suspend fun getCurrentColorTheme(): AppThemeColor {
		return withContext(dispatcherProvider.io) {
			AppThemeColor.from(sharedPreferences.getString(KEY_COLOR_THEME, ""))
		}
	}

	fun saveColorTheme(appThemeColor: AppThemeColor) {
		sharedPreferences.edit { putString(KEY_COLOR_THEME, appThemeColor.title) }
	}

	companion object {
		private const val STORAGE_NAME = "app"

		private const val KEY_COLOR_THEME = "theme"
	}
}