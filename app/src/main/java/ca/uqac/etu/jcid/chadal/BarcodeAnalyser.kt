package ca.uqac.etu.jcid.chadal

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import ca.uqac.etu.jcid.chadal.data.BarcodeValue
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/**
 * This class contains all the logic for barcode scanning.
 * It calls Google's Machine Learning Kit (ML Kit), in a "bundled" way, so that the app can be used
 * without an internet connection.
 */
class BarcodeAnalyser(
    private val onBarcodeScanned: (BarcodeValue) -> Unit) : ImageAnalysis.Analyzer
{
    // Used in order to prevent the same code from being scanned multiple times
    private var firstCall = true

    // Defines the barcode formats recognized by the app
    // In our case, we want to recognize every 1D barcode available in ML Kit (basically,
    // everything except 2D codes such as QR codes)
    private val scanOptions = BarcodeScannerOptions.Builder()
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
        .build()

    // The main method, used to analyse an image
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        // Access and use the barcode scanning feature of ML Kit
        val scanner = BarcodeScanning.getClient(scanOptions)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                // If there is a barcode that has not been scanned yet, get its number
                if (barcodes.isNotEmpty() && firstCall) {
                    val barcode = barcodes.firstOrNull() ?: return@addOnSuccessListener
                    if (barcode.valueType != Barcode.TYPE_TEXT && barcode.valueType != Barcode.TYPE_PRODUCT) return@addOnSuccessListener

                    // If the barcode is valid (not a text etc.), we transfer its value to the method "onBarcodeScanned"
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