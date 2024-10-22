package ca.uqac.etu.jcid.chadal

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import android.graphics.ImageFormat
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.nio.ByteBuffer

class BarcodeAnalyser(
    private val onBarcodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer {

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
        val result = scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints

                    val rawValue = barcode.rawValue
                    val valueType = barcode.valueType

                    if (valueType != Barcode.TYPE_TEXT && valueType != Barcode.TYPE_PRODUCT) continue
                    val value = barcode.displayValue
                }
            }
            .addOnFailureListener {
            }
    }
}