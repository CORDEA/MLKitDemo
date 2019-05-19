package jp.cordea.mlkitdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.objects.FirebaseVisionObject

class ObjectDetectionAndTrackingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val srcRect = Rect()
    private val destRect = Rect()

    private val textPaint = Paint().apply {
        color = context.getColor(android.R.color.white)
        style = Paint.Style.FILL
        textSize = 32.0f
    }
    private val paint = Paint().apply {
        color = context.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        strokeWidth = 2.0f
    }

    private var image: FirebaseVisionImage? = null
    private var objects: List<FirebaseVisionObject>? = null

    fun drawBoundingBox(image: FirebaseVisionImage, objects: List<FirebaseVisionObject>) {
        this.image = image
        this.objects = objects
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val image = image ?: return
        val objects = objects ?: return
        val bitmap = image.bitmap
        srcRect.set(0, 0, bitmap.width, bitmap.height)
        destRect.set(0, 0, width, height)
        canvas.drawBitmap(bitmap, srcRect, destRect, null)
        val widthRatio = width.toFloat() / bitmap.width
        val heightRatio = height.toFloat() / bitmap.height
        objects.forEach {
            val box = it.boundingBox
            canvas.drawRect(
                box.left * widthRatio,
                box.top * heightRatio,
                box.right * widthRatio,
                box.bottom * heightRatio,
                paint
            )
            canvas.drawText(
                categoryToString(it.classificationCategory),
                box.left * widthRatio,
                box.top * heightRatio,
                textPaint
            )
        }
    }

    private fun categoryToString(int: Int): String =
        when (int) {
            FirebaseVisionObject.CATEGORY_FASHION_GOOD -> "fashion good"
            FirebaseVisionObject.CATEGORY_FOOD -> "food"
            FirebaseVisionObject.CATEGORY_HOME_GOOD -> "home good"
            FirebaseVisionObject.CATEGORY_PLACE -> "place"
            FirebaseVisionObject.CATEGORY_PLANT -> "plant"
            FirebaseVisionObject.CATEGORY_UNKNOWN -> "unknown"
            else -> throw IllegalArgumentException()
        }
}
