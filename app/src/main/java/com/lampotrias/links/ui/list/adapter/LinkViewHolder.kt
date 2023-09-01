package com.lampotrias.links.ui.list.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.links.R
import com.lampotrias.links.databinding.LinkItemVhBinding
import com.lampotrias.links.domain.model.LinkModel

class LinkViewHolder(private val binding: LinkItemVhBinding): RecyclerView.ViewHolder(binding.root) {
	fun bind(linkModel: LinkModel, position: Int, listener: LinkEventListener) {
		binding.title.text = linkModel.title.ifEmpty { linkModel.url }
		binding.desctiption.text = linkModel.description
		binding.imageUrl.setImageURI(linkModel.imageUrl)
		binding.favoriteIcon.setImageResource(if (linkModel.isFavorite) R.drawable.baseline_star_on else R.drawable.baseline_star_off)

		binding.detail.setOnClickListener {
			listener.onDetail(linkModel)
		}

		binding.share.setOnClickListener {
			listener.onShare(linkModel)
		}

		binding.qrCode.setOnClickListener {
			listener.onQrCode(linkModel)
		}

		binding.moreContainer.setOnClickListener {
			val popupMenu = PopupMenu(binding.root.context, binding.moreIcon)
			popupMenu.inflate(R.menu.more_link_menu)
			popupMenu.setOnMenuItemClickListener { item ->
				when (item.itemId) {
					R.id.action_show -> {
						listener.onDetail(linkModel)
					}

					R.id.action_delete -> {
						listener.onDelete(linkModel, position)
					}

					else -> {}
				}
				false
			}
			popupMenu.show()
		}

		binding.favoriteContainer.setOnClickListener {
			listener.onFavorite(linkModel)
		}
	}
}