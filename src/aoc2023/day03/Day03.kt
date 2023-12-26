package aoc2023.day03

import readInput
import util.*

private fun part1(input: List<String>): Int {
    val engine = parseCharMaze(input)
    val numbers = mutableListOf<Int>()
    var number = ""
    for (y in engine.yIndices) {
        // end inclusive to always clear number
        for (x in 0..engine.xSize) {
            val c = engine.at(x, y)?.t ?: '.'
            if (c.isDigit()) {
                number += c
            } else if (number.isNotEmpty()) {
                val start = x - number.length
                if ((start until x).any { xIndex2 -> adjacentToSymbol(engine, xIndex2, y) }) {
                    numbers += number.toInt()
                }
                number = ""
            }
        }
    }
    return numbers.sum()
}

private fun part2(input: List<String>): Int {
    val engine = parseCharMaze(input)
    val numberEngine = createItemMaze(engine.xSize, engine.ySize) { _, _ -> 0 }
    var number = ""
    for (y in engine.yIndices) {
        for (x in 0..engine.xSize) {
            val c = engine.at(x, y)?.t ?: '.'
            if (c.isDigit()) {
                number += c
            } else if (number.isNotEmpty()) {
                val start = x - number.length
                val n = number.toInt()
                number = ""
                if ((start until x).any { xIndex2 -> adjacentToSymbol(engine, xIndex2, y) }) {
                    for (x2 in start until x) {
                        numberEngine.at(x2, y)!!.t = n
                    }
                }
                number = ""
            }
        }
    }
    var result = 0
    for (yIndex in engine.yIndices) {
        for (xIndex in engine.xIndices) {
            val cell = engine.at(xIndex, yIndex)
            val c = cell?.t
            if (c == '*') {
                val numbers = numberEngine.neighbours2(numberEngine.at(xIndex, yIndex)!!).map {
                    it.second.t
                }.filter { it > 0 }.distinct()

                if (numbers.size == 2) {
                    result += numbers[0] * numbers[1]
                }
            }
        }
    }
    return result
}

private val Char.symbol get() = !(this.isDigit() || this == '.')

fun adjacentToSymbol(engine: CharMaze, x: Int, y: Int) = engine.neighbours2(engine.at(x, y)!!).any {
    it.second.t.symbol
}


fun main() {
    run {
        val input = readInput(2023, 3, "example")
        val res = part1(input)
        println(res)
        check(4361 == res)
    }

    run {
        val input = readInput(2023, 3)
        val res = part1(input)
        println(res)
        check(527446 == res)
    }

    run {
        val input = readInput(2023, 3, "example")
        val res = part2(input)
        println(res)
        check(467835 == res)
    }

    run {
        val input = readInput(2023, 3)
        val res = part2(input)
        println(res)
        check(73201705 == res)
    }
}