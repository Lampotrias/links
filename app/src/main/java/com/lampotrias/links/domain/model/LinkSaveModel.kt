package com.lampotrias.links.domain.model

import com.lampotrias.links.data.db.link.LinkDatabaseModel

data class LinkSaveModel(
	val id: Long = 0,
	val dateCreate: Long = System.currentTimeMillis(),
	val title: String,
	val description: String,
	val url: String,
	val folderId: Long,
	val imageUrl: String,
	val isFavorite: Boolean = false
)

fun LinkSaveModel.asDatabaseModel(): LinkDatabaseModel {
	return LinkDatabaseModel(
		id = id,
		dateCreate = dateCreate,
		title = title,
		description = description,
		url = url,
		folderId = folderId,
		imageUrl = imageUrl
	)
}