package com.lampotrias.links.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.links.databinding.LinkItemVhBinding
import com.lampotrias.links.domain.model.LinkModel

class LinkViewHolder(private val binding: LinkItemVhBinding): RecyclerView.ViewHolder(binding.root) {
	fun bind(linkModel: LinkModel) {
		binding.title.text = linkModel.title.ifEmpty { linkModel.url }
		binding.desctiption.text = linkModel.description
		binding.imageUrl.setImageURI(linkModel.imageUrl)
	}
}