package be.damad.aoc2023.aoc06

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

private fun alt1(time: Long, distance: Long): Long {
    val x1 = sqrt((time * time - 4 * distance).toDouble())
    val y1 = ((time - x1) / 2).toLong()
    val y2 = ceil(((x1 + time) / 2)).toLong()
    return y2 - y1 - 1
}

private fun calculate(times: List<Long>, distances: List<Long>): Long {
    return times.zip(distances).map { calculateOne(it.first, it.second) }.fold(1L) { a, b -> a * b }
}

private fun calculateAlt(times: List<Long>, distances: List<Long>): Long {
    return times.zip(distances).map { alt1(it.first, it.second) }.fold(1L) { a, b -> a * b }
}

fun main() {
    println(testTimes.zip(testDistances).map { calculateOne(it.first, it.second) })
    check(288L == calculate(testTimes, testDistances))
    check(288L == calculateAlt(testTimes, testDistances))
    println(calculate(times, distances))
    println(calculateOne(56717999L, 334113513502430L))
    println(alt1(56717999L, 334113513502430L))
}