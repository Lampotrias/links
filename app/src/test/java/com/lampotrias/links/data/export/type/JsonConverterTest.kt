package com.lampotrias.links.data.export.type

import com.google.common.truth.Truth
import com.lampotrias.links.domain.model.LinkModel
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class JsonConverterTest {

	@Test
	fun exportData() {
		val model1 = LinkModel(
			11,
			123123,
			"title",
			"description",
			"url",
			"imageUrl",
			false
		)

		val model2 = LinkModel(
			22,
			333333,
			"title2",
			"description2",
			"url2",
			"imageUrl2",
			true
		)

		val converter = JsonConverter()
		val result = converter.exportData(listOf(model1, model2))

		Truth.assertThat(result.length).isEqualTo(1)
	}
}