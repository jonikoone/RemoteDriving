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

    private var xPosition = 0f
    private var yPosition = 0f

    private var xSize = 0
    private var ySize = 0

    private val fingerPositionPaint = Paint().apply {
        color = Color.BLUE
    }

    private val borderPaint = Paint().apply {
        color = Color.GRAY
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        xSize = w
        ySize = h
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
            drawRoundRect(10f, 10f, xSize - 10f, ySize - 10f, 10f, 10f, borderPaint)
            drawText("Touch position: $xPosition:$yPosition", 10f, 10f, fingerPositionPaint)
        }
        invalidate()
    }


}

