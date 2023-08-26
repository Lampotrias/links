package com.lampotrias.links.di

import android.content.Context
import androidx.room.Room
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
			.fallbackToDestructiveMigration()
			.build()
	}
}
