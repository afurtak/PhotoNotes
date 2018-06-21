package com.example.afurtak.photonotes

import android.graphics.Bitmap
import android.graphics.Color
import java.lang.Math.abs

fun parseBitmap(leftTop: Point, rightTop: Point, leftBottom: Point, rightBottom: Point, inputBitmap: Bitmap): Bitmap? {
    val top = LineSegment(leftTop, rightTop)
    val bottom = LineSegment(leftBottom, rightBottom)

    val topXBottom = top.getCommonPoint(bottom)

    val right = LineSegment(rightBottom, rightTop)
    val left = LineSegment(leftBottom, leftTop)

    val rightXLeft = right.getCommonPoint(left)


    val outputBitmap= Bitmap.createBitmap(abs((top.p1.x - top.p2.x).toInt()), abs((top.p1.y - top.p2.y).toInt()), Bitmap.Config.RGB_565)
    println("${abs(top.p1.x - top.p2.x)}, ${abs(top.p1.y - top.p2.y)}")
    if (topXBottom != null)
        println("top x bottom: ${topXBottom!!.x}, ${topXBottom.y}")


    if (rightXLeft != null)
        println("right x left: ${rightXLeft!!.x}, ${rightXLeft.y}")

    println("input bitmap: ${inputBitmap.width}, ${inputBitmap.height}")

    if (true) {
        var x = 0
        var y = 0
        var leftSidePoint = left.getNextPoint()
        while (y < abs(top.p1.y - top.p2.y)) {
            x = 0
            //println("$y")
            var topSidePoint = top.getNextPoint()
            while (x < abs(top.p1.x - top.p2.x)) {
                val p = Line(topSidePoint, rightXLeft!!).getCommonPoint(Line(leftSidePoint, topXBottom!!))
                println("p: ${p!!.x}, ${p.y}")
                outputBitmap.setPixel(x, y, inputBitmap.getPixel(p.x.toInt(), p.y.toInt()))
                x++
                topSidePoint = top.getNextPoint()
            }
            y++
            leftSidePoint = left.getNextPoint()
        }
        return outputBitmap
    }
    else {
        print("ASDF")
        return null
    }
}