package com.lampotrias.links.ui.list

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lampotrias.links.R
import com.lampotrias.links.databinding.FragmentLinksListBinding
import com.lampotrias.links.domain.model.LinkModel
import com.lampotrias.links.ui.addshow.AddShowLinkFragment
import com.lampotrias.links.ui.list.adapter.LinkEventListener
import com.lampotrias.links.ui.list.adapter.LinksListAdapter
import com.lampotrias.links.ui.list.adapter.SwipeToDeleteCallback
import com.lampotrias.links.ui.qrcode.QRCodeGenerateFragment
import com.lampotrias.links.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LinksListFragment : Fragment() {

	private var _binding: FragmentLinksListBinding? = null
	private val binding get() = _binding!!

	private val viewModel: LinksListViewModel by viewModels()
	private val linksAdapter = LinksListAdapter(object : LinkEventListener {
		override fun onDetail(linkModel: LinkModel) {
			navigateToDetail(linkModel)
		}

		override fun onQrCode(linkModel: LinkModel) {
			navigateToQrCode(linkModel)
		}

		override fun onDelete(linkModel: LinkModel, position: Int) {
			deleteLink(position, linkModel)
		}

		override fun onShare(linkModel: LinkModel) {
			activity?.let {
				Utils.shareUrl(it, linkModel.url)
			}
		}

		override fun onFavorite(linkModel: LinkModel) {
			viewModel.updateFavorite(linkModel)
		}
	})

	private fun navigateToDetail(linkModel: LinkModel) {
		val addShowLinkFragment = AddShowLinkFragment.newInstanceForDetail(linkModel)
		parentFragmentManager.commit {
			setReorderingAllowed(true)
			add(R.id.main_fragment_container, addShowLinkFragment, "add-detail")
			addToBackStack(null)
		}
	}

	private fun navigateToQrCode(linkModel: LinkModel) {
		val qrCodeGenerateFragment = QRCodeGenerateFragment.newInstance(linkModel.url)
		parentFragmentManager.commit {
			setReorderingAllowed(true)
			add(R.id.main_fragment_container, qrCodeGenerateFragment, "qrcode-generator")
			addToBackStack(null)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentLinksListBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.rvLinkList.apply {
			layoutManager = LinearLayoutManager(context)
			adapter = linksAdapter
			val swipeToDeleteCallback = SwipeToDeleteCallback(
				requireContext()
			) { position ->
				deleteLink(position)
			}
			val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
			itemTouchHelper.attachToRecyclerView(this)
		}

		binding.floatingActionButton.setOnClickListener {
			val addShowLinkFragment = AddShowLinkFragment.newInstanceForAdd()
			parentFragmentManager.commit {
				setReorderingAllowed(true)
				add(R.id.main_fragment_container, addShowLinkFragment, "add-detail")
				addToBackStack(null)
			}
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState.collect { state ->
					linksAdapter.setItems(state.links)
				}
			}
		}
	}

	private fun deleteLink(position: Int, linkModel: LinkModel? = null) {
		val deletedLink = linkModel ?: linksAdapter.getLink(position) ?: return

		linksAdapter.removeItem(position)


		Snackbar.make(
			binding.root,
			"Link was removed from the list.",
			Snackbar.LENGTH_LONG
		).apply {

			setAction("UNDO") {
				linksAdapter.restoreItem(deletedLink, position)
				viewModel.restoreLink(deletedLink)
			}
			setActionTextColor(Color.YELLOW)

			viewModel.deleteLink(deletedLink)

//				addCallback(object : Snackbar.Callback() {
//					override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
//						if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_SWIPE) {
//							viewModel.deleteLink(deletedLink)
//						}
//					}
//				})
			show()
		}
	}
}