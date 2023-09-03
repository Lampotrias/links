package com.lampotrias.links.data.export.type

import com.lampotrias.links.data.export.IOutputExporter
import com.lampotrias.links.domain.model.LinkModel
import org.json.JSONArray
import org.json.JSONObject

class JsonConverter : IOutputExporter {
	override fun exportData(data: List<LinkModel>): String {
		val arrayObjects = JSONArray()
		data.forEach {
			val jsObject = JSONObject().apply {
				put("id", it.id)
				put("title", it.title)
				put("url", it.url)
				put("dateCreate", it.dateCreate)
				put("isFavorite", it.isFavorite)
				put("description", it.description)
				put("imageUrl", it.imageUrl)
			}
			arrayObjects.put(jsObject)
		}
		return arrayObjects.toString()
	}
}