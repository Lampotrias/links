package com.lampotrias.links.ui.addedit

import com.lampotrias.links.utils.OneShotEvent

data class AddEditUiState(
	val showLoading: Boolean = false,
	val titleLink: String = "",
	val descriptionLink: String = "",
	val imageUrlLink: String = "",
	val urlLink: String = "",
	val error: OneShotEvent<Throwable>? = null,
	val success: OneShotEvent<Long>? = null
)
