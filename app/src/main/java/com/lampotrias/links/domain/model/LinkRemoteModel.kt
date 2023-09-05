package com.lampotrias.links.domain.model

data class LinkRemoteModel(
	val title: String,
	val description: String,
	val url: String,
	val imageUrl: String,
)

fun LinkRemoteModel.asSaveModel(): LinkSaveModel {
	return LinkSaveModel(
		id = 0L,
		title = title,
		description = description,
		imageUrl = imageUrl,
		dateCreate = System.currentTimeMillis(),
		url = url,
		isFavorite = false,
		folderId = 1, // TODO FIX IT
	)
}