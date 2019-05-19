package jp.cordea.mlkitdemo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ImageChoosableUiBinder(val fragment: Fragment) {
    companion object {
        private const val REQUEST_CODE = 1
    }

    sealed class Result {
        class ReceivedLocalImage(val uri: Uri) : Result()
    }

    private val _onResult = MutableLiveData<Result>()
    val onResult: LiveData<Result> = _onResult

    fun bind(chooseButton: Button) {
        chooseButton.setOnClickListener {
            fragment.startActivityForResult(
                Intent.createChooser(
                    Intent(Intent.ACTION_GET_CONTENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    },
                    "Choose picture"
                ),
                REQUEST_CODE
            )
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uri = data?.data ?: return
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE) {
            _onResult.value = Result.ReceivedLocalImage(uri)
        }
    }
}
