package aoc2023.day10

import day
import util.*
import util.location.Direction4
import util.location.Located
import util.location.Location

private enum class Pipe(val c: Char, val first: Direction4, val second: Direction4) {
    EW('-', Direction4.East, Direction4.West),
    NS('|', Direction4.North, Direction4.South),
    NE('L', Direction4.North, Direction4.East),
    NW('J', Direction4.North, Direction4.West),
    SW('7', Direction4.South, Direction4.West),
    SE('F', Direction4.South, Direction4.East);

    override fun toString() = "$c"
    fun other(fromDirection: Direction4) = if (fromDirection == first) {
        second
    } else {
        first
    }

    val directions = listOf(first, second)

    fun toMiniMap(): List<String> {
        return when (this) {
            EW -> listOf("...", "xxx", "...")
            NS -> listOf(".x.", ".x.", ".x.")
            NE -> listOf(".x.", ".xx", "...")
            NW -> listOf(".x.", "xx.", "...")
            SW -> listOf("...", "xx.", ".x.")
            SE -> listOf("...", ".xx", ".x.")
        }
    }
}

private data class Cell(override val location: Location, val pipe: Pipe?, val start: Boolean = false) : Located,
    CharProvider {
    override fun toChar() = "$char"

    override fun toString() = "(${location.x}, ${location.y}, $char)"

    val char = if (start) {
        'S'
    } else pipe?.c ?: '.'

    val isPipe = pipe != null

    fun toMiniMap(): List<String> {
        return if (start) {
            listOf(".x.", "xxx", ".x.")
        } else pipe?.toMiniMap() ?: listOf("...", "...", "...")
    }
}

private data class Arrival(val cell: Cell, val fromDirection: Direction4) {
    val toDirection = cell.pipe?.other(fromDirection)
}

private fun checkArrival(it: Cell, direction: Direction4) =
    if (it.start || it.pipe?.directions?.contains(direction) == true) {
        Arrival(it, direction)
    } else {
        null
    }

private typealias Field = Maze<Cell>

private fun parse(data: List<String>): Field {
    return data.mapIndexed { yIndex, line ->
        line.mapIndexed { xIndex, c ->
            if (c == 'S') {
                Cell(Location(xIndex, yIndex), null, start = true)
            } else {
                Cell(
                    Location(xIndex, yIndex),
                    Pipe.entries.singleOrNull { it.c == c })
            }
        }
    }.run { Field(this) }
}

private val Field.start get() = this.rows.flatten().single { it.start }

private fun follow(maze: Field, start: Arrival): List<Arrival>? {
    val visited = mutableListOf<Arrival>()
    var pos: Arrival? = start
    while (pos != null && pos.cell.isPipe && pos !in visited) {
        visited.add(pos)
        pos = maze.goDirection(pos.cell, pos.toDirection!!)
    }
    if (pos == null) {
        return null
    }
    if (visited.isEmpty()) {
        return null
    }
    if (pos.cell.start) {
        return visited
    }
    return null
}

private fun Field.goDirection(cell: Cell, toDirection: Direction4) = when (toDirection) {
    Direction4.North -> northOf(cell)
    Direction4.East -> eastOf(cell)
    Direction4.West -> westOf(cell)
    Direction4.South -> southOf(cell)
}

private fun calculate(maze: Field): Pair<Int, List<Arrival>> {
    var start = maze.start
    val startPositions =
        listOfNotNull(maze.northOf(start), maze.southOf(start), maze.eastOf(start), maze.westOf(start))
    var path =
        startPositions.firstNotNullOf { follow(maze, it) }
    path = listOf(Arrival(start, path.last().toDirection!!)) + path
    return ((path.size) / 2) to path
}

private fun Field.northOf(cell: Cell) = peekNorthOf(cell)?.let { checkArrival(it, Direction4.South) }
private fun Field.southOf(cell: Cell) = peekSouthOf(cell)?.let { checkArrival(it, Direction4.North) }
private fun Field.eastOf(cell: Cell) = peekEastOf(cell)?.let { checkArrival(it, Direction4.West) }
private fun Field.westOf(cell: Cell) = peekWestOf(cell)?.let { checkArrival(it, Direction4.East) }

private fun reduceMazeToPath(maze: Field, path: List<Cell>): Field {
    return Field(maze.rows.map {
        it.map {
            if (it in path) {
                it
            } else {
                it.copy(pipe = null)
            }
        }
    })
}

private enum class CellType(override val c: Char) : WithChar {
    PATH('x'),
    INSIDE('I'),
    OUTSIDE('O'),
    UNKNOWN('.');

    override fun toString() = "$c"
}

private typealias Field2 = EnumMaze<CellType>

private fun Field2.count(): Int {
    var res = 0
    for (x in rows[0].indices) {
        for (y in rows.indices) {
            if ((x - 1) % 3 == 0 && (y - 1) % 3 == 0) {
                if (at(x, y)?.t == CellType.UNKNOWN) {
                    res++
                }
            }
        }
    }
    return res
}

private fun makeMaze2(maze: Field): Field2 {
    val result = maze.rows.indices.flatMap { listOf("", "", "") }.toMutableList()
    var i = 0
    for (row in maze.rows) {
        for (cell in row) {
            val m = cell.toMiniMap()
            result[i] = result[i] + m[0]
            result[i + 1] = result[i + 1] + m[1]
            result[i + 2] = result[i + 2] + m[2]

        }
        i += 3
    }
    val res2 = result.mapIndexed { yIndex, s ->
        s.mapIndexed { xIndex, c ->
            if (c == 'x') {
                CellType.PATH
            } else {
                CellType.UNKNOWN
            }
        }
    }
    return makeItemMaze(res2)
}

private fun fillOutside(maze: Field2) {
    val toVisit = mutableListOf(maze.rows[0][0])
    while (toVisit.isNotEmpty()) {
        val cell = toVisit.removeAt(0)
        if (cell.t == CellType.UNKNOWN) {
            cell.t = CellType.OUTSIDE
            for (n in maze.neighbours(cell)) {
                if (n.second.t == CellType.UNKNOWN) {
                    toVisit.add(n.second)
                }
            }
        }
    }
}

private fun enclosed2(maze: Field): Int {
    val path = calculate(maze).second
    val pathCells = path.map { it.cell }
    maze.show(pathCells)
    val maze = reduceMazeToPath(maze, pathCells)
    maze.show(pathCells)
    val maze2 = makeMaze2(maze)
    maze2.show()
    fillOutside(maze2)
    maze2.show()
    return maze2.count()
}

fun main() {
    day(2023, 10) {
        part1(8, "test") { calculate(parse(it)).first }
        part1(6882, "input") { calculate(parse(it)).first }
        part2(4, "test1") { enclosed2(parse(it)) }
        part2(8, "test2") { enclosed2(parse(it)) }
        part2(10, "test3") { enclosed2(parse(it)) }
        part2(491, "input") { enclosed2(parse(it)) }

    }
}