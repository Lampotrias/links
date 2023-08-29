package com.lampotrias.links.domain.cases

import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.LinksRepo
import com.lampotrias.links.domain.model.LinkModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestoreLinkUseCase @Inject constructor(
	private val repo: LinksRepo,
	private val dispatcherProvider: DispatcherProvider,
) {
	suspend operator fun invoke(linkModel: LinkModel) {
		withContext(dispatcherProvider.io) {
			repo.restoreLink(linkModel)
		}
	}
}