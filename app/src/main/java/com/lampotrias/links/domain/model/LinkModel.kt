package com.lampotrias.links.domain.model

import android.os.Parcelable
import com.lampotrias.links.data.db.link.LinkDatabaseModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class LinkModel(
	val id: Long = 0,
	val dateCreate: Long = System.currentTimeMillis(),
	val title: String,
	val description: String,
	val url: String,
	val folderId: Long,
	val folderName: String,
	val imageUrl: String,
	val isFavorite: Boolean = false,
) : Parcelable

fun LinkModel.asDatabaseModel(): LinkDatabaseModel {
	return LinkDatabaseModel(
		id = id,
		dateCreate = dateCreate,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		folderId = folderId,
	)
}