package com.lampotrias.links.domain

import com.lampotrias.links.domain.model.FolderModel
import kotlinx.coroutines.flow.Flow

interface FolderStorage {
	fun createFolder(folderModel: FolderModel): Long
	fun getFolders(): Flow<List<FolderModel>>
}