package com.lampotrias.links.data.db.folder

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lampotrias.links.domain.model.FolderModel

@Entity("folders")
data class FolderDatabaseModel(
	@PrimaryKey(autoGenerate = true) val id: Long,
	val name: String
)

fun FolderDatabaseModel.asDomainModel(): FolderModel {
	return FolderModel(
		id = id,
		name = name
	)
}