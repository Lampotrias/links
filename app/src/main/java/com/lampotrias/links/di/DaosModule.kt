package com.lampotrias.links.di

import com.lampotrias.links.data.db.LinksDao
import com.lampotrias.links.data.db.LinksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
	@Provides
	fun providesEpisodesDao(database: LinksDatabase): LinksDao {
		return database.linksDao()
	}
}