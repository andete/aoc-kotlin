package aoc2023.day06

import day
import kotlin.math.ceil
import kotlin.math.sqrt

private val testTimes = listOf(7L, 15L, 30L)
private val testDistances = listOf(9L, 40L, 200L)

private val times = listOf(56L, 71L, 79L, 99L)
private val distances = listOf(334L, 1135L, 1350L, 2430L)

private fun calculateOne(time: Long, distance: Long): Long {
    var res = 0L
    for (t in 1 until time) {
        val d = (time - t) * t
        if (d > distance) {
            res++
        }
    }
    return res
}

private fun calculate(times: List<Long>, distances: List<Long>): Long {
    return times.zip(distances).map { calculateOne(it.first, it.second) }.fold(1L) { a, b -> a * b }
}

private fun alt1(time: Long, distance: Long): Long {
    val x1 = sqrt((time * time - 4 * distance).toDouble())
    val y1 = ((time - x1) / 2).toLong()
    val y2 = ceil(((x1 + time) / 2)).toLong()
    return y2 - y1 - 1
}

fun main() {
    day(2023, 6) {
        part1("test", 288L) {
            calculate(testTimes, testDistances)
        }
        part1("input", 211904L) {
            calculate(times, distances)
        }
        part2("input", 43364472L) {
            calculateOne(56717999L, 334113513502430L)
        }
        part2("input/alt", 43364472L) {
            alt1(56717999L, 334113513502430L)
        }
    }
}