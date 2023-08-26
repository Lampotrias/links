package com.lampotrias.links.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lampotrias.links.databinding.FragmentAddEditLinkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditLinkFragment : Fragment() {

	private var _binding: FragmentAddEditLinkBinding? = null
	private val binding get() = _binding!!

	private val viewModel: AddEditLinkViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAddEditLinkBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnCheckUrl.setOnClickListener {
			if (binding.url.text.isNotEmpty()) {
				viewModel.getMetadata(binding.url.text.trim().toString())
			}
		}

		binding.btnCopyUrl.setOnClickListener {

		}

		binding.btnOpenUrl.setOnClickListener {

		}

		subscribeEvents()
	}

	private fun subscribeEvents() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState.collect { state ->
					binding.url.setText(state.urlLink)
					binding.url.setSelection(binding.url.text.length)

					binding.title.text = state.titleLink
					binding.desctiption.text = state.descriptionLink
					binding.imageUrl.setImageURI(state.imageUrlLink)

					state.error?.getContentIfNotHandled()?.let {
						Toast.makeText(
							requireContext(),
							"Error check: ${it.localizedMessage}",
							Toast.LENGTH_LONG
						).show()
					}

					state.success?.getContentIfNotHandled()?.let {
						Toast.makeText(
							requireContext(),
							"Success added link with id: #$it",
							Toast.LENGTH_LONG
						).show()
					}
				}
			}
		}
	}

	companion object {
		private const val MODE_KEY = "mode"
		private const val LINk_ID_KEY = "mode"
		fun newInstanceForAdd(mode: FragmentMode) = AddEditLinkFragment().apply {
			arguments = bundleOf(
				MODE_KEY to mode
			)
		}

		fun newInstanceForEdit(mode: FragmentMode, linkId: String) = AddEditLinkFragment().apply {
			arguments = bundleOf(
				MODE_KEY to mode,
				LINk_ID_KEY to linkId
			)
		}
	}
}