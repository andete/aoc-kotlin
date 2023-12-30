package aoc2023.day14

import day
import util.*

private enum class RockType(override val c: Char) : WithChar {
    EMPTY('.'),
    ROUND('O'),
    SQUARE('#'),
}

private typealias Platform = ItemMaze<RockType>

private val Platform.load
    get() = rows.mapIndexed { index, cells ->
        cells.count { it.round } * (cells.size - index)
    }.sum()

private val LocatedItem<RockType>.round get() = t == RockType.ROUND
private val LocatedItem<RockType>.empty get() = t == RockType.EMPTY
private val LocatedItem<RockType>.square get() = t == RockType.SQUARE

private fun tiltNorth(platform: Platform) {
    for (repeat in platform.yIndices) {
        for (y in platform.yIndices.reversed()) {
            val row = platform.rows[y]
            for (x in row.indices) {
                if (row[x].round && platform.at(x, y - 1)?.empty == true) {
                    row[x].t = RockType.EMPTY
                    platform.at(x, y - 1)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun tiltSouth(platform: Platform) {
    for (repeat in platform.yIndices) {
        for (y in platform.yIndices) {
            val row = platform.rows[y]
            for (x in row.indices) {
                if (row[x].round && platform.at(x, y + 1)?.empty == true) {
                    row[x].t = RockType.EMPTY
                    platform.at(x, y + 1)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun tiltEast(platform: Platform) {
    for (repeat in platform.xIndices) {
        for (x in platform.xIndices) {
            for (y in platform.yIndices) {
                if (platform.at(x, y)!!.round && platform.at(x + 1, y)?.empty == true) {
                    platform.at(x, y)!!.t = RockType.EMPTY
                    platform.at(x + 1, y)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun tiltWest(platform: Platform) {
    for (repeat in platform.xIndices) {
        for (x in platform.xIndices.reversed()) {
            for (y in platform.yIndices) {
                if (platform.at(x, y)!!.round && platform.at(x - 1, y)?.empty == true) {
                    platform.at(x, y)!!.t = RockType.EMPTY
                    platform.at(x - 1, y)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun repeatingSequence(ps: List<Platform>): Int? {
    var i = 1
    val j = ps.indices.last
    while (j - i * 2 >= 0) {
        val x1 = ps.subList(j - i * 2, j - i)
        val x2 = ps.subList(j - i, j)
        if (x1 == x2) {
            return i
        }
        i++
    }
    return null
}

private fun manyTilts(p: Platform, amount: Int = 1000000000): Platform? {
    val seen = mutableListOf<Platform>()
    repeat(amount) {
        val p2 = p.copy(rows = p.rows.map { it.map { it.copy() } })
        seen.add(p2)
        repeatingSequence(seen)?.let { interval ->
            return seen[it - interval + (amount - it) % interval]
        }
        tiltNorth(p)
        tiltWest(p)
        tiltSouth(p)
        tiltEast(p)
    }
    return p
}

fun main() {
    day(2023, 14) {
        part1(136, "test") {
            val p: Platform = parseEnumMaze(it)
            tiltNorth(p)
            p.load
        }
        part1(105003, "input") {
            val p: Platform = parseEnumMaze(it)
            tiltNorth(p)
            p.load
        }
        part2(64, "test") {
            val p: Platform = parseEnumMaze(it)
            val res = manyTilts(p)!!
            res.load
        }
        part2(93742, "input") {
            val p: Platform = parseEnumMaze(it)
            val res = manyTilts(p)!!
            res.load
        }
    }
}