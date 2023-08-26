package com.lampotrias.links.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampotrias.links.R
import com.lampotrias.links.databinding.FragmentLinksListBinding
import com.lampotrias.links.ui.addedit.AddEditLinkFragment
import com.lampotrias.links.ui.addedit.FragmentMode
import com.lampotrias.links.ui.list.adapter.LinksListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LinksListFragment : Fragment() {

	private var _binding: FragmentLinksListBinding? = null
	private val binding get() = _binding!!

	private val viewModel: LinksListViewModel by activityViewModels()
	private val linksAdapter = LinksListAdapter()

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
		}

		binding.floatingActionButton.setOnClickListener {
			val addEditLinkFragment = AddEditLinkFragment.newInstanceForAdd(FragmentMode.Add)
			parentFragmentManager.commit {
				setReorderingAllowed(true)
				add(R.id.main_fragment_container, addEditLinkFragment, "add-edit")
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
}