package jp.cordea.mlkitdemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textRecognitionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_textRecognitionFragment)
        }
        faceDetectionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_faceDetectionFragment)
        }
        barcodeScanningButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_barcodeScanningFragment)
        }
        imageLabelingButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_imageLabelingFragment)
        }
        objectDetectionAndTrackingButton.setOnClickListener {
            findNavController()
                .navigate(R.id.action_mainFragment_to_objectDetectionAndTrackingFragment)
        }
    }
}
