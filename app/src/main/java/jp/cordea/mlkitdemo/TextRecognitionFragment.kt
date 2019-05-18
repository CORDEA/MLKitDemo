package jp.cordea.mlkitdemo


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.fragment_text_recognition.*

class TextRecognitionFragment : Fragment() {
    companion object {
        private const val REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_text_recognition, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(Intent.createChooser(intent, "Choose picture"), REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data ?: return
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE) {
            handleImage(uri)
        }
    }

    private fun handleImage(uri: Uri) {
        progressBar.isVisible = true
        contentGroup.isVisible = false
        textView.text = ""
        val image = FirebaseVisionImage.fromFilePath(requireContext(), uri)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        detector.processImage(image)
            .addOnCompleteListener {
                progressBar.isVisible = false
                contentGroup.isVisible = true
            }
            .addOnSuccessListener {
                textView.text = it.text
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}
