package com.lampotrias.links.ui.settings

import androidx.lifecycle.ViewModel
import com.lampotrias.links.domain.cases.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val preferencesUseCase: PreferencesUseCase,
) : ViewModel() {

	suspend fun getCurrentColorTheme(): AppThemeColor {
		return preferencesUseCase.getCurrentColorTheme()
	}

	fun saveColorTheme(appThemeColor: AppThemeColor) {
		preferencesUseCase.saveColorTheme(appThemeColor)
	}
}