package com.lampotrias.links.ui.addshow

import com.lampotrias.links.domain.model.FolderModel
import com.lampotrias.links.utils.OneShotEvent

data class AddShodUiState(
	val showLoading: Boolean = false,
	val titleLink: String = "",
	val descriptionLink: String = "",
	val imageUrlLink: String = "",
	val urlLink: String = "",
	val folderModel: FolderModel? = null,
	val error: OneShotEvent<Throwable>? = null,
	val success: OneShotEvent<Long>? = null
)
