package com.lampotrias.links.ui.settings

import com.lampotrias.links.utils.OneShotEvent
import java.io.File

data class SettingsUiState (
	val successCsv: OneShotEvent<File>? = null,
	val errorCsv: OneShotEvent<Throwable>? = null,

	val successJson: OneShotEvent<File>? = null,
	val errorJson: OneShotEvent<Throwable>? = null
)