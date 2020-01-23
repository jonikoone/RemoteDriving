package com.jonikoone.remotedriving.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jonikoone.remotedriving.data.Offset

/**
 * TODO: document your custom view class.
 */
class TouchScreen(context: Context, attr: AttributeSet) : View(context, attr) {

    lateinit var onSendPosotion: (Offset) -> Unit

    var xPosition: Float = 0F
    var yPosition: Float = 0F

    var sckipFirstTouthEvent = true

    val paint = Paint().apply {
        this.color = Color.BLUE
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            if (this.action == MotionEvent.ACTION_DOWN) {
                xPosition = x
                yPosition = y
            }
            val xOffset = x - xPosition
            val yOffset = y - yPosition

            onSendPosotion.invoke(
                Offset(
                    x = xOffset,
                    y = yOffset
                )
            )
            xPosition = x
            yPosition = y
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawText("Touch position: $xPosition:$yPosition", 10f, 10f, paint)
        }
        invalidate()
    }


}

