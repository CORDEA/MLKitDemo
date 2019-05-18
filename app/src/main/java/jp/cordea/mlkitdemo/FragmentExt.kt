package jp.cordea.mlkitdemo

import android.content.Intent
import androidx.fragment.app.Fragment

fun Fragment.createImageChooserIntent(): Intent =
    Intent.createChooser(
        Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        },
        "Choose picture"
    )
