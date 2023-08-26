package com.lampotrias.links.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LinksDao {
	@Upsert
	fun addLink(linkDatabaseModel: LinkDatabaseModel): Long
	@Query("DELETE FROM links WHERE id = :linkId")
	fun deleteLink(linkId: Long)
	@Query("SELECT * FROM links ORDER BY dateCreate DESC")
	fun getLinks(): Flow<List<LinkDatabaseModel>>
	@Update
	fun updateLink(linkDatabaseModel: LinkDatabaseModel)
}