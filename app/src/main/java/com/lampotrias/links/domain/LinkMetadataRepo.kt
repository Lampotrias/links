package com.lampotrias.links.domain

import com.lampotrias.links.domain.model.LinkRemoteModel

interface LinkMetadataRepo {
	suspend fun getLinkMetadata(url: String): Result<LinkRemoteModel>
}