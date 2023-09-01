package com.lampotrias.links.domain.cases

import com.google.common.truth.Truth.assertThat
import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddLinkUseCaseTest {
	private val link1 = LinkModel(
		id = 1,
		dateCreate = System.currentTimeMillis(),
		title = "title1",
		description = "description1",
		url = "url1",
		imageUrl = "imageUrl1"
	)

	private val link2 = LinkModel(
		id = 2,
		dateCreate = System.currentTimeMillis(),
		title = "title2",
		description = "description2",
		url = "url2",
		imageUrl = "imageUrl2"
	)

	private val linksRepo = object : LinksRepo {
		private val links = mutableListOf<LinkModel>()
		override suspend fun addLink(linkModel: LinkModel): Long {
			links.add(linkModel)

			return linkModel.id
		}

		override suspend fun deleteLink(linkModel: LinkModel) {
			links.removeIf {
				it.id == linkModel.id
			}
		}

		override suspend fun restoreLink(linkModel: LinkModel) {
			TODO("Not yet implemented")
		}

		override fun getAllLinks(): Flow<List<LinkModel>> {
			return flow {
				emit(links)
			}
		}

		override fun getFavoritesLinks(): Flow<List<LinkModel>> {
			TODO("Not yet implemented")
		}

		override suspend fun updateLink(linkModel: LinkModel) {
			links.removeIf {
				it.id == linkModel.id
			}
			links.add(linkModel)
		}

		override suspend fun updateFavorite(linkModel: LinkModel) {
			TODO("Not yet implemented")
		}
	}

	@Test
	operator fun invoke() {
		runBlocking {
			linksRepo.addLink(link1)

			linksRepo.getAllLinks().collect {
				assertThat(it.size).isEqualTo(1)
			}

			linksRepo.addLink(link2)

			linksRepo.getAllLinks().collect {
				assertThat(it.size).isEqualTo(2)
			}
		}
	}
}