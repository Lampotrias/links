package com.lampotrias.links.domain.model

import com.lampotrias.links.data.db.LinkDatabaseModel

data class LinkModel(
	val id: Long,
	val dateCreate: Long,
	val title: String,
	val description: String,
	val url: String,
	val imageUrl: String,
)

fun LinkModel.asDatabaseModel(): LinkDatabaseModel {
	return LinkDatabaseModel(
		id = id,
		dateCreate = dateCreate,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl
	)
}