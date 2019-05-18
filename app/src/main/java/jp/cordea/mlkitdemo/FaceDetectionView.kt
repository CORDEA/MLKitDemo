package jp.cordea.mlkitdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class FaceDetectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val srcRect = Rect()
    private val destRect = Rect()
    private val paint = Paint().apply {
        color = context.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        strokeWidth = 2.0f
    }

    private var image: FirebaseVisionImage? = null
    private var boxes: List<Rect>? = null

    fun drawBoundingBox(image: FirebaseVisionImage, boxes: List<Rect>) {
        this.image = image
        this.boxes = boxes
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val image = image ?: return
        val boxes = boxes ?: return
        val bitmap = image.bitmap
        srcRect.set(0, 0, bitmap.width, bitmap.height)
        destRect.set(0, 0, width, height)
        canvas.drawBitmap(bitmap, srcRect, destRect, null)
        val widthRatio = width.toFloat() / bitmap.width
        val heightRatio = height.toFloat() / bitmap.height
        boxes.forEach {
            canvas.drawRect(
                it.left * widthRatio,
                it.top * heightRatio,
                it.right * widthRatio,
                it.bottom * heightRatio,
                paint
            )
        }
    }
}
