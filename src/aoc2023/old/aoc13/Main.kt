package be.damad.aoc2023.aoc13

import kotlin.math.min

private val testData = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#""".split('\n')

private val testData2 = """#.#.####.#.#.##
.#.##..##.#...#
.#..#..#..#.#..
.##..##..##.#.#
....####.......
##...##...##..#
##...##...##..#
....####.......
.##..##..##.#.#
.#..#..#..#.#..
.#.##..##.#...#
#.#.####.#.#.#.
..#.#..#.#....#
##.#.##.#.####.
..#..##..#....#
#..#.##.#..#.##
#.#.####.#.#...""".split('\n')

private data class Cell(val x: Int, val y: Int, val rock: Boolean) {
    override fun toString() = if (rock) { "#" } else { "." }
}
private data class Map(val cells: List<List<Cell>>) {
    fun at(x: Int, y: Int) = cells.getOrNull(y)?.getOrNull(x)

    fun row(y: Int) = cells.getOrNull(y)?.map { it.rock }
    fun column(x: Int) = cells.mapNotNull { it.getOrNull(x)?.rock }
    fun smudge(x: Int, y: Int): Map {
        return copy(cells = cells.mapIndexed { yIndex, row ->
            if (yIndex == y) {
                row.mapIndexed { xIndex, cell ->
                    if (xIndex == x) {
                        cell.copy(rock = !cell.rock)
                    } else {
                        cell
                    }
                }
            } else {
                row.toList()
            }
        })
    }

    fun print() {
        for (row in cells) {
            val l = row.joinToString("") { "$it" }
            println(l)
        }
        println()
    }

}

private fun parse(data: List<String>): List<Map> {
    val res = mutableListOf<Map>()
    var cells = mutableListOf<List<Cell>>()
    var y = 0
    for (line in data) {
        if (line.isEmpty()) {
            res.add(Map(cells.toList()))
            cells.clear()
            y = 0
        } else {
            cells.add(line.mapIndexed { x, c -> Cell(x, y, c == '#') })
            y++
        }
    }
    res.add(Map(cells))
    return res
}

private fun checkSplitY(map: Map, y1: Int, y2: Int): Boolean {
    var y1 = y1
    var y2 = y2
    while (y1 > 0 && y2 < map.cells.size - 1) {
        y1--
        y2++
        if (map.row(y1) != map.row(y2)) {
            return false
        }
    }
    return true
}

private fun checkSplitX(map: Map, x1: Int, x2: Int): Boolean {
    var x1 = x1
    var x2 = x2
    while (x1 > 0 && x2 < map.cells[0].size - 1) {
        x1--
        x2++
        if (map.column(x1) != map.column(x2)) {
            return false
        }
    }
    return true
}

private fun summarizeY(map: Map): Int {
    val r1 = 0 until map.cells.size - 1
    val r2 = 1 until map.cells.size
    for (pair in r1.zip(r2)) {
        if (map.row(pair.first) == map.row(pair.second)) {
            if (checkSplitY(map, pair.first, pair.second)) {
                return pair.first + 1

            }
        }
    }
    return 0
}

private fun summarizeX(map: Map): Int {
    val r1 = 0 until map.cells[0].size - 1
    val r2 = 1 until map.cells[0].size
    for (pair in r1.zip(r2)) {
        if (map.column(pair.first) == map.column(pair.second)) {
            if (checkSplitX(map, pair.first, pair.second)) {
                return pair.first + 1
            }
        }
    }
    return 0
}

private fun calculate2(map: Map): Int {
    map.print()
    val oldX = summarizeX(map)
    val oldY = summarizeY(map)
    var m = Int.MAX_VALUE
    var m2 = Int.MAX_VALUE
    var ySeen = false
    for (y in map.cells.indices) {
        for (x in map.cells[0].indices) {
            val s: Map = map.smudge(x, y)
            val y2 = summarizeY(s)
            val edgeDiff = min(y2, map.cells.size - y2)
            val validRange = (y2 - edgeDiff)..(y2 + edgeDiff - 1)
            if (y2 != 0 && y2 != oldY && y in validRange) {
                println("smudge at ($x,$y) y-mirror at $y2")
                s.print()
                return y2 * 100
            }
            val x2 = summarizeX(s)
            if (x2 != 0 && x2 != oldX) {
                println("smudge at ($x,$y) x-mirror at $x2")
                s.print()
                return x2
            }
//            if (!ySeen) {
//                val x2 = summarizeX(s)
//                if (x2 in 1..<m2 /* && x2 != old */) {
//                    m2 = x2
//                    m = x2
//                }
//            }
//            val y2 = summarizeY(s)
//            if (!ySeen && y2 > 0 /* && y2 * 100 != old */) {
//                ySeen = true
//                m = y2 * 100
//                m2 = y2
//            } else if (y2 in 1..<m2 /* && y2 * 100 != old */) {
//                m = y2 * 100
//                m2 = y2
//            }
        }
    }
    if (m == Int.MAX_VALUE) {
        return 0
        error("none found")
    }
    return m
}

private fun calculate(map: Map) = summarizeX(map) + 100 * summarizeY(map)

private fun calculate(data: List<Map>): Int {
    val c = data.map { calculate(it) }
    println(c)
    return c.sum()
}

private fun calculate2(data: List<Map>): Int {
    val c = data.map { calculate2(it) }
    println(c)
    return c.sum()
}

fun main() {
    run {
        val res = calculate(parse(testData))
        println(res)
        check(405 == res)
    }

    run {
        val res = calculate2(parse(testData))
        println(res)
        check(400 == res)
    }

    run {
        val res2 = calculate(parse(aoc13data))
        println(res2)
        check(36448 == res2)
    }

    run {
        val res2 = calculate2(parse(aoc13data))
        println(res2)
        check(36448 == res2)
    }
}