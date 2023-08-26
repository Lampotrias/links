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

		override fun getLinks(): Flow<List<LinkModel>> {
			return flow {
				emit(links)
			}
		}

		override suspend fun updateLink(linkModel: LinkModel) {
			links.removeIf {
				it.id == linkModel.id
			}
			links.add(linkModel)
		}
	}

	@Test
	operator fun invoke() {
		runBlocking {
			linksRepo.addLink(link1)

			linksRepo.getLinks().collect {
				assertThat(it.size).isEqualTo(1)
			}

			linksRepo.addLink(link2)

			linksRepo.getLinks().collect {
				assertThat(it.size).isEqualTo(2)
			}
		}
	}
}