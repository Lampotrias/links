package com.lampotrias.links.ui.addshow

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
import com.lampotrias.links.databinding.FragmentAddShowLinkBinding
import com.lampotrias.links.domain.model.FolderModel
import com.lampotrias.links.domain.model.LinkModel
import com.lampotrias.links.domain.model.LinkSaveModel
import com.lampotrias.links.utils.Utils
import com.lampotrias.links.utils.ext.getPlainText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AddShowLinkFragment : Fragment() {

	private val viewModel: AddShowLinkViewModel by viewModels()

	private var _binding: FragmentAddShowLinkBinding? = null
	private val binding get() = _binding!!

	private var currentFolder: FolderModel? = null

	init {
		Timber.e("init ${this.id}")
	}

	@RequiresApi(Build.VERSION_CODES.TIRAMISU)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		Timber.e("onCreate ${this.id}")
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		Timber.e("onCreateView ${this.id}")
		_binding = FragmentAddShowLinkBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		Timber.e("onViewCreated ${this.id}")

		val mode = requireArguments().getParcelable(DETAIL_MODE_KEY) ?: FragmentDetailMode.Add
		val linkModel = requireArguments().getParcelable<LinkModel>(LINk_MODEL_KEY)

		if (mode == FragmentDetailMode.Add) {
			val externalUrl = requireArguments().getString(URL_KEY) ?: ""
			viewModel.setInitialUrl(externalUrl)
		}

		if (mode == FragmentDetailMode.Show && linkModel != null) {
			viewModel.setInitialState(linkModel)
		}

		when (mode) {
			FragmentDetailMode.Show -> {
				binding.btnSave.text = "Save"
				binding.btnSave.visibility = View.INVISIBLE
				binding.btnInsertFromClipboard.visibility = View.INVISIBLE
				binding.btnCheckUrl.visibility = View.INVISIBLE
			}

			FragmentDetailMode.Add -> {
				binding.btnSave.text = "Add"
				binding.btnSave.visibility = View.VISIBLE
				binding.btnInsertFromClipboard.visibility = View.VISIBLE
				binding.btnCheckUrl.visibility = View.VISIBLE
			}
		}

		binding.btnCheckUrl.setOnClickListener {
			val url = binding.url.editText?.text?.toString()

			if (!url.isNullOrEmpty()) {
				viewModel.getMetadata(url.trim())
			}
		}

		binding.btnInsertFromClipboard.setOnClickListener {
			val text =
				(activity?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.getPlainText()
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
			val url = binding.url.editText?.text?.toString()
			if (!url.isNullOrEmpty()) {
				currentFolder?.let { folder ->
					viewModel.addLink(
						LinkSaveModel(
							url = url,
							description = binding.desctiption.text.toString(),
							title = binding.title.text.toString(),
							imageUrl = binding.imageUrl.tag?.toString() ?: "",
							folderId = folder.id,
						)
					)
				} ?: run {
					Toast.makeText(requireContext(), "folder is null", Toast.LENGTH_SHORT).show()
				}
			} else {
				Toast.makeText(requireContext(), "Empty url", Toast.LENGTH_SHORT).show()
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

					currentFolder = state.folderModel
					binding.folderName.text = "folder: ${state.folderModel?.name}" ?: ""

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

	override fun onAttach(context: Context) {
		super.onAttach(context)

		Timber.e("onAttach ${this.id}")
	}

	override fun onDestroyView() {
		super.onDestroyView()

		Timber.e("onDestroyView ${this.id}")
	}

	override fun onDestroy() {
		super.onDestroy()

		Timber.e("onDestroy ${this.id}")
	}

	override fun onDetach() {
		super.onDetach()

		Timber.e("onDetach ${this.id}")
	}

	companion object {
		private const val DETAIL_MODE_KEY = "mode"
		private const val URL_KEY = "url"
		private const val LINk_MODEL_KEY = "link_model_key"
		fun newInstanceForAdd(url: String = "") = AddShowLinkFragment().apply {
			arguments = bundleOf(
				DETAIL_MODE_KEY to FragmentDetailMode.Add,
				URL_KEY to url
			)
		}

		fun newInstanceForDetail(linkModel: LinkModel) = AddShowLinkFragment().apply {
			arguments = bundleOf(
				DETAIL_MODE_KEY to FragmentDetailMode.Show,
				LINk_MODEL_KEY to linkModel
			)
		}
	}
}