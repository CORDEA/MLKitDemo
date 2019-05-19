package jp.cordea.mlkitdemo


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.fragment_image_labeling.*

class ImageLabelingFragment : Fragment() {
    private val uiBinder = ImageChoosableUiBinder(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_image_labeling, container, false)

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
        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler

        labeler.processImage(image)
            .addOnSuccessListener { result ->
            }
            .addOnFailureListener { it.printStackTrace() }
    }
}
