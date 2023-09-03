package com.lampotrias.links.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.links.domain.cases.ExportUseCase
import com.lampotrias.links.domain.cases.PreferencesUseCase
import com.lampotrias.links.utils.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val preferencesUseCase: PreferencesUseCase,
	private val exportUseCase: ExportUseCase,
) : ViewModel() {

	private val _uiState = MutableStateFlow(SettingsUiState())
	val uiState = _uiState.asStateFlow()

	suspend fun getCurrentColorTheme(): AppThemeColor {
		return preferencesUseCase.getCurrentColorTheme()
	}

	fun saveColorTheme(appThemeColor: AppThemeColor) {
		preferencesUseCase.saveColorTheme(appThemeColor)
	}

	fun exportJson() {
		viewModelScope.launch {
			exportUseCase.exportJson().fold(
				{ file ->
					_uiState.update {
						it.copy(
							successJson = OneShotEvent(file)
						)
					}
				},
				{ throwable ->
					_uiState.update {
						it.copy(
							errorJson = OneShotEvent(throwable)
						)
					}
				}
			)
		}
	}

	fun exportCsv() {
		viewModelScope.launch {
			exportUseCase.exportCsv().fold(
				{ file ->
					_uiState.update {
						it.copy(
							successCsv = OneShotEvent(file)
						)
					}
				},
				{ throwable ->
					_uiState.update {
						it.copy(
							errorCsv = OneShotEvent(throwable)
						)
					}
				}
			)
		}
	}
}