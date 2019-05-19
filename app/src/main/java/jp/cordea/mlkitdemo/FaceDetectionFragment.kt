package jp.cordea.mlkitdemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.fragment_face_detection.*

class FaceDetectionFragment : Fragment() {
    private val uiBinder = ImageChoosableUiBinder(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_face_detection, container, false)

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
        progressBar.isVisible = true
        contentGroup.isVisible = false
        val image = FirebaseVisionImage.fromFilePath(requireContext(), uri)
        val detector = FirebaseVision.getInstance().visionFaceDetector
        detector.detectInImage(image)
            .addOnCompleteListener {
                progressBar.isVisible = false
                contentGroup.isVisible = true
            }
            .addOnSuccessListener { result ->
                resultView.drawBoundingBox(image, result.map { it.boundingBox })
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}
