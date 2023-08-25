package com.lampotrias.links.domain.cases

import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel

class AddLinkUseCase(
	private val repo: LinksRepo
) {
	operator fun invoke(linkModel: LinkModel) {
		repo.addLink(linkModel)
	}
}