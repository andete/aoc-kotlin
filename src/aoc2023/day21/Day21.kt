package aoc2023.day21

import day
import util.*
import util.location.Location

private enum class Plot(override val c: Char) : WithChar {
    ROCK('#'),
    START('S'),
    EMPTY('.'),
}

private typealias Map = ItemMaze<Plot>

private fun Map.atInfinite(x: Int, y: Int): LocatedItem<Plot> {
    val y2 = ((y % ySize) + ySize) % ySize
    val x2 = ((x % xSize) + xSize) % xSize
    return at(x2, y2)!!.copy(Location(x, y))
}

private fun Map.infiniteNeighbours(it: LocatedItem<Plot>): List<LocatedItem<Plot>> {
    return listOf(
        atInfinite(it.x - 1, it.y),
        atInfinite(it.x + 1, it.y),
        atInfinite(it.x, it.y + 1),
        atInfinite(it.x, it.y - 1)
    ).filter {
        it.t != Plot.ROCK
    }
}

private fun Map.reachableNeighbours(it: LocatedItem<Plot>): List<LocatedItem<Plot>> {
    return neighbours(it).map { it.second}.filter {
        it.t != Plot.ROCK
    }
}

private val Map.start get() = rows.flatten().single { it.t == Plot.START }

private fun walk(map: Map, amount: Int): List<Int> {
    var steppedOn = listOf(map.start)
    val res = mutableListOf<Int>()
    repeat(amount) {
        steppedOn = steppedOn.flatMap {
            map.reachableNeighbours(it)
        }.distinct()
        res.add(steppedOn.size)
    }
    return res
}

private fun walk2(map: Map, amount: Int): List<Int> {
    var steppedOn = setOf(map.start)
    val res = mutableListOf<Int>()
    repeat(amount) {
        val n = mutableSetOf<LocatedItem<Plot>>()
        steppedOn.forEach {
            n.addAll(map.infiniteNeighbours(it))
        }
        steppedOn = n
        res.add(steppedOn.size)
    }
    return res
}

private fun part1(amount: Int, data: List<String>): Int {
    val map = parseEnumMaze<Plot>(data)
    val q = walk(map, amount)
    return q.last()
}

private fun part2(amount: Int, data: List<String>): Int {
    val map = parseEnumMaze<Plot>(data)
    val q = walk2(map, amount)
    return q.last()
}

fun main() {
    day(2023, 21) {
        part1(16, "example") { part1(6, it) }
        part1(3594, "input") { part1(64, it) }
        part2(16, "example:6") { part2(6, it) }
        part2(50, "example:10") { part2(10, it) }
        part2(1594, "example:50") { part2(50, it) }
        part2(6536, "example:100") { part2(100, it) }
        part2(167004, "example:500") { part2(500, it) }
        part2(668697, "example:1000") { part2(1000, it) }
        part2(16733044, "example:5000") { part2(5000, it) }
        part2(16733044, "example:26501365") { part2(26501365, it) }
    }
}