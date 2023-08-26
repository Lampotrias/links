package com.lampotrias.links.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
	entities = [
		LinkDatabaseModel::class
	],
	version = 1,
	exportSchema = false
)

abstract class LinksDatabase : RoomDatabase() {
	abstract fun linksDao(): LinksDao
}