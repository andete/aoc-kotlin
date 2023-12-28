package aoc2023.day13

import day
import util.*
import kotlin.math.min

@JvmInline
private value class Cell(val value: Boolean): WithChar {
    override val c get() = if (value) { '#' } else { '.' }

}


private typealias Map = ItemMaze<Cell>

private fun Map.row(y: Int) = rows.getOrNull(y)?.map { it.t }
private fun Map.column(x: Int) = rows.mapNotNull { it.getOrNull(x)?.t }

private fun Map.smudge(x: Int, y: Int): Map {
    return copy(rows = rows.mapIndexed { yIndex, row ->
        if (yIndex == y) {
            row.mapIndexed { xIndex, cell ->
                if (xIndex == x) {
                    cell.copy(t = Cell(!cell.t.value))
                } else {
                    cell
                }
            }
        } else {
            row.toList()
        }
    })
}

private fun parse(data: List<String>): List<Map> {
    val res = mutableListOf<Map>()
    var cells = mutableListOf<List<Cell>>()
    var y = 0
    for (line in data) {
        if (line.isEmpty()) {
            res.add(makeItemMaze(cells.toList()))
            cells.clear()
            y = 0
        } else {
            cells.add(line.mapIndexed { x, c -> Cell(c == '#') })
            y++
        }
    }
    res.add(makeItemMaze(cells))
    return res
}

private fun checkSplitY(map: Map, y1: Int, y2: Int): Boolean {
    var y1 = y1
    var y2 = y2
    while (y1 > 0 && y2 < map.ySize - 1) {
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
    while (x1 > 0 && x2 < map.xSize - 1) {
        x1--
        x2++
        if (map.column(x1) != map.column(x2)) {
            return false
        }
    }
    return true
}

private fun summarizeY(map: Map): Int {
    val r1 = 0 until map.ySize - 1
    val r2 = 1 until map.ySize
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
    val r1 = 0 until map.xSize - 1
    val r2 = 1 until map.xSize
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
    map.show()
    val oldX = summarizeX(map)
    val oldY = summarizeY(map)
    var m = Int.MAX_VALUE
    var m2 = Int.MAX_VALUE
    var ySeen = false
    for (y in map.yIndices) {
        for (x in map.xIndices) {
            val s: Map = map.smudge(x, y)
            val y2 = summarizeY(s)
            val edgeDiff = min(y2, map.ySize - y2)
            val validRange = (y2 - edgeDiff)..(y2 + edgeDiff - 1)
            if (y2 != 0 && y2 != oldY && y in validRange) {
                println("smudge at ($x,$y) y-mirror at $y2")
                s.show()
                return y2 * 100
            }
            val x2 = summarizeX(s)
            if (x2 != 0 && x2 != oldX) {
                println("smudge at ($x,$y) x-mirror at $x2")
                s.show()
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

private fun part1(data: List<Map>): Int {
    val c = data.map { calculate(it) }
    println(c)
    return c.sum()
}

private fun part2(data: List<Map>): Int {
    val c = data.map { calculate2(it) }
    println(c)
    return c.sum()
}

fun main() {
    day(2023, 13) {
        part1(405, "test") { part1(parse(it)) }
        part1(36448, "input") { part1(parse(it)) }
        // TODO: part2
        part2(400, "test") { part2(parse(it)) }
        part2(36448, "input") { part2(parse(it)) }

    }
}