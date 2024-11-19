package ca.uqac.etu.jcid.chadal

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import android.graphics.ImageFormat
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import ca.uqac.etu.jcid.chadal.data.BarcodeValue
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.nio.ByteBuffer

class BarcodeAnalyser(
    private val onBarcodeScanned: (BarcodeValue) -> Unit
): ImageAnalysis.Analyzer {
    private var firstCall = true

    val scanOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_UPC_A,
            Barcode.FORMAT_UPC_E,
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_EAN_13,
            Barcode.FORMAT_CODE_39,
            Barcode.FORMAT_CODE_128,
            Barcode.FORMAT_ITF,
            Barcode.FORMAT_CODE_93,
            Barcode.FORMAT_CODABAR,
        )
        /*
        .setZoomSuggestionOptions(
            ZoomSuggestionOptions.Builder(zoomCallback)
            .setMaxSupportedZoomRatio(5f)
            .build())
         */
        .build()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        val scanner = BarcodeScanning.getClient(scanOptions)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty() && firstCall) {
                    val barcode = barcodes.firstOrNull() ?: return@addOnSuccessListener
                    if (barcode.valueType != Barcode.TYPE_TEXT && barcode.valueType != Barcode.TYPE_PRODUCT) return@addOnSuccessListener
                    barcode.rawValue?.let { codeValue ->
                        firstCall = false
                        onBarcodeScanned(BarcodeValue(codeValue, barcode.displayValue?: codeValue))
                        imageProxy.close()
                    }
                }
            }
            .addOnFailureListener { }
            .addOnCompleteListener { imageProxy.close() }
    }
}