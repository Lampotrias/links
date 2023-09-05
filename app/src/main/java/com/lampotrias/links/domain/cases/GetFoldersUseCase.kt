package com.lampotrias.links.domain.cases

import com.lampotrias.links.domain.FolderStorage
import com.lampotrias.links.domain.model.FolderModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
	private val folderStorage: FolderStorage,
) {
	operator fun invoke(): Flow<List<FolderModel>> {
		return folderStorage.getFolders()
	}
}