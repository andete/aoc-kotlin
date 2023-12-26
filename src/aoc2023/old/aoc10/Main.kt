package be.damad.aoc2023.aoc10

private val testData = """\
7-F7-
.FJ|7
SJLL7
|F--J
LJ.LJ""".split('\n')

private enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}

private enum class Pipe(val c: Char, val first: Direction, val second: Direction) {
    EW('-', Direction.EAST, Direction.WEST),
    NS('|', Direction.NORTH, Direction.SOUTH),
    NE('L', Direction.NORTH, Direction.EAST),
    NW('J', Direction.NORTH, Direction.WEST),
    SW('7', Direction.SOUTH, Direction.WEST),
    SE('F', Direction.SOUTH, Direction.EAST);

    override fun toString() = "$c"
    fun other(fromDirection: Direction) = if (fromDirection == first) {
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

private data class Cell(val x: Int, val y: Int, val pipe: Pipe?, val start: Boolean = false) {
    override fun toString() = "($x, $y, $char)"

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

private data class Arrival(val cell: Cell, val fromDirection: Direction) {
    val toDirection = cell.pipe?.other(fromDirection)
}

private data class Maze(val cells: List<List<Cell>>) {
    val start get() = cells.flatten().single { it.start }

    fun at(x: Int, y: Int): Cell? = cells.getOrNull(y)?.getOrNull(x)

    private fun checkArrival(it: Cell, direction: Direction) =
        if (it.start || it.pipe?.directions?.contains(direction) == true) {
            Arrival(it, direction)
        } else {
            null
        }

    fun peekNorthOf(cell: Cell) = at(cell.x, cell.y - 1)
    fun peekSouthOf(cell: Cell) = at(cell.x, cell.y + 1)
    fun peekWestOf(cell: Cell) = at(cell.x - 1, cell.y)
    fun peekEastOf(cell: Cell) = at(cell.x + 1, cell.y)

    fun northOf(cell: Cell) = peekNorthOf(cell)?.let { checkArrival(it, Direction.SOUTH) }

    fun southOf(cell: Cell) = peekSouthOf(cell)?.let { checkArrival(it, Direction.NORTH) }
    fun westOf(cell: Cell) = peekWestOf(cell)?.let { checkArrival(it, Direction.EAST) }
    fun eastOf(cell: Cell) = peekEastOf(cell)?.let { checkArrival(it, Direction.WEST) }

    fun goDirection(cell: Cell, direction: Direction) = when (direction) {
        Direction.NORTH -> northOf(cell)
        Direction.EAST -> eastOf(cell)
        Direction.WEST -> westOf(cell)
        Direction.SOUTH -> southOf(cell)
    }

    override fun toString() = cells.joinToString("\n") { it.joinToString("") { "${it.char}" } }
    fun toStringWithHighLight(highLights: List<Pair<List<Cell>, Int>>): String {
        return cells.joinToString("\n") {
            it.joinToString("") { cell ->
                val h = highLights.firstOrNull { cell in it.first }
                if (h != null) {
                    "\u001b[${h.second}m${cell.char}\u001b[0m"
                } else {
                    "${cell.char}"
                }
            }
        }
    }

}

private enum class CellType {
    PATH,
    INSIDE,
    OUTSIDE,
    UNKNOWN,
}

private data class Cell2(val x: Int, val y: Int, var type: CellType) {
    override fun toString() = when (type) {
        CellType.INSIDE -> "I"
        CellType.OUTSIDE -> "O"
        CellType.PATH -> "x"
        CellType.UNKNOWN -> "."
    }
}

private data class Maze2(val cells: List<List<Cell2>>) {
    override fun toString() = cells.joinToString("\n") {
        it.joinToString(" ")
    }

    fun at(x: Int, y: Int) = cells.getOrNull(y)?.getOrNull(x)

    fun neighbours(cell: Cell2): List<Cell2> {
        return listOfNotNull(
            at(cell.x - 1, cell.y),
            at(cell.x + 1, cell.y),
            at(cell.x, cell.y - 1),
            at(cell.x, cell.y + 1),

            )
    }

    fun count(): Int {
        var res = 0
        for (x in cells[0].indices) {
            for (y in cells.indices) {
                if ((x - 1) % 3 == 0 && (y - 1) % 3 == 0) {
                    if (at(x, y)?.type == CellType.UNKNOWN) {
                        res ++
                    }
                }
            }
        }
        return res
    }
}

private fun reduceMazeToPath(maze: Maze, path: List<Cell>): Maze {
    return Maze(maze.cells.map {
        it.map {
            if (it in path) {
                it
            } else {
                it.copy(pipe = null)
            }
        }
    })
}

private fun makeMaze2(maze: Maze): Maze2 {
    val result = maze.cells.indices.flatMap { listOf("", "", "") }.toMutableList()
    var i = 0
    for (row in maze.cells) {
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
            Cell2(
                xIndex, yIndex, if (c == 'x') {
                    CellType.PATH
                } else {
                    CellType.UNKNOWN
                }
            )
        }
    }
    return Maze2(res2)
}

private fun parse(data: List<String>): Maze {
    return data.mapIndexed { yIndex, line ->
        line.mapIndexed { xIndex, c ->
            if (c == 'S') {
                Cell(xIndex, yIndex, null, start = true)
            } else {
                Cell(xIndex, yIndex, Pipe.entries.singleOrNull { it.c == c })
            }
        }
    }.run { Maze(this) }
}

private fun follow(maze: Maze, start: Arrival): List<Arrival>? {
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

private fun calculate(maze: Maze): Pair<Int, List<Arrival>> {
    var start = maze.start
    val startPositions =
        listOfNotNull(maze.northOf(start), maze.southOf(start), maze.eastOf(start), maze.westOf(start))
    var path =
        startPositions.firstNotNullOf { follow(maze, it) }
    path = listOf(Arrival(start, path.last().toDirection!!)) + path
    return ((path.size) / 2) to path
}

private fun enclosed2(maze: Maze): Int {
    val path = calculate(maze).second
    val pathCells = path.map { it.cell }
    println(maze.toStringWithHighLight(listOf(pathCells to 34)))
    val maze = reduceMazeToPath(maze, pathCells)
    println(maze.toStringWithHighLight(listOf(pathCells to 34)))
    val maze2 = makeMaze2(maze)
    println(maze2)
    fillOutside(maze2)
    println(maze2)
    return maze2.count()
}

private fun fillOutside(maze: Maze2) {
    val toVisit = mutableListOf(maze.cells[0][0])
    while (toVisit.isNotEmpty()) {
        val cell = toVisit.removeAt(0)
        if (cell.type == CellType.UNKNOWN) {
            cell.type = CellType.OUTSIDE
            for (n in maze.neighbours(cell)) {
                if (n.type == CellType.UNKNOWN) {
                    toVisit.add(n)
                }
            }
        }
    }
}

fun main() {
    val res1 = calculate(parse(testData)).first
    println(res1)
    check(8 == res1)

    val res2 = calculate(parse(aoc10data)).first
    println(res2)
    check(6882 == res2)

    val res3 = enclosed2(parse(aoc10testData1))
    println(res3)
    check(4 == res3)

    val res5 = enclosed2(parse(aoc10testData2))
    println(res5)
    check(8 == res5)

    val res6 = enclosed2(parse(aoc10testData3))
    println(res6)
    check(10 == res6)

    val res7 = enclosed2(parse(aoc10data))
    println(res7)
    check(491 == res7)
}