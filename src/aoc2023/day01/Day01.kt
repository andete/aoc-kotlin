package aoc2023.day01

import readInput
import util.digitStringToInt


fun main() {

    run {
        val input = readInput(2023, 1, "example")
        val res = part1(input)
        println(res)
        assert(142 == res)
    }

    run {
        val input = readInput(2023, 1, "input")
        val res = part1(input)
        println(res)
        assert(56108 == res)
    }

    run {
        val input = readInput(2023, 1, "example2")
        val res = part2(input)
        println(res)
        assert(281 == res)
    }

    run {
        val input = readInput(2023, 1, "input")
        val res = part2(input)
        println(res)
        assert(55652 == res)
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