package aoc2023.day18

import day
import util.location.Direction4
import util.location.Location
import kotlin.math.max
import kotlin.math.min

private data class Command(val direction: Direction4, val amount: Long, val color: String)

private fun parse(data: List<String>): List<Command> {
    return data.map { line ->
        val s1 = line.split(' ')
        val d = when (s1[0]) {
            "L" -> Direction4.West
            "R" -> Direction4.East
            "U" -> Direction4.North
            "D" -> Direction4.South
            else -> error("unknown direction ${s1[0]}")
        }
        Command(d, s1[1].toLong(), s1[2])
    }
}

private fun parse2(data: List<String>): List<Command> {
    return data.map { line ->
        val s1 = line.split(' ', ')', ')', '#')
        val s2 = s1[3]
        val hex = s2.substring(0, 5)
        val dir = s2.last()
        val direction = when (dir) {
            '0' -> Direction4.East
            '1' -> Direction4.South
            '2' -> Direction4.West
            '3' -> Direction4.North
            else -> error("unknown direction")
        }
        val amount = hex.toLong(16)
        Command(direction, amount, "")
    }
}

private fun follow(commands: List<Command>): List<Location> {
    var location = Location(0, 0)
    val res = mutableListOf(location)
    for (command in commands) {
        repeat(command.amount.toInt()) {
            location += command.direction
            res.add(location)
        }
    }
    return res
}

private fun corners(commands: List<Command>): List<Location> {
    var location = Location(0, 0)
    val res = mutableListOf(location)
    for (command in commands) {
        location += command.direction.location * command.amount
        res.add(location)
    }
    return normalize(res.distinct())
}

private enum class CellState(val c: Char) {
    OUTSIDE('O'),
    DUG('#'),
    UNKNOWN('.')
}

private interface Cell {
    val location: Location
    var state: CellState
}

private data class Cell1(override val location: Location, override var state: CellState) : Cell {
    override fun toString() = "${state.c}"
}

private data class Cell2(override val location: Location, override var state: CellState, var surface: Long) : Cell {
    override fun toString() = "${state.c}"
}

private interface Lagoon {
    val xSize: Int
    val ySize: Int
    val cells: List<List<Cell>>

    fun at(x: Int, y: Int) = cells.getOrNull(y)?.getOrNull(x)
    fun at(l: Location) = cells.getOrNull(l.y)?.getOrNull(l.x)

    val digSize: Long
}

private data class Lagoon1(override val cells: List<List<Cell1>>) : Lagoon {

    override val ySize = cells.size
    override val xSize = cells[0].size

    fun print() {
        for (row in cells) {
            println(row.joinToString(""))
        }
        println()
    }

    override val digSize get() = cells.flatten().count { it.state == CellState.DUG }.toLong()
}

private data class Lagoon2(override val cells: List<List<Cell2>>, val xn: List<Int>, val yn: List<Int>) : Lagoon {

    override val ySize = cells.size
    override val xSize = cells[0].size

    fun print() {
        for (row in cells) {
            println(row.joinToString(""))
        }
        println()
    }

    override val digSize
        get() = cells.flatten().sumOf {
            if (it.state == CellState.DUG) {
                it.surface
            } else {
                0L
            }
        }
}

private fun normalize(l: List<Location>): List<Location> {
    val minX = l.minOf { it.x }
    val minY = l.minOf { it.y }
    return l.map { it.copy(x = it.x - minX, y = it.y - minY) }
}

private fun toLagoon1(visited: List<Location>): Lagoon1 {
    val normalized = normalize(visited)
    val maxX = normalized.maxOf { it.x }
    val maxY = normalized.maxOf { it.y }
    val cells = (0..maxY).map { y ->
        (0..maxX).map { x ->
            val l = Location(x, y)
            Cell1(
                l, if (l in normalized) {
                    CellState.DUG
                } else {
                    CellState.UNKNOWN
                }
            )
        }
    }
    return Lagoon1(cells)
}

private fun toLagoon2(corners: List<Location>, commands: List<Command>): Lagoon2 {
    val xn = corners.flatMap { listOf(it.x, it.x + 1) }.distinct().sorted()
    val yn = corners.flatMap { listOf(it.y, it.y + 1) }.distinct().sorted()
    val cells = (0 until (yn.size - 1)).map { y ->
        (0 until (xn.size - 1)).map { x ->
            val l = Location(x, y)
            Cell2(l, CellState.UNKNOWN, 0)
        }
    }
    var location = corners[0]
    var location2 = Location(xn.indexOf(location.x), yn.indexOf(location.y))
    for (command in commands) {
        location += command.direction.location * command.amount
        val newLocation2 = Location(xn.indexOf(location.x), yn.indexOf(location.y))
        val xMin = min(newLocation2.x, location2.x)
        val xMax = max(newLocation2.x, location2.x)
        val yMin = min(newLocation2.y, location2.y)
        val yMax = max(newLocation2.y, location2.y)
        for (x in xMin..xMax) {
            for (y in yMin..yMax) {
                val x2 = xn[x + 1] - xn[x]
                val y2 = yn[y + 1] - yn[y]
                cells[y][x].state = CellState.DUG
                cells[y][x].surface = x2.toLong() * y2
            }
            location2 = newLocation2
        }

    }
    return Lagoon2(cells, xn, yn)
}

private fun detectExterior(lagoon: Lagoon) {
    for (x in 0 until lagoon.xSize) {
        tryFillOutSide(lagoon, lagoon.at(x, 0)!!)
        tryFillOutSide(lagoon, lagoon.at(x, lagoon.ySize - 1)!!)
    }
    for (y in 0 until lagoon.ySize) {
        tryFillOutSide(lagoon, lagoon.at(0, y)!!)
        tryFillOutSide(lagoon, lagoon.at(lagoon.xSize - 1, y)!!)
    }
}

private fun tryFillOutSide(lagoon: Lagoon, at: Cell) {
    val candidates = mutableListOf(at)
    if (at.state == CellState.DUG) { return }
    val visited = hashSetOf<Cell>()
    while (candidates.isNotEmpty()) {
        val candidate = candidates.removeAt(0)
        visited.add(candidate)
        candidate.state = CellState.OUTSIDE
        val n = Direction4.entries.mapNotNull {
            lagoon.at(candidate.location + it)
        }.filter { it.state != CellState.DUG && it !in visited && it !in candidates }
        candidates.addAll(n)
    }
}

private fun fillInside(lagoon: Lagoon) {
    for (cell in lagoon.cells.flatten()) {
        if (cell.state == CellState.UNKNOWN) {
            cell.state = CellState.DUG
        }
    }
}

private fun fillInside2(lagoon: Lagoon2) {
    for (cell in lagoon.cells.flatten()) {
        if (cell.state == CellState.UNKNOWN) {
            cell.state = CellState.DUG
            val x = lagoon.xn[(cell.location.x + 1).toInt()] - lagoon.xn[(cell.location.x).toInt()]
            val y = lagoon.yn[(cell.location.y + 1).toInt()] - lagoon.yn[(cell.location.y).toInt()]
            cell.surface = x.toLong() * y
        }
    }
}

private fun part1(data: List<String>): Long {
    val p = parse(data)
    val corners = corners(p)
    val lagoon = toLagoon2(corners, p)
    detectExterior(lagoon)
    fillInside2(lagoon)
    return lagoon.digSize
}

private fun part2(data: List<String>): Long {
    val p = parse2(data)
    val corners = corners(p)
    val lagoon = toLagoon2(corners, p)
    detectExterior(lagoon)
    fillInside2(lagoon)
    return lagoon.digSize
}

fun main() {
    day(2023, 18) {
        part1(62, "test", ::part1)
        part1(33491, "input", ::part1)
        part1(952408144115L, "test", ::part2)
        part1(87716969654406L, "input", ::part2)
    }
}