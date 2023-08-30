package com.lampotrias.links.di

import android.content.Context
import android.database.SQLException
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lampotrias.links.data.db.LinksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
	@Provides
	@Singleton
	fun providesNiaDatabase(@ApplicationContext context: Context): LinksDatabase {
		return Room.databaseBuilder(context, LinksDatabase::class.java, "links-database")
			.addMigrations(MIGRATION_1_2)
			.addMigrations(MIGRATION_2_3)
			.fallbackToDestructiveMigration()
			.build()
	}


	private val MIGRATION_1_2 = object : Migration(1, 2) {
		override fun migrate(database: SupportSQLiteDatabase) {

		}
	}

	private val MIGRATION_2_3 = object : Migration(2, 3) {
		override fun migrate(database: SupportSQLiteDatabase) {
			try {
				database.execSQL("SELECT `isFavorite` FROM `links` LIMIT 1")
			} catch (ex: SQLException) {
				database.execSQL("ALTER TABLE `links` ADD COLUMN `isFavorite` BOOLEAN NOT NULL DEFAULT 0")
			}
		}
	}
}
