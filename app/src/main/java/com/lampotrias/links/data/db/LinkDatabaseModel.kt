package com.lampotrias.links.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "links")
data class LinkDatabaseModel (
	@PrimaryKey val id: Long,
	val dateCreate: Long,
	val title: String,
	val description: String,
	val url: String,
	val imageUrl: String,
)