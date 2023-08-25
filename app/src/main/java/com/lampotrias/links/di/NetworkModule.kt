package com.lampotrias.links.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	@Provides
	fun provideOkHttpClient(): OkHttpClient {
		return OkHttpClient.Builder()
			.build()
	}
}