package com.lampotrias.links.data.db.folder

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FoldersDao {
	@Query("SELECT * FROM folders ORDER BY ID ASC")
	fun getFolders(): Flow<List<FolderDatabaseModel>>

	@Upsert
	fun insertFolder(folderDatabaseModel: FolderDatabaseModel): Long
}