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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_landmark_recognition.*

class LandmarkRecognitionFragment : Fragment() {
    private val uiBinder = ImageChoosableUiBinder(this)
    private val groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_landmark_recognition, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiBinder.onResult.observe(this, Observer {
            when (it) {
                is ImageChoosableUiBinder.Result.ReceivedLocalImage -> handleImage(it.uri)
            }
        })
        uiBinder.bind(chooseButton)

        recyclerView.adapter = groupAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        uiBinder.handleActivityResult(requestCode, resultCode, data)
    }

    private fun handleImage(uri: Uri) {
        progressBar.isVisible = true
        contentGroup.isVisible = false
        val image = FirebaseVisionImage.fromFilePath(requireContext(), uri)
        val detector = FirebaseVision.getInstance().visionCloudLandmarkDetector
        detector.detectInImage(image)
            .addOnCompleteListener {
                progressBar.isVisible = false
                contentGroup.isVisible = true
            }
            .addOnSuccessListener { result ->
                groupAdapter.clear()
                groupAdapter.addAll(result.map { SimpleTextItem(SimpleTextItemModel(it.landmark)) })
            }
            .addOnFailureListener { it.printStackTrace() }
    }
}
