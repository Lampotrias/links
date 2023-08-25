package com.lampotrias.links.domain

import com.lampotrias.links.domain.model.LinkModel

interface LinksRepo {
	fun addLink(linkModel: LinkModel)
	fun deleteLink(linkModel: LinkModel)
	fun getLinks(): List<LinkModel>
	fun updateLink(linkModel: LinkModel)
}