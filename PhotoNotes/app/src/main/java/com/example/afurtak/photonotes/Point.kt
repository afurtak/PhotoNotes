package com.example.afurtak.photonotes

class Point(var x: Long, var y: Long) {
    constructor() : this(0, 0)

    fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    fun equals(other: Point): Boolean {
        return x == other.x && y == other.y
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}