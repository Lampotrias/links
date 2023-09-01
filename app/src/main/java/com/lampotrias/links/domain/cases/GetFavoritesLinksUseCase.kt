package com.lampotrias.links.domain.cases

import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesLinksUseCase @Inject constructor(
	private val repo: LinksRepo,
) {
	operator fun invoke(): Flow<List<LinkModel>> {
		return repo.getFavoritesLinks()
	}
}