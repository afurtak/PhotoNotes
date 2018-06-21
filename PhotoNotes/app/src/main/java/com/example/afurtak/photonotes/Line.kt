package com.example.afurtak.photonotes

open class Line(p1: Point, p2: Point) {

    /*
    Ax + By + C = 0
     */
    protected var a = 0L
    protected var b = 0L
    protected var c = 0L


    init {
        if (p1.x == p2.x) {
            a = 1
            b = 0
            c = -p1.x
        }
        else {
            val v = Point(p1.y - p2.y, -(p1.x - p2.x))
            a = v.x
            b = v.y
            c = -p1.x * v.x - p1.y * v.y
        }
    }

    fun getCommonPoint(line: Line): Point? {
        return when (isParallel(line)) {
            true ->
                    null
            else -> {
                /*
                |a b = -c
                |a b = -c
                 */
                val w = a * line.b - b * line.a
                val wx = -c * line.b + b * line.c
                val wy = -a * line.c + c * line.a

                Point(wx / w, wy / w)
            }
        }
    }

    fun isParallel(line: Line): Boolean {
        return when {
            b == 0L && line.b != 0L -> false
            line.b == 0L && b != 0L -> false
            b == 0L && line.b == 0L -> true
            else -> (a.toDouble() / b) == (line.a.toDouble() / line.b)
        }
    }

    open fun belongs(point: Point) = (a * point.x + b * point.y + c == 0L)

    open fun belongs(x: Long, y: Long) = belongs(Point(x, y))

    fun isVertical() = (b == 0L)
}