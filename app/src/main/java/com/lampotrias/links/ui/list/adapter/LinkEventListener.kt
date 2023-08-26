package com.lampotrias.links.ui.list.adapter

import com.lampotrias.links.domain.model.LinkModel

interface LinkEventListener {
	fun onEdit(linkModel: LinkModel)
	fun onShare(linkModel: LinkModel)
	fun onMore(linkModel: LinkModel)
	fun onFavorite(linkModel: LinkModel)
}