package be.damad.aoc2023.aoc18

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

private val testData = """R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)""".split('\n')

private enum class Direction(val x: Long, val y: Long) {
    D(0, 1),
    U(0, -1),
    L(-1, 0),
    R(1, 0);

    val location get() = Location(x, y)
}

private data class Command(val direction: Direction, val amount: Long, val color: String)

private data class Location(val x: Long, val y: Long) {
    operator fun plus(other: Direction) = Location(x + other.x, y + other.y)
    operator fun plus(other: Location) = Location(x + other.x, y + other.y)

    operator fun times(i: Long) = Location(x * i, y * i)

}

private fun parse(data: List<String>): List<Command> {
    return data.map { line ->
        val s1 = line.split(' ')
        Command(Direction.valueOf(s1[0]), s1[1].toLong(), s1[2])
    }
}

private fun parse2(data: List<String>): List<Command> {
    return data.map { line ->
        val s1 = line.split(' ', ')', ')', '#')
        val s2 = s1[3]
        val hex = s2.substring(0, 5)
        val dir = s2.last()
        val direction = when(dir) {
            '0' -> Direction.R
            '1' -> Direction.D
            '2' -> Direction.L
            '3' -> Direction.U
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
    fun at(l: Location) = cells.getOrNull(l.y.toInt())?.getOrNull(l.x.toInt())

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

private data class Lagoon2(override val cells: List<List<Cell2>>, val xn: List<Long>, val yn: List<Long>) : Lagoon {

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
            val l = Location(x.toLong(), y.toLong())
            Cell2(l, CellState.UNKNOWN, 0)
        }
    }
    var location = corners[0]
    var location2 = Location(xn.indexOf(location.x).toLong(), yn.indexOf(location.y).toLong())
    for (command in commands) {
        location += command.direction.location * command.amount
        val newLocation2 = Location(xn.indexOf(location.x).toLong(), yn.indexOf(location.y).toLong())
        val xMin = min(newLocation2.x, location2.x).toInt()
        val xMax = max(newLocation2.x, location2.x).toInt()
        val yMin = min(newLocation2.y, location2.y).toInt()
        val yMax = max(newLocation2.y, location2.y).toInt()
        for (x in xMin..xMax) {
            for (y in yMin..yMax) {
                val x2 = xn[x+1] - xn[x]
                val y2 = yn[y+1] - yn[y]
                cells[y][x].state = CellState.DUG
                cells[y][x].surface = x2 * y2
            }
            location2 = newLocation2
        }

    }
    return Lagoon2(cells, xn, yn)
}

private fun detectExterior(lagoon: Lagoon) {
    for (x in 0 until lagoon.xSize) {
        tryFillOutSide(lagoon, lagoon.at(x, 0))
        tryFillOutSide(lagoon, lagoon.at(x, lagoon.ySize - 1))
    }
    for (y in 0 until lagoon.ySize) {
        tryFillOutSide(lagoon, lagoon.at(0, y))
        tryFillOutSide(lagoon, lagoon.at(lagoon.xSize - 1, y))
    }
}

private fun tryFillOutSide(lagoon: Lagoon, at: Cell?) {
    if (at == null) {
        return
    }
    if (at.state != CellState.UNKNOWN) {
        return
    }
    at.state = CellState.OUTSIDE
    for (entry in Direction.entries) {
        tryFillOutSide(lagoon, lagoon.at(at.location + entry))
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
            cell.surface = x * y
        }
    }
}

fun main() {
    run {
        val p = parse(testData)
        println(p)
        val visited = follow(p)
        println(visited)
        val lagoon = toLagoon1(visited)
        check(38L == lagoon.digSize)
        detectExterior(lagoon)
        fillInside(lagoon)
        println(lagoon.digSize)
        check(62L == lagoon.digSize)
    }

    run {
        val p = parse(aoc18data)
        val visited = follow(p)
        val lagoon = toLagoon1(visited)
        println(lagoon.digSize)
        detectExterior(lagoon)
        fillInside(lagoon)
        println(lagoon.digSize)
        check(33491L == lagoon.digSize)
    }

    run {
        val p = parse(testData)
        val corners = corners(p)
        val lagoon = toLagoon2(corners, p)
        check(38L == lagoon.digSize)
        detectExterior(lagoon)
        fillInside2(lagoon)
        println(lagoon.digSize)
        check(62L == lagoon.digSize)
    }

    run {
        val p = parse(aoc18data)
        val corners = corners(p)
        val lagoon = toLagoon2(corners, p)
        println(lagoon.digSize)
        check(3090L == lagoon.digSize)
        detectExterior(lagoon)
        fillInside2(lagoon)
        println(lagoon.digSize)
        check(33491L == lagoon.digSize)
    }

    run {
        val p = parse2(testData)
        val corners = corners(p)
        val lagoon = toLagoon2(corners, p)
        detectExterior(lagoon)
        fillInside2(lagoon)
        println(lagoon.digSize)
        check(952408144115L == lagoon.digSize)
    }

    run {
        val p = parse2(aoc18data)
        val corners = corners(p)
        val lagoon = toLagoon2(corners, p)
        detectExterior(lagoon)
        fillInside2(lagoon)
        println(lagoon.digSize)
        check(87716969654406L == lagoon.digSize)
    }
}