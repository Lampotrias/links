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

	@Query("UPDATE links SET markDeleted = 1 WHERE id = :linkId")
	fun deleteLink(linkId: Long)

	@Query("UPDATE links SET markDeleted = 0 WHERE id = :linkId")
	fun restoreLink(linkId: Long)

	@Query("SELECT * FROM links WHERE markDeleted = 0 ORDER BY dateCreate DESC")
	fun getAllLinks(): Flow<List<LinkDatabaseModel>>

	@Query("SELECT * FROM links WHERE markDeleted = 0 AND isFavorite = 1 ORDER BY dateCreate DESC")
	fun getFavoritesLinks(): Flow<List<LinkDatabaseModel>>

	@Update
	fun updateLink(linkDatabaseModel: LinkDatabaseModel)

	@Query("UPDATE links SET `isFavorite` =  NOT `isFavorite` WHERE id = :linkId")
	fun updateFavorite(linkId: Long)
}