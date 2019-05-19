package jp.cordea.mlkitdemo


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_object_detection_and_tracking.*

class ObjectDetectionAndTrackingFragment : Fragment() {
    private val uiBinder = ImageChoosableUiBinder(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_object_detection_and_tracking, container, false)

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
    }
}
