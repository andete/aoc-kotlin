package be.damad.aoc2023.aoc03

private val testData = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598..""".split('\n')

private val Char.symbol get() = !(this.isDigit() || this == '.')
private val neighbours = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
private fun List<String>.at(x: Int, y: Int): Char = this.getOrNull(y)?.getOrNull(x) ?: '.'

private fun adjacentToSymbol(data: List<String>, x: Int, y: Int): Boolean {
    val neighbours = neighbours.map {
        data.at(x + it.first, y + it.second)
    }
    return neighbours.any { it.symbol }
}

private fun calculatePart1(data: List<String>): Int {
    val y = data.size
    val x = data[0].length
    val numbers = mutableListOf<Int>()
    var number = ""
    for (yIndex in data.indices) {
        val line = data[yIndex]
        for (xIndex in 0..line.length) {
            val c = line.getOrNull(xIndex) ?: '.'
            if (c.isDigit()) {
                number += c
            } else if (number.isNotEmpty()) {
                val start = xIndex - number.length
                if ((start until xIndex).any { xIndex2 -> adjacentToSymbol(data, xIndex2, yIndex) }) {
                    numbers += number.toInt()
                }
                number = ""
            }
        }
    }
    return numbers.sum()
}

private fun calculatePart2(data: List<String>): Int {
    data class Location(val x: Int, val y: Int)
    data class LocatedInt(val x: Int, val y: Int, val i: Int)

    var number = ""
    val numbers = mutableMapOf<Location, MutableList<LocatedInt>>()
    for (yIndex in data.indices) {
        val line = data[yIndex]
        for (xIndex in 0..line.length) {
            val c = line.getOrNull(xIndex) ?: '.'
            if (c.isDigit()) {
                number += c
            } else if (number.isNotEmpty()) {
                val start = xIndex - number.length
                for (xIndex2 in (start until xIndex)) {
                    numbers.getOrPut(Location(xIndex2, yIndex)) { mutableListOf() }
                        .add(LocatedInt(start, yIndex, number.toInt()))
                }
                number = ""
            }
        }
    }
    var result = 0
    for (yIndex in data.indices) {
        val line = data[yIndex]
        for (xIndex in line.indices) {
            val c = line[xIndex]
            if (c == '*') {
                val n = neighbours.flatMap {
                    numbers.getOrDefault(Location(xIndex + it.first, yIndex + it.second), listOf())
                }.distinct()
                if (n.size == 2) {
                    result += n[0].i * n[1].i

                }
            }
        }
    }
    return result
}

fun main() {
    check(4361 == calculatePart1(testData))
    val res = calculatePart1(aoc03data)
    println(res)
    check(527446 == res)
    check(467835 == calculatePart2(testData))
    val res2 = calculatePart2(aoc03data)
    println(res2)
    check(73201705 == res2)
}
