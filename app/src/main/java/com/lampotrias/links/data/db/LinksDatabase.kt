package com.lampotrias.links.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lampotrias.links.data.db.folder.FoldersDao
import com.lampotrias.links.data.db.folder.FolderDatabaseModel
import com.lampotrias.links.data.db.link.LinkDatabaseModel
import com.lampotrias.links.data.db.link.LinksDao

@Database(
	entities = [
		LinkDatabaseModel::class,
		FolderDatabaseModel::class,
	],
	version = 5,
	exportSchema = false
)

abstract class LinksDatabase : RoomDatabase() {
	abstract fun linksDao(): LinksDao
	abstract fun foldersDao(): FoldersDao
}