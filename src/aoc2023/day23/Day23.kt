package aoc2023.day23

import readInput
import util.*

fun main() {

    run {
        val testInput = readInput(2023, 23, "test1")
        val res = part1(testInput)
        println(res)
        check(94 == res)
    }

    run {
        val testInput = readInput(2023, 23, "test1")
        val res = part1AStar(testInput)
        println(res)
        check(94 == res)
    }

    run {
        val input = readInput(2023, 23)
        val res = part1(input)
        println(res)
        check(2010 == res)
    }

//    run {
//        val input = readInput(2023, 23)
//        val res = part1AStar(input)
//        println(res)
//        check(2010 == res)
//    }

//    run {
//        val testInput = readInput(2023, 23, "test1")
//        val res = part2(testInput)
//        println(res)
//        check(154 == res)
//    }

    run {
        val testInput = readInput(2023, 23, "test1")
        val res = part2AStar(testInput)
        println(res)
        check(154 == res)
    }

    run {
        val input = readInput(2023, 23)
        val res = part2AStar(input)
        println(res)
        check(2010 == res)
    }
}

private enum class TileType(val c: Char, val d: Direction? = null) {
    Start('S'),
    End('E'),
    Path('.'),
    Forest('#'),
    SlopeNorth('^', Direction.North),
    SlopeSouth('v', Direction.South),
    SlopeWest('<', Direction.West),
    SlopeEast('>', Direction.East);

    val path get() = this != Forest && this.d == null
    val pathOrSlope get() = this != Forest
}

private data class Cell(override val location: Location, val type: TileType) : Located {
    override fun toChar(visited: List<Located>): String {
        return if (this in visited) {
            "\u001b[31m${type.c}\u001b[0m"
        } else {
            "${type.c}"
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
    val start = map.rows.flatten().single { it.type == TileType.Start }
    val visited = mutableListOf<Cell>()
    val path = search2(map, start, visited)!!
    map.show(path)
    return path.size - 1
}

private fun searchAStar(map: Map): Int {
    val start = map.rows.flatten().single { it.type == TileType.Start }
    val end = map.rows.flatten().single { it.type == TileType.End }
    fun cost(c: Cell, d: Cell) = 1
    fun heuristic(c: Cell, visited: List<Cell>) = 0 // (-(map.xSize - c.location.x + map.ySize - c.location.y))// + visited.size
    fun neighbours(c: Cell, visited: List<Cell>): List<Cell> {
        return map.neighbours(c).filter { (direction, newCell) ->
            val slopeOk = if (!slopesAreEasy) {
                newCell.type.path || newCell.type.d == direction
            } else {
                newCell.type.pathOrSlope
            }
            val visitedOk = newCell !in visited
            slopeOk && visitedOk
        }.map { it.second }
    }

    val path = SearchFor2023Day23.path(start, end, ::cost, ::neighbours, ::heuristic)
    map.show(path)
    return path.size - 1
}

private var slopesAreEasy = false

private fun search2(map: Maze<Cell>, cell: Cell, visited: MutableList<Cell>): List<Cell>? {
    visited.add(cell)
    if (cell.type == TileType.End) {
        return listOf(cell)
    }
    val candidates = map.neighbours(cell).filter { (direction, newCell) ->
        val slopeOk = if (!slopesAreEasy) {
            newCell.type.path || newCell.type.d == direction
        } else {
            newCell.type.pathOrSlope
        }
        val visitedOk = newCell !in visited
        slopeOk && visitedOk
    }
    if (candidates.isEmpty()) {
        return null
    }
    var bestPath: List<Cell> = emptyList()
    for (candidate in candidates) {
        val s = search2(map, candidate.second, visited.toMutableList())
        if (s != null && s.size > bestPath.size) {
            bestPath = s
        }
    }
    if (bestPath.isEmpty()) {
        return null
    }
    return listOf(cell) + bestPath
}

private fun part1(input: List<String>): Int {
    slopesAreEasy = false
    val map = parse(input)
    map.show()
    return search(map)
}

private fun part1AStar(input: List<String>): Int {
    slopesAreEasy = false
    val map = parse(input)
    return searchAStar(map)
}

private fun part2(input: List<String>): Int {
    slopesAreEasy = true
    val map = parse(input)
    return search(map)
}

private fun part2AStar(input: List<String>): Int {
    slopesAreEasy = true
    val map = parse(input)
    return searchAStar(map)
}
