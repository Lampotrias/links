package com.lampotrias.links.domain.cases

import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddLinkUseCase @Inject constructor(
	private val repo: LinksRepo,
	private val dispatcherProvider: DispatcherProvider,
) {
	suspend operator fun invoke(linkModel: LinkModel): Result<Long> {
		return withContext(dispatcherProvider.io) {
			return@withContext try {
				val id = repo.addLink(linkModel)
				Result.success(id)
			} catch (ex: Exception) {
				ex.printStackTrace()
				Result.failure(ex)
			}
		}
	}
}