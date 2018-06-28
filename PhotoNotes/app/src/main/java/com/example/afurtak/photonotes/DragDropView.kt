package com.example.afurtak.photonotes

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import org.opencv.core.Point

class DragDropView(context: Context, attributes: AttributeSet) : Button(context, attributes) {

    init {
        isClickable = false
        setBackgroundColor(Color.BLACK)
    }

    private var dX = 0F
    private var dY = 0F

    fun getRescalePoint(realWidth: Int, realHeight: Int, showingWidth: Int, showingHeight: Int): Point {
        return Point((realWidth * x / showingWidth).toDouble(), (realHeight * y / showingHeight).toDouble())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {

            MotionEvent.ACTION_DOWN -> {
                dX = x - event.rawX
                dY = y - event.rawY
            }

            MotionEvent.ACTION_MOVE ->
                animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()

            else -> return false
        }
        return true
    }
}