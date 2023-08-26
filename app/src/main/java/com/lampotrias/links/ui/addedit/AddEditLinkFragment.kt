package com.lampotrias.links.ui.addedit

import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lampotrias.links.databinding.FragmentAddEditLinkBinding
import com.lampotrias.links.domain.model.LinkModel
import com.lampotrias.links.utils.Utils
import com.lampotrias.links.utils.ext.getPlainText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AddEditLinkFragment : Fragment() {

	private val viewModel: AddEditLinkViewModel by viewModels()

	private var _binding: FragmentAddEditLinkBinding? = null
	private val binding get() = _binding!!

	init {
		Timber.w("init")
	}

	@RequiresApi(Build.VERSION_CODES.TIRAMISU)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		Timber.w("onCreate")
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAddEditLinkBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val mode = requireArguments().getParcelable(MODE_KEY) ?: FragmentMode.Add
		val linkModel = requireArguments().getParcelable<LinkModel>(LINk_KEY)

		if (mode == FragmentMode.Edit && linkModel != null) {
			viewModel.setInitialState(linkModel)
		}

		binding.btnSave.text = when(mode) {
			FragmentMode.Edit -> "Save"
			FragmentMode.Add -> "Add"
		}

		binding.btnCheckUrl.setOnClickListener {
			val url = binding.url.editText?.text?.toString()

			if (!url.isNullOrEmpty()) {
				viewModel.getMetadata(url.trim())
			}
		}

		binding.btnInsertFromClipboard.setOnClickListener {
			val text = (activity?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.getPlainText()
			if (!text.isNullOrEmpty()) {
				binding.url.editText?.setText(text)
			}
		}

		binding.btnOpenUrl.setOnClickListener {
			val url = binding.url.editText?.toString()
			if (!url.isNullOrEmpty()) {
				activity?.let {
					Utils.openUrl(it, url)
				}
			}
		}

		binding.btnSave.setOnClickListener {
			val url = binding.url.editText?.toString()
			if (!url.isNullOrEmpty()) {
				viewModel.addLink(
					LinkModel(
						url = url,
						description = binding.desctiption.text.toString(),
						title = binding.title.text.toString(),
						imageUrl = binding.imageUrl.tag?.toString() ?: ""
					)
				)
			} else {
				Toast.makeText(
					requireContext(),
					"Empty url",
					Toast.LENGTH_SHORT
				).show()
			}
		}

		subscribeEvents()
	}

	private fun subscribeEvents() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState.collect { state ->
					binding.url.editText?.setText(state.urlLink)
					binding.url.editText?.setSelection(binding.url.editText?.text?.length ?: 0)

					binding.title.text = state.titleLink
					binding.desctiption.text = state.descriptionLink
					with(binding.imageUrl) {
						setImageURI(state.imageUrlLink)
						tag = state.imageUrlLink
					}

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
		private const val LINk_KEY = "link"
		fun newInstanceForAdd() = AddEditLinkFragment().apply {
			arguments = bundleOf(
				MODE_KEY to FragmentMode.Add
			)
		}

		fun newInstanceForEdit(linkModel: LinkModel) = AddEditLinkFragment().apply {
			arguments = bundleOf(
				MODE_KEY to FragmentMode.Edit,
				LINk_KEY to linkModel
			)
		}
	}
}