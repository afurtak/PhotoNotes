package com.example.afurtak.photonotes

class Point(var x: Int, var y: Int) {
    constructor() : this(0, 0)

    fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    fun equals(other: Point): Boolean {
        return x == other.x && y == other.y
    }
}