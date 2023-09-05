package com.lampotrias.links.domain.cases

import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.FolderStorage
import com.lampotrias.links.domain.model.FolderModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateFolderUseCase @Inject constructor(
	private val folderStorage: FolderStorage,
	private val dispatcherProvider: DispatcherProvider,
) {
	suspend operator fun invoke(folderModel: FolderModel): Result<Long> {
		return withContext(dispatcherProvider.io) {
			return@withContext try {
				val id = folderStorage.createFolder(folderModel)
				Result.success(id)
			} catch (ex: Exception) {
				ex.printStackTrace()
				Result.failure(ex)
			}
		}
	}
}