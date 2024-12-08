package aoc2024.day01

import day
import kotlin.math.absoluteValue

fun main() {
    day(2024, 1) {
        part1(11, "example", ::part1)
        part1(2057374, "input", ::part1)
        part2(31, "example", ::part2)
        part2(23177084, "input", ::part2)

    }
}

private fun part1(data: List<String>): Long {
    val data = data.map {
        it.split("   ").map { it.toLong() }
    }
    val l1 = data.map { it[0] }.sorted()
    val l2 = data.map { it[1] }.sorted()
    return l1.zip(l2).sumOf { (a, b) -> (a - b).absoluteValue }
}

private fun part2(data: List<String>): Long {
    val data = data.map {
        it.split("   ").map { it.toLong() }
    }
    val l1 = data.map { it[0] }.sorted()
    val l2 = data.map { it[1] }.sorted()
    return l1.sumOf {
        it * l2.count { t -> t == it }
    }
}