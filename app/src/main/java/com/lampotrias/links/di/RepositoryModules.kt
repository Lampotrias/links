package com.lampotrias.links.di

import com.lampotrias.links.data.LinkMetadataRepoRepoImpl
import com.lampotrias.links.data.db.LinksDao
import com.lampotrias.links.data.db.LinksStorage
import com.lampotrias.links.domain.LinkMetadataRepo
import com.lampotrias.links.domain.LinksRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules {
	@Provides
	fun provideLinkMetadataRepo(httpClient: OkHttpClient): LinkMetadataRepo {
		return LinkMetadataRepoRepoImpl(httpClient)
	}

	@Provides
	fun provideLinksRepo(
		linksDao: LinksDao,
		dispatcherProvider: DispatcherProvider,
	): LinksRepo {
		return LinksStorage(linksDao, dispatcherProvider)
	}
}