package com.lampotrias.links.domain.model

data class LinkModel(
	val id: Long,
	val dateCreate: Long,
	val title: String,
	val description: String,
	val url: String,
	val imageUrl: String,
)