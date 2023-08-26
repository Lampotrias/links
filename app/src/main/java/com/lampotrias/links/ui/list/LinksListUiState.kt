package com.lampotrias.links.ui.list

import com.lampotrias.links.domain.model.LinkModel

data class LinksListUiState(
	val links: List<LinkModel> = emptyList()
)
