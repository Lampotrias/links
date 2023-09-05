package com.lampotrias.links.data.db.link

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "links")
data class LinkDatabaseModel(
	@PrimaryKey(autoGenerate = true) val id: Long,
	val dateCreate: Long,
	val title: String,
	val description: String,
	val url: String,
	@ColumnInfo(name = "folder_id")
	val folderId: Long,
	val imageUrl: String,
	val markDeleted: Boolean = false,
	val isFavorite: Boolean = false,
)