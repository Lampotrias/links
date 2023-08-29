package com.lampotrias.links.domain

import com.lampotrias.links.domain.model.LinkModel
import kotlinx.coroutines.flow.Flow

interface LinksRepo {
	suspend fun addLink(linkModel: LinkModel): Long
	suspend fun deleteLink(linkModel: LinkModel)
	suspend fun restoreLink(linkModel: LinkModel)
	fun getLinks(): Flow<List<LinkModel>>
	suspend fun updateLink(linkModel: LinkModel)
}