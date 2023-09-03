package com.lampotrias.links.data.export

import com.lampotrias.links.domain.model.LinkModel


interface IOutputExporter {
	fun exportData(data: List<LinkModel>): String
}