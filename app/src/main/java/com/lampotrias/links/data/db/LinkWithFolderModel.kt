package com.lampotrias.links.data.db

import androidx.room.Embedded
import androidx.room.Relation
import com.lampotrias.links.data.db.folder.FolderDatabaseModel
import com.lampotrias.links.data.db.link.LinkDatabaseModel
import com.lampotrias.links.domain.model.LinkModel

data class LinkWithFolderModel(
	@Embedded val link: LinkDatabaseModel,
	@Relation(
		parentColumn = "folder_id",
		entityColumn = "id"
	)
	val folder: FolderDatabaseModel
)

fun LinkWithFolderModel.asDomainModel(): LinkModel {
	return LinkModel(
		id = link.id,
		dateCreate = link.dateCreate,
		title = link.title,
		description = link.description,
		url = link.url,
		imageUrl = link.imageUrl,
		isFavorite = link.isFavorite,
		folderId = folder.id,
		folderName = folder.name
	)
}