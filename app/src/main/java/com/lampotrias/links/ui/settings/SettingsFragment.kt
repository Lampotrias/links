package com.lampotrias.links.ui.settings

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lampotrias.links.databinding.FragmentSettingsBinding
import com.lampotrias.links.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class SettingsFragment : Fragment() {

	private var _binding: FragmentSettingsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: SettingsViewModel by viewModels()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSettingsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.exportCsv.setOnClickListener {
			viewModel.exportCsv()
		}

		binding.exportJson.setOnClickListener {
			viewModel.exportJson()
		}

		subscribeEvents()
	}

	private fun subscribeEvents() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				val storedTheme = viewModel.getCurrentColorTheme().ordinal
				val themeVariants = AppThemeColor.entries.map { it.title }.toTypedArray()

				binding.themeSelector.apply {
					val adapter =
						ArrayAdapter(requireContext(), R.layout.simple_spinner_item, themeVariants)
					adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
					setAdapter(adapter)
					setSelection(storedTheme)
					onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
						override fun onItemSelected(
							parent: AdapterView<*>?,
							view: View?,
							position: Int,
							id: Long
						) {
							if (storedTheme == position) {
								return
							}

							val selectedTheme = AppThemeColor.entries[position]
							viewModel.saveColorTheme(selectedTheme)

							applyTheme(selectedTheme)
						}

						override fun onNothingSelected(parent: AdapterView<*>?) = Unit
					}
				}
			}
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState.collect { state ->

					state.successJson?.getContentIfNotHandled()?.let { file ->
						sendFile(file)
					}

					state.successCsv?.getContentIfNotHandled()?.let { file ->
						sendFile(file)
					}

					state.errorCsv?.getContentIfNotHandled()?.let {
						showError("Error export csv")
					}

					state.errorJson?.getContentIfNotHandled()?.let {
						showError("Error export json")
					}
				}
			}
		}
	}

	private fun sendFile(file: File) {
		activity?.let {
			Utils.systemSendFile(it, file)
		}
	}

	private fun showError(message: String) {
		Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
	}

	private fun applyTheme(selectedTheme: AppThemeColor) {
		when (selectedTheme) {
			AppThemeColor.System -> {
				AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
			}

			AppThemeColor.Dark -> {
				AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
			}

			AppThemeColor.Light -> {
				AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
			}
		}
	}

	companion object {
		fun newInstance(): SettingsFragment {
			return SettingsFragment()
		}
	}
}