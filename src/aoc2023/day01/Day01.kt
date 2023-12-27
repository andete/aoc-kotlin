package aoc2023.day01

import day
import readInput
import util.digitStringToInt


fun main() {

    day(2023, 1) {
        part1(142, "example", ::part1)
        part1(56108, "input", ::part1)
        part2(281, "example2", ::part2)
        part2(55652, "input", ::part2)
    }
}

private fun part1(input: List<String>) = input.sumOf {
    val f = it.find { it.isDigit() }!!
    val l = it.reversed().find { it.isDigit() }!!
    "$f$l".toInt()
}

private fun part2(data: List<String>) = data.sumOf {
    val e = digits(it)
    e.first() * 10 + e.last()
}

private fun digits(data: String): List<Int> {
    val res = mutableListOf<Int>()
    for (i in data.indices) {
        val sub = data.substring(i)
        if (sub[0].isDigit()) {
            res.add(sub[0].digitToInt())
        } else {
            for (d in digitStringToInt.keys) {
                if (sub.startsWith(d)) {
                    res.add(digitStringToInt[d]!!)
                }
            }
        }
    }
    return res
}