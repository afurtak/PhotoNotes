package com.example.afurtak.photonotes

open class Line(p1: Point, p2: Point) {

    /*
    Ax + By + C = 0
     */
    protected var a = 0
    protected var b = 0
    protected var c = 0


    init {
        if (p1.x == p2.x) {
            a = 1
            b = 0
            c = -p1.x
        }
        else {
            a = -(p1.x - p2.x)
            b = (p1.y - p2.y)
            c = -a * p1.x - b * p1.y
        }
    }

    fun getCommonPoint(line: Line): Point? {
        return when (isParallel(line)) {
            true ->
                null

            false -> {
                val w = a * line.b - b * line.a
                val wx = -c * line.b + b * line.c
                val wy = -a * line.c + c * line.a

                Point(wx / w, wy / w)
            }
        }
    }

    private fun isParallel(line: Line) = (a / b).toFloat() == (line.a / line.b).toFloat()

    open fun belongs(point: Point) = (a * point.x + b * point.y + c == 0)

    open fun belongs(x: Int, y: Int) = belongs(Point(x, y))

    fun isVertical() = (b == 0)
}