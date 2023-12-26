package be.damad.aoc2023.aoc16

import kotlin.math.max

private val testData = """.|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|....""".split('\n')

private enum class TileType(val c: Char) {
    EMPTY('.'),
    NS('|'),
    WE('-'),
    NWSE('\\'),
    SWNE('/'),
}

private enum class Direction(val x: Int, val y: Int) {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
}

private val tileMap = mapOf(
    (Direction.NORTH to TileType.EMPTY) to listOf(Direction.NORTH),
    (Direction.SOUTH to TileType.EMPTY) to listOf(Direction.SOUTH),
    (Direction.EAST to TileType.EMPTY) to listOf(Direction.EAST),
    (Direction.WEST to TileType.EMPTY) to listOf(Direction.WEST),

    (Direction.NORTH to TileType.NS) to listOf(Direction.NORTH),
    (Direction.SOUTH to TileType.NS) to listOf(Direction.SOUTH),
    (Direction.EAST to TileType.NS) to listOf(Direction.NORTH, Direction.SOUTH),
    (Direction.WEST to TileType.NS) to listOf(Direction.NORTH, Direction.SOUTH),

    (Direction.WEST to TileType.WE) to listOf(Direction.WEST),
    (Direction.EAST to TileType.WE) to listOf(Direction.EAST),
    (Direction.NORTH to TileType.WE) to listOf(Direction.WEST, Direction.EAST),
    (Direction.SOUTH to TileType.WE) to listOf(Direction.WEST, Direction.EAST),

    (Direction.NORTH to TileType.NWSE) to listOf(Direction.WEST),
    (Direction.SOUTH to TileType.NWSE) to listOf(Direction.EAST),
    (Direction.EAST to TileType.NWSE) to listOf(Direction.SOUTH),
    (Direction.WEST to TileType.NWSE) to listOf(Direction.NORTH),

    (Direction.NORTH to TileType.SWNE) to listOf(Direction.EAST),
    (Direction.SOUTH to TileType.SWNE) to listOf(Direction.WEST),
    (Direction.EAST to TileType.SWNE) to listOf(Direction.NORTH),
    (Direction.WEST to TileType.SWNE) to listOf(Direction.SOUTH),

    )

private data class Tile(
    val x: Int,
    val y: Int,
    val t: TileType,
    var directions: MutableList<Direction> = mutableListOf()
) {
    val energized get() = directions.isNotEmpty()

    fun deepCopy() = copy(directions = directions.toMutableList())
}

private data class Contraption(val tiles: List<List<Tile>>) {

    fun deepCopy(): Contraption =
        Contraption(tiles = tiles.map { row -> row.map { tile -> tile.deepCopy()} })

    val energized get() = tiles.sumOf { it.count { it.energized } }

    fun at(x: Int, y: Int) = tiles.getOrNull(y)?.getOrNull(x)

    fun print() {
        for (tile in tiles) {
            val l = tile.joinToString("") {
                if (it.energized) {
                    "*"
                } else {
                    "${it.t.c}"
                }
            }
            println(l)
        }
        println()
    }

}

private fun parse(data: List<String>): Contraption {
    val cells = data.mapIndexed { yIndex, s ->
        s.mapIndexed { xIndex, c ->
            Tile(xIndex, yIndex, TileType.entries.single { it.c == c })
        }
    }
    return Contraption(cells)
}

private fun beam(contraption: Contraption, x: Int = 0, y: Int = 0, direction: Direction = Direction.EAST) {
    val cell = contraption.at(x, y) ?: return
    if (direction in cell.directions) { return }
    cell.directions.add(direction)
    val nexts = tileMap[direction to cell.t]!!
    for (next in nexts) {
        beam(contraption, x + next.x, y + next.y, next)
    }
}

private fun calculate2(contraption: Contraption): Int {
    var m = 0
    for (x in contraption.tiles[0].indices) {
        val x1 = contraption.deepCopy()
        beam(x1, x, 0, Direction.SOUTH)
        m = max(m, x1.energized)
    }
    for (x in contraption.tiles[0].indices) {
        val x1 = contraption.deepCopy()
        beam(x1, x, contraption.tiles.size - 1, Direction.NORTH)
        m = max(m, x1.energized)
    }
    for (y in contraption.tiles.indices) {
        val x1 = contraption.deepCopy()
        beam(x1, 0, y, Direction.EAST)
        m = max(m, x1.energized)
    }
    for (y in contraption.tiles.indices) {
        val x1 = contraption.deepCopy()
        beam(x1, contraption.tiles[0].size - 1, y, Direction.WEST)
        m = max(m, x1.energized)
    }
    return m
}

fun main() {
    run {
        val c = parse(testData)
        c.print()
        beam(c)
        c.print()
        println(c.energized)
        check(c.energized == 46)
    }

    run {
        val c = parse(aoc16data)
        //c.print()
        beam(c)
        //c.print()
        println(c.energized)
        check(c.energized == 7979)
    }

    run {
        val c = parse(testData)
        val res = calculate2(c)
        println(res)
        check(res == 51)
    }

    run {
        val c = parse(aoc16data)
        val res = calculate2(c)
        println(res)
        check(res == 8437)
    }
}