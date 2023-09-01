package com.lampotrias.links.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lampotrias.links.domain.model.LinkModel

@Entity(tableName = "links")
data class LinkDatabaseModel(
	@PrimaryKey(autoGenerate = true) val id: Long,
	val dateCreate: Long,
	val title: String,
	val description: String,
	val url: String,
	val imageUrl: String,
	val markDeleted: Boolean = false,
	val isFavorite: Boolean = false,
)

fun LinkDatabaseModel.asDomainModel(): LinkModel {
	return LinkModel(
		id = id,
		dateCreate = dateCreate,
		title = title,
		description = description,
		url = url,
		imageUrl = imageUrl,
		isFavorite = isFavorite,
	)
}