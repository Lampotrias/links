package com.lampotrias.links.domain.cases

import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.LinkMetadataRepo
import com.lampotrias.links.domain.model.LinkRemoteModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLinkMetadataUseCase @Inject constructor(
	private val metadataRepo: LinkMetadataRepo,
	private val dispatcherProvider: DispatcherProvider,
) {
	suspend operator fun invoke(url: String): Result<LinkRemoteModel> {
		return withContext(dispatcherProvider.io) {
			return@withContext metadataRepo.getLinkMetadata(url)
		}
	}
}