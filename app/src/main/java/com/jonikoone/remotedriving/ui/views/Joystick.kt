package com.jonikoone.remotedriving.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jonikoone.remotedriving.data.Offset

class Joystick(context: Context, attr: AttributeSet) : View(context, attr) {

    var middleX: Float = 0F
    var middleY: Float = 0F

    var xPosition = middleX
    var yPosition = middleY

    val paint = Paint().apply {
        this.color = Color.BLUE
    }

    var callback: ((Offset) -> Unit)? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        middleX = w / 2F
        middleY = h / 2F
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    xPosition = event.x
                    yPosition = event.y
                }
                MotionEvent.ACTION_UP -> {
                    xPosition = middleX
                    yPosition = middleY
                }
            }
        }
        return false
    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawText(
                "Middle point: $middleX:$middleY\nPosition: $xPosition:$yPosition",
                10f,
                10f,
                paint
            )
            drawCircle(xPosition, yPosition, 30F, paint)
        }
        callback?.invoke(
            Offset(
                xPosition - middleX,
                yPosition - middleY
            )
        )
        invalidate()
    }

}