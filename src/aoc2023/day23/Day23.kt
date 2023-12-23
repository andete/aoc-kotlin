package aoc2023.day23

import readInput
import util.*
import kotlin.math.absoluteValue

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

    run {
        val input = readInput(2023, 23)
        val res = part1AStar(input)
        println(res)
        check(2010 == res)
    }

    run {
        val testInput = readInput(2023, 23, "test1")
        val res = part2(testInput)
        println(res)
        check(154 == res)
    }

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

private data class CellWrapper(
    val direction: Direction?,
    val cell: Cell,
    val map: Map,

    ) : InvertedAStar.NeighboursProvider<CellWrapper> {

    override fun toString() = cell.toString()
    override fun neighbours(visited: List<CellWrapper>): List<CellWrapper> {
        val visited = visited.map { it.cell }
        return map.neighbours(cell).filter { (direction, newCell) ->
            val slopeOk = if (!slopesAreEasy) {
                newCell.type.path || newCell.type.d == direction
            } else {
                newCell.type.pathOrSlope
            }
            val visitedOk = newCell !in visited
            slopeOk && visitedOk
        }.map { CellWrapper(it.first, it.second, map) }
    }

    override fun equals(other: Any?) = cell == (other as? CellWrapper)?.cell
    override fun hashCode(): Int {
        return cell.hashCode()
    }
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
    val path = search2(map, start, visited)!!
    map.show(path)
    return path.size - 1
}

private fun searchAStar(map: Map): Int {
    val start = map.cells.flatten().single { it.type == TileType.Start }
    val startW = CellWrapper(Direction.South, start, map)
    val end = map.cells.flatten().single { it.type == TileType.End }
    val endW = CellWrapper(null, end, map)
    fun heuristic(c: CellWrapper) = 0
    fun distance(c: CellWrapper, d: CellWrapper) = 1
    val path = InvertedAStar.path(startW, endW, ::heuristic, ::distance)
    map.show(path.map { it.cell })
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
