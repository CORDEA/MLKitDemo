package jp.cordea.mlkitdemo


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.fragment_barcode_scanning.*

class BarcodeScanningFragment : Fragment() {
    private val uiBinder = ImageChoosableUiBinder(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_barcode_scanning, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiBinder.onResult.observe(this, Observer {
            when (it) {
                is ImageChoosableUiBinder.Result.ReceivedLocalImage -> handleImage(it.uri)
            }
        })
        uiBinder.bind(chooseButton)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        uiBinder.handleActivityResult(requestCode, resultCode, data)
    }

    private fun handleImage(uri: Uri) {
        val image = FirebaseVisionImage.fromFilePath(requireContext(), uri)
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

        detector.detectInImage(image)
            .addOnSuccessListener { result ->
                result.firstOrNull()?.let { handleBarcode(it) }
            }
            .addOnFailureListener {
            }
    }

    private fun handleBarcode(barcode: FirebaseVisionBarcode) {
        when (barcode.valueType) {
            FirebaseVisionBarcode.TYPE_URL -> {
                val uri = barcode.rawValue?.toUri() ?: return
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    }
}
