package aoc2023.day24

import day
import readInput
import kotlin.math.sign
import kotlin.math.sqrt

private data class Location(val x: Double, val y: Double) {

    operator fun plus(other: Location) = Location(x + other.x, y + other.y)
    operator fun minus(other: Location) = Location(x - other.x, y - other.y)
    operator fun div(d: Double) = Location(x / d, y / d)
    operator fun times(d: Double) = Location(x * d, y * d)

    fun distance(other: Location): Double {
        return sqrt((other.y * other.y - y * y)) + sqrt((other.x * other.x - x * x))
    }
}

private data class Equation(val b: Double, val c: Double)

private data class Hail(val position: Location, val speed: Location) {

    val equation
        get(): Equation {
            val b = -speed.x / speed.y
            val c = -b * position.y - position.x
            return Equation(b, c)
        }

    fun intersect(other: Hail): Location? {
        if (speed == other.speed) {
            return null
        }
        if (position == other.position) {
            return position
        }
        val e1 = equation
        val e2 = other.equation
        val x = (e1.b * e2.c - e2.b * e1.c) / (e2.b - e1.b)
        val y = (e1.c - e2.c) / (e2.b - e1.b)
        return Location(x, y)
    }

    fun tOfPoint(p: Location): Double {
        val delta = p - position
        return delta.x / speed.x
    }
}

private fun parse(data: List<String>): List<Hail> {
    return data.map { line ->
        val x = line.split("@")
        val p = x[0]
        val v = x[1]
        val pos = p.split(" ", ",").filter { it.isNotEmpty() }.map { it.toLong() }
        val vel = v.split(" ", ",").filter { it.isNotEmpty() }.map { it.toLong() }
        Hail(Location(pos[0].toDouble(), pos[1].toDouble()), Location(vel[0].toDouble(), vel[1].toDouble()))
    }
}

private fun part1(hails: List<Hail>, range: ClosedFloatingPointRange<Double> = 7.0..27.0): Long {
    var res = 0L
    for (i in hails.indices) {
        for (j in (i + 1) until hails.size) {
            val a = hails[i]
            val b = hails[j]
            a.intersect(b)?.let {
                println(it)
                if (it.x in range && it.y in range && a.tOfPoint(it) >= 0.0 && b.tOfPoint(it) >= 0.0) {
                    res++
                }
            }
        }
    }
    return res
}

fun main() {
    day(2023, 24) {
       part1(2L, "test") {
           val hails = parse(it)
           part1(hails)
       }
        part2(13149L, "input") {
            val hails = parse(it)
            part1(hails, 200000000000000.0..400000000000000.0)
        }
    }
}