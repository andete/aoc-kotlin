package aoc2024.day02

import day
import kotlin.math.absoluteValue

fun main() {
    day(2024, 2) {
        part1(2, "example", ::part1)
        part1(479, "input", ::part1)
        part2(4, "example", ::part2)
        part2(531, "input", ::part2)

    }
}

private fun part1(data: List<String>): Int {
    val data = data.map { it.split(" ").map { it.toLong()} }
    return data.count { safeReport1(it) }
}

fun safeReport1(report: List<Long>): Boolean {
    val l1 = report.subList(0, report.size - 1)
    val l2 = report.subList(1, report.size)
    val increasing = l1.zip(l2).all { it.first <= it.second }
    val decreasing = l1.zip(l2).all { it.first >= it.second }
    if (!increasing && !decreasing) {
        return false
    }
    for ((a, b) in l1.zip(l2)) {
        val d = (a - b).absoluteValue
        if (d < 1 || d > 3) {
            return false
        }
    }
    return true
}

private fun part2(data: List<String>): Int {
    val data = data.map { it.split(" ").map { it.toLong()} }
    return data.count { safeReport2(it) }
}

fun safeReport2(report: List<Long>): Boolean {
    if (safeReport1(report)) {
        return true
    }
    for (i in report.indices) {
        val sub = report.filterIndexed { index, lng -> index != i }
        if (safeReport1(sub)) {
            return true
        }
    }
    return false
}