package com.lampotrias.links.data.db

import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel
import com.lampotrias.links.domain.model.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LinksStorage @Inject constructor(
	private val linksDao: LinksDao,
	private val dispatcherProvider: DispatcherProvider,
) : LinksRepo {
	override suspend fun addLink(linkModel: LinkModel) {
		withContext(dispatcherProvider.io) {
			linksDao.addLink(linkModel.asDatabaseModel())
		}
	}

	override suspend fun deleteLink(linkModel: LinkModel) {
		withContext(dispatcherProvider.io) {
			linksDao.deleteLink(linkModel.id)
		}
	}

	override fun getLinks(): Flow<List<LinkModel>> {
		return linksDao.getLinks()
			.map {
				it.map { linkDatabaseModel ->
					linkDatabaseModel.asDomainModel()
				}
			}
	}

	override suspend fun updateLink(linkModel: LinkModel) {
		withContext(dispatcherProvider.io) {
			linksDao.updateLink(linkModel.asDatabaseModel())
		}
	}
}