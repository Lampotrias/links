package com.lampotrias.links.ui.qrcode

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


class QRCodeGenerateViewModel : ViewModel() {
	fun getQrCodeBitmap(url: String): Bitmap? {
		return try {
			val barcodeEncoder = BarcodeEncoder()
			barcodeEncoder.encodeBitmap(url, BarcodeFormat.QR_CODE, 500, 500)
		} catch (e: Exception) {
			null
		}
	}
}