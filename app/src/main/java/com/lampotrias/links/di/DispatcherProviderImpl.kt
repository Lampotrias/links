package com.lampotrias.links.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
	override val io: CoroutineDispatcher
		get() = Dispatchers.IO
	override val main: CoroutineDispatcher
		get() = Dispatchers.Main
}