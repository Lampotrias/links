package com.lampotrias.links.data.export.type

import com.lampotrias.links.data.export.IOutputExporter
import com.lampotrias.links.domain.model.LinkModel

class CsvConverter : IOutputExporter {
	override fun exportData(data: List<LinkModel>): String {
		return buildString {

			data.forEachIndexed { index, linkModel ->

				append(linkModel.id)
				append(";")

				append("\"")
				append(linkModel.title)
				append("\"")
				append(";")

				append("\"")
				append(linkModel.url)
				append("\"")
				append(";")

				append(linkModel.dateCreate)
				append(";")

				append(linkModel.isFavorite)
				append(";")

				append("\"")
				append(linkModel.description)
				append("\"")
				append(";")

				append("\"")
				append(linkModel.imageUrl)
				append("\"")
				append(";")

				if (index != data.size) {
					append("\n")
				}
			}
		}
	}
}