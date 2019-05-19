package jp.cordea.mlkitdemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import kotlinx.android.synthetic.main.fragment_language_identification.*

class LanguageIdentificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_language_identification, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            val text = editText.text.toString()
            identifyLanguage(text)
        }
    }

    private fun identifyLanguage(text: String) {
        FirebaseNaturalLanguage.getInstance()
            .languageIdentification
            .identifyLanguage(text)
            .addOnSuccessListener { textView.text = it }
            .addOnFailureListener { it.printStackTrace() }
    }
}
