package com.lampotrias.links.ui.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.lampotrias.links.domain.model.LinkModel

class LinksDiffUtil(
	private val oldList: List<LinkModel>,
	private val newList: List<LinkModel>
) : DiffUtil.Callback() {
	override fun getOldListSize(): Int {
		return oldList.size
	}

	override fun getNewListSize(): Int {
		return newList.size
	}

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition].id == newList[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}

	override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
		val diff = mutableMapOf<String, Any>()
		if (oldList[oldItemPosition].isFavorite != newList[newItemPosition].isFavorite) {
			diff["isFavorite"] = newList[newItemPosition].isFavorite
		}

		return diff.ifEmpty { null }
	}
}