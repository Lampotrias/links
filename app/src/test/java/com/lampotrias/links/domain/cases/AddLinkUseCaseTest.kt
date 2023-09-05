package com.lampotrias.links.domain.cases

import com.google.common.truth.Truth.assertThat
import com.lampotrias.links.data.db.link.LinkDatabaseModel
import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel
import com.lampotrias.links.domain.model.LinkSaveModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddLinkUseCaseTest {
	private val link1 = LinkSaveModel(
		id = 1,
		dateCreate = System.currentTimeMillis(),
		title = "title1",
		description = "description1",
		url = "url1",
		imageUrl = "imageUrl1",
		folderId = 1,
	)

	private val link2 = LinkSaveModel(
		id = 2,
		dateCreate = System.currentTimeMillis(),
		title = "title2",
		description = "description2",
		url = "url2",
		imageUrl = "imageUrl2",
		folderId = 1,
	)

	private val linksRepo = object : LinksRepo {
		private val links = mutableListOf<LinkSaveModel>()
		override suspend fun addLink(linkSaveModel: LinkSaveModel): Long {
			links.add(linkSaveModel)

			return linkSaveModel.id
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
//				emit(links)
			}
		}

		override fun getFavoritesLinks(): Flow<List<LinkModel>> {
			TODO("Not yet implemented")
		}

		override suspend fun updateLink(linkModel: LinkModel) {
			links.removeIf {
				it.id == linkModel.id
			}
//			links.add(linkModel)
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