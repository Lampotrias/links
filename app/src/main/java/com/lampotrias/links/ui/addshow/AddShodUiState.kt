package com.lampotrias.links.ui.addshow

import com.lampotrias.links.utils.OneShotEvent

data class AddShodUiState(
	val showLoading: Boolean = false,
	val titleLink: String = "",
	val descriptionLink: String = "",
	val imageUrlLink: String = "",
	val urlLink: String = "",
	val error: OneShotEvent<Throwable>? = null,
	val success: OneShotEvent<Long>? = null
)
