package com.example.afurtak.photonotes

import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class LineSegment(val p1: Point, val p2: Point) : Line(p1, p2) {

    private var lastX = max(p1.x, p2.x)
    private var lastY = max(p1.y, p2.y)

    val function : (Long) -> (Long) = { (it * a + c) / -b }

    override fun belongs(point: Point): Boolean {
        val condition = point.x >= max(p1.x, p2.x) && point.x <= max(p1.x, p2.x)
                && point.y >= max(p1.y, p2.y) && point.y <= max(p1.y, p2.y)
        return super.belongs(point) && condition
    }

    override fun belongs(x: Long, y: Long) = belongs(Point(x, y))

    fun getNextPoint(): Point {
        incrementLastXY()

        return when (isVertical()) {
            true ->
                Point(p1.x, lastY)

            false ->
                Point(lastX, function(lastX))
        }
    }

    fun length(): Float {
        val x = p1.x - p2.x
        val y = p1.y - p2.y
        return sqrt((x * x + y * y).toFloat())
    }

    private fun incrementLastXY() {
        when (isVertical()) {
            true ->
                if (lastY == max(p1.y, p2.y))
                    lastY = min(p1.y, p2.y)
                else
                    lastY++
            else ->
                if (lastX == max(p1.x, p2.x))
                    lastX = min(p1.x, p2.x)
                else
                    lastX++
        }
    }

}