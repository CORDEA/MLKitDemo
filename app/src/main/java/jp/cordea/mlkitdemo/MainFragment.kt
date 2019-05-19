package jp.cordea.mlkitdemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textRecognitionButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.textRecognitionFragment)
        )
        faceDetectionButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.faceDetectionFragment)
        )
        barcodeScanningButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.barcodeScanningFragment)
        )
        imageLabelingButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.imageLabelingFragment)
        )
        objectDetectionAndTrackingButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.objectDetectionAndTrackingFragment)
        )
        landmarkRecognitionButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.landmarkRecognitionFragment)
        )
        languageIdentificationButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.languageIdentificationFragment)
        )
    }
}
