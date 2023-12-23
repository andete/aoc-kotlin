package aoc2023.day23

import println
import readInput
import util.Direction
import util.Located
import util.Location
import util.Maze
import kotlin.math.max

fun main() {

    run {
        // test if implementation meets criteria from the description, like:
        val testInput = readInput(2023, 23, "test1")
        val res = part1(testInput)
        println(res)
        check(94 == res)
    }

    run {
        val input = readInput(2023, 23)
        val res = part1(input)
        println(res)
        check(94 == res)
    }
}

private enum class TileType(val c: Char, val d: Direction? = null) {
    Start('S'),
    Path('.'),
    Forest('#'),
    SlopeNorth('^', Direction.North),
    SlopeSouth('v', Direction.South),
    SlopeWest('<', Direction.West),
    SlopeEast('>', Direction.East),
}

private data class Cell(override val location: Location, val type: TileType) : Located {
    override fun toChar(visited: List<Located>): Char {
        if (this in visited) {
            return 'O'
        } else {
            return type.c
        }
    }
}

private typealias Map = Maze<Cell>

private fun parse(input: List<String>): Map {
    val cells = input.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            Cell(Location(x, y),
                TileType.entries.single { it.c == c })
        }
    }
    return Map(cells)
}

private fun search(map: Map): Int {
    val start = map.cells.flatten().single { it.type == TileType.Start }
    val visited = mutableListOf<Cell>()
    return search2(map, start, visited) - 1
}

private fun search2(map: Maze<Cell>, cell: Cell, visited: MutableList<Cell>): Int {
    visited.add(cell)
    val candidates = map.neighbours(cell).filter { (direction, newCell) ->
        val slopeOk = newCell.type == TileType.Path || newCell.type.d == direction
        val visitedOk = newCell !in visited
        slopeOk && visitedOk
    }
    var m = 0
    for (candidate in candidates) {
        val s = search2(map, candidate.second, visited.toMutableList())
        m = max(s, m)
    }
    return 1 + m
}

private fun part1(input: List<String>): Int {
    val map = parse(input)
    map.show()
    return search(map)
}

private fun part2(input: List<String>): Int {
    return input.size
}
