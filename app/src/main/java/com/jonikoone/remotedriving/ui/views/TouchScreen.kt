package com.jonikoone.remotedriving.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.jonikoone.remotedriving.data.Offset
import kotlinx.coroutines.*

/**
 * TODO: document your custom view class.
 */
class TouchScreen(context: Context, attr: AttributeSet) : View(context, attr) {

    lateinit var sendPositionMouse: (Offset) -> Unit
    lateinit var sendScrollWheel: (Offset) -> Unit

    lateinit var sendPressMouseLeftButton: () -> Unit
    lateinit var sendReleaseMouseLeftButton: () -> Unit
    lateinit var sendClickMouseLeftButton: () -> Unit

//    lateinit var sendClickMouseRightButton: () -> Unit

    private val doubleClickDelay = 200L

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

    var pressJob: Job? = null
    var clickJob: Job? = null
    var scroll = false
/*
* отправляет положение при движении по экрану
* оправляет нажатие или зажатие на ЛКМ
* */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {

            when(action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    Log.e("Multi-touch", "down singl")

                    xPosition = x
                    yPosition = y

                    if (pressJob?.isActive == true) {
                        clickJob?.cancel()
                        pressJob?.cancel()
                        sendPressMouseLeftButton()
                    } else {
                        pressJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(doubleClickDelay)
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (pressJob?.isActive == true){
                        clickJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(doubleClickDelay)
                            sendClickMouseLeftButton()
                        }
                    }
                    sendReleaseMouseLeftButton()
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    Log.e("Multi-touch", "down")
                    scroll = true
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    Log.e("Multi-touch", "up")
                    scroll = false
                }
                MotionEvent.ACTION_MOVE -> {
                    if (scroll){
                        sendScrollWheel(
                            Offset(
                                x = x - xPosition,
                                y = y - yPosition
                            )
                        )
                    } else {
                        sendPositionMouse(
                            Offset(
                                x = x - xPosition,
                                y = y - yPosition
                            )
                        )
                    }
                    xPosition = x
                    yPosition = y
                }
            }
        }
        return true
//        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawRoundRect(10f, 10f, xSize - 10f, ySize - 10f, 10f, 10f, borderPaint)
            //drawText("Touch position: $xPosition:$yPosition", 10f, 10f, fingerPositionPaint)
        }
        //invalidate()
    }


}

