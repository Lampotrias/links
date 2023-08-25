package com.lampotrias.links.domain.model

data class LinkRemoteModel(
	val title: String,
	val description: String,
	val url: String,
	val imageUrl: String,
)

fun LinkRemoteModel.asDomainModel(): LinkModel {
	return LinkModel (
		id = 0L,
		title = title,
		description = description,
		imageUrl = imageUrl,
		dateCreate = System.currentTimeMillis(),
		url = url,
	)
}