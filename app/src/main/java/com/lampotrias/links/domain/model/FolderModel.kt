package com.lampotrias.links.domain.model

import com.lampotrias.links.data.db.folder.FolderDatabaseModel

data class FolderModel(
	val id: Long = 0,
	val name: String,
)

fun FolderModel.asDatabaseModel(): FolderDatabaseModel {
	return FolderDatabaseModel(
		id = id,
		name = name
	)
}