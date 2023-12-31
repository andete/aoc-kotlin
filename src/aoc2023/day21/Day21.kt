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

private fun Map.infiniteReachableNeighbours(it: LocatedItem<Plot>): List<LocatedItem<Plot>> {
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
    return neighbours(it).map { it.second }.filter {
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

private fun testWalk(map: Map, amount: Int): Int {
    val available = ArrayDeque(listOf(map.start to amount))
    val seen: HashSet<LocatedItem<Plot>> = hashSetOf()
    val res = mutableListOf<LocatedItem<Plot>>()
    while (available.isNotEmpty()) {
        val (plot, a) = available.removeFirst()
        if (a >= 0) {
            if (a % 2 == 0) {
                res.add(plot)
            }
        }
        if (a > 0) {
            for (reachableNeighbour in map.reachableNeighbours(plot)) {
                if (reachableNeighbour in seen || reachableNeighbour.t == Plot.ROCK) {
                    continue
                }
                available.add(reachableNeighbour to (a - 1))
                seen.add(reachableNeighbour)
            }
        }
    }
    return res.size
}

private fun infiniteWalk(map: Map, amount: Int): Int {
    var steppedOn = setOf(map.start)
    var res = 0
    repeat(amount) {
        val n = mutableSetOf<LocatedItem<Plot>>()
        steppedOn.forEach {
            n.addAll(map.infiniteReachableNeighbours(it))
        }
        steppedOn = n
        map.show(steppedOn)
        res = steppedOn.size
    }
    return res
}

private fun testInfiniteWalk(map: Map, amount: Int): Int {
    var steppedOn = setOf(map.start)
    var res = 0
    repeat(amount) {
        val n = mutableSetOf<LocatedItem<Plot>>()
        steppedOn.forEach {
            n.addAll(map.infiniteReachableNeighbours(it))
        }
        steppedOn = n
        res = steppedOn.size
        println(res)
    }
    return res
}

private fun part1(amount: Int, data: List<String>): Int {
    val map = parseEnumMaze<Plot>(data)
    map.visitedChar = 'O'
    val q = walk(map, amount)
    return q.last()
}

private fun part2(amount: Int, data: List<String>): Int {
    val map = parseEnumMaze<Plot>(data)
    map.visitedChar = 'O'
    return infiniteWalk(map, amount)
}

fun main() {
    day(2023, 21) {
        part1(16, "example") { part1(6, it) }
        part1(3594, "input") { part1(64, it) }
        test("pattern", "example") {
            val map = parseEnumMaze<Plot>(it)
            map.visitedChar = 'O'
            testWalk(map, 64)
        }
//        test("pattern", "example") {
//            val map = parseEnumMaze<Plot>(it)
//            map.visitedChar = 'O'
//            testInfiniteWalk(map, 64)
//        }
//        part2(16, "example:6") { part2(6, it) }
//        part2(50, "example:10") { part2(10, it) }
//        part2(1594, "example:50") { part2(50, it) }
//        part2(6536, "example:100") { part2(100, it) }
//        part2(167004, "example:500") { part2(500, it) }
//        part2(668697, "example:1000") { part2(1000, it) }
//        part2(16733044, "example:5000") { part2(5000, it) }
//        part2(16733044, "example:26501365") { part2(26501365, it) }
    }
}