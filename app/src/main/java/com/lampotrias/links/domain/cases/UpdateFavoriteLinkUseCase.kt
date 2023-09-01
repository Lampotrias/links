package com.lampotrias.links.domain.cases

import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateFavoriteLinkUseCase @Inject constructor(
	private val repo: LinksRepo,
	private val dispatcherProvider: DispatcherProvider,
) {
	suspend operator fun invoke(linkModel: LinkModel): Result<Unit> {
		return withContext(dispatcherProvider.io) {
			return@withContext try {
				repo.updateFavorite(linkModel)
				Result.success(Unit)
			} catch (ex: Exception) {
				ex.printStackTrace()
				Result.failure(ex)
			}
		}
	}
}