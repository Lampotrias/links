package com.lampotrias.links.di

import com.lampotrias.links.data.LinkMetadataRepoRepoImpl
import com.lampotrias.links.domain.LinkMetadataRepo
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
}