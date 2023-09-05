package com.lampotrias.links.di

import com.lampotrias.links.data.db.link.LinksDao
import com.lampotrias.links.data.db.LinksDatabase
import com.lampotrias.links.data.db.folder.FoldersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
	@Provides
	fun providesLinksDao(database: LinksDatabase): LinksDao {
		return database.linksDao()
	}

	@Provides
	fun providesFoldersDao(database: LinksDatabase): FoldersDao {
		return database.foldersDao()
	}
}