package com.lampotrias.links.di

import com.lampotrias.links.data.FolderStorageImpl
import com.lampotrias.links.data.LinkMetadataRepoRepoImpl
import com.lampotrias.links.data.db.link.LinksDao
import com.lampotrias.links.data.LinksStorage
import com.lampotrias.links.data.db.folder.FoldersDao
import com.lampotrias.links.domain.FolderStorage
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

	@Provides
	fun provideFolderStorage(
		foldersDao: FoldersDao,
		dispatcherProvider: DispatcherProvider,
	): FolderStorage {
		return FolderStorageImpl(foldersDao, dispatcherProvider)
	}
}