package com.lampotrias.links.data

import com.lampotrias.links.data.db.folder.FoldersDao
import com.lampotrias.links.data.db.folder.asDomainModel
import com.lampotrias.links.di.DispatcherProvider
import com.lampotrias.links.domain.FolderStorage
import com.lampotrias.links.domain.model.FolderModel
import com.lampotrias.links.domain.model.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FolderStorageImpl @Inject constructor(
	private val foldersDao: FoldersDao,
	private val dispatcherProvider: DispatcherProvider
) : FolderStorage {
	override fun createFolder(folderModel: FolderModel): Long {
		return foldersDao.insertFolder(folderModel.asDatabaseModel())
	}

	override fun getFolders(): Flow<List<FolderModel>> {
		return foldersDao.getFolders().map {
			it.map { dbFolder ->
				dbFolder.asDomainModel()
			}
		}
	}
}
