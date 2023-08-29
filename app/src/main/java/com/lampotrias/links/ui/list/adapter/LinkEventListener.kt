package com.lampotrias.links.ui.list.adapter

import com.lampotrias.links.domain.model.LinkModel

interface LinkEventListener {
	fun onDetail(linkModel: LinkModel)
	fun onQrCode(linkModel: LinkModel)
	fun onDelete(linkModel: LinkModel, position: Int)
	fun onShare(linkModel: LinkModel)
	fun onFavorite(linkModel: LinkModel)
}