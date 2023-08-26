package com.lampotrias.links.ui.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lampotrias.links.R
import com.lampotrias.links.databinding.FragmentAddEditLinkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditLinkFragment : Fragment() {

	private var _binding: FragmentAddEditLinkBinding? = null
	private val binding get() = _binding!!

	private val viewModel: AddEditLinkViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_add_edit_link, container, false)
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