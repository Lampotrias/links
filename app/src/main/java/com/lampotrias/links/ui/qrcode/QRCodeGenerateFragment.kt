package com.lampotrias.links.ui.qrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lampotrias.links.databinding.FragmentQrCodeGenerateBinding

class QRCodeGenerateFragment : Fragment() {

	private var _binding: FragmentQrCodeGenerateBinding? = null
	private val binding get() = _binding!!

	private val viewModel: QRCodeGenerateViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentQrCodeGenerateBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val externalUrl = requireArguments().getString(URL_KEY) ?: ""

		val bitmap = viewModel.getQrCodeBitmap(externalUrl)

		if (externalUrl.isNotEmpty() && bitmap != null) {
			binding.qrcodeUrl.text = externalUrl
			binding.qrcode.setImageBitmap(bitmap)
		} else {
			// error
		}
	}

	companion object {
		private const val URL_KEY = "url"
		fun newInstance(url: String): QRCodeGenerateFragment {
			return QRCodeGenerateFragment().apply {
				arguments = bundleOf(
					URL_KEY to url
				)
			}
		}
	}
}