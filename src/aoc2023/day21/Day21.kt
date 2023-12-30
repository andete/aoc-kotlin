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

private val Map.start get() = rows.flatten().single { it.t == Plot.START }

//
//
//private data class Map(val plots: List<List<Plot>>) {
//    override fun toString() = plots.joinToString("\n") {
//        it.joinToString("") { it.toStringForMap() }
//    }
//
//    fun at(x: Int, y: Int) = plots.getOrNull(y)?.getOrNull(x)
//
//    fun atInfinite(x: Int, y: Int): Plot {
//        val y2 = ((y % plots.size) + plots.size) % plots.size
//        val x2 = ((x % plots[0].size) + plots[0].size) % plots[0].size
//        return plots[y2][x2].copy(x = x, y = y)
//    }
//
//    fun neighbours(it: Plot): List<Plot> {
//        return listOfNotNull(at(it.x - 1, it.y), at(it.x + 1, it.y), at(it.x, it.y + 1), at(it.x, it.y - 1)).filter {
//            !it.rock
//        }
//    }
//
//    fun infiniteNeighbours(it: Plot): List<Plot> {
//        return listOf(
//            atInfinite(it.x - 1, it.y),
//            atInfinite(it.x + 1, it.y),
//            atInfinite(it.x, it.y + 1),
//            atInfinite(it.x, it.y - 1)
//        ).filter {
//            !it.rock
//        }
//    }
//
//    val start = plots.flatten().single { it.start }
//}

private fun walk(map: Map, amount: Int): List<Int> {
    var steppedOn = listOf(map.start)
    val res = mutableListOf<Int>()
    repeat(amount) {
        steppedOn = steppedOn.flatMap {
            map.neighbours(it).map { it.second }
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
        part1(668697, "example") { part2(1000, it) }
    }
}