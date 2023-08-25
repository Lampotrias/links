package com.lampotrias.links.data

import com.lampotrias.links.domain.LinkMetadataRepo
import com.lampotrias.links.domain.model.LinkRemoteModel
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class LinkMetadataRepoRepoImpl(private val httpClient: OkHttpClient) : LinkMetadataRepo {
	override suspend fun getLinkMetadata(url: String): Result<LinkRemoteModel> {
		val finalUrl = BASE_URL + url
		val request = Request.Builder()
			.url(finalUrl)
			.build()

		try {
			val response = httpClient.newCall(request).execute()
			if (response.isSuccessful) {
				val responseJson = JSONObject(response.body!!.string())
				val imageUrl = responseJson.optJSONArray("images")?.let { jsImages ->
					if (jsImages.length() > 0) {
						jsImages.optString(0, "")
					} else {
						""
					}
				} ?: ""

				val title = responseJson.optString("title")
				val description = responseJson.optString("description")

				return Result.success(
					LinkRemoteModel(
						url = url,
						imageUrl = imageUrl,
						title = title,
						description = description,
					)
				)
			} else {
				throw RuntimeException("query not success")
			}

		} catch (ex: Exception) {
			return Result.failure(ex)
		}
	}

	companion object {
		private const val BASE_URL = "https://jsonlink.io/api/extract?url="
	}
}