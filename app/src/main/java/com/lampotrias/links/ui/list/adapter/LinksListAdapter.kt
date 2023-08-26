package com.lampotrias.links.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.links.databinding.LinkItemVhBinding
import com.lampotrias.links.domain.model.LinkModel

class LinksListAdapter(private val listener: LinkEventListener) : RecyclerView.Adapter<LinkViewHolder>() {
	private val links = mutableListOf<LinkModel>()

	fun setItems(newList: List<LinkModel>) {
		val diffResult = DiffUtil.calculateDiff(LinksDiffUtil(links, newList))
		links.clear()
		links.addAll(newList)

		diffResult.dispatchUpdatesTo(this)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
		return LinkViewHolder(
			LinkItemVhBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
		val link = links[position]

		holder.bind(link, listener)
	}

	override fun getItemCount(): Int {
		return links.size
	}
}