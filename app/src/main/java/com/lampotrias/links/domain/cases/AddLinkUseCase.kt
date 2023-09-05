package com.lampotrias.links.domain.cases

import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkSaveModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddLinkUseCase @Inject constructor(
	private val repo: LinksRepo,
	private val dispatcherProvider: DispatcherProvider,
) {
	suspend operator fun invoke(linkSaveModel: LinkSaveModel): Result<Long> {
		return withContext(dispatcherProvider.io) {
			return@withContext try {
				val id = repo.addLink(linkSaveModel)
				Result.success(id)
			} catch (ex: Exception) {
				ex.printStackTrace()
				Result.failure(ex)
			}
		}
	}
}