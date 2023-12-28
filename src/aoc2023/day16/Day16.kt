package aoc2023.day16

import day
import util.*
import kotlin.math.max

private enum class TileType(override val c: Char): WithChar {
    EMPTY('.'),
    NS('|'),
    WE('-'),
    NWSE('\\'),
    SWNE('/'),
}

private val tileMap = mapOf(
    (Direction.North to TileType.EMPTY) to listOf(Direction.North),
    (Direction.South to TileType.EMPTY) to listOf(Direction.South),
    (Direction.East to TileType.EMPTY) to listOf(Direction.East),
    (Direction.West to TileType.EMPTY) to listOf(Direction.West),

    (Direction.North to TileType.NS) to listOf(Direction.North),
    (Direction.South to TileType.NS) to listOf(Direction.South),
    (Direction.East to TileType.NS) to listOf(Direction.North, Direction.South),
    (Direction.West to TileType.NS) to listOf(Direction.North, Direction.South),

    (Direction.West to TileType.WE) to listOf(Direction.West),
    (Direction.East to TileType.WE) to listOf(Direction.East),
    (Direction.North to TileType.WE) to listOf(Direction.West, Direction.East),
    (Direction.South to TileType.WE) to listOf(Direction.West, Direction.East),

    (Direction.North to TileType.NWSE) to listOf(Direction.West),
    (Direction.South to TileType.NWSE) to listOf(Direction.East),
    (Direction.East to TileType.NWSE) to listOf(Direction.South),
    (Direction.West to TileType.NWSE) to listOf(Direction.North),

    (Direction.North to TileType.SWNE) to listOf(Direction.East),
    (Direction.South to TileType.SWNE) to listOf(Direction.West),
    (Direction.East to TileType.SWNE) to listOf(Direction.North),
    (Direction.West to TileType.SWNE) to listOf(Direction.South),

    )

private data class Tile(
    val t: TileType,
    var directions: MutableList<Direction> = mutableListOf()
): DeepCopyable<Tile> {
    val energized get() = directions.isNotEmpty()

    override fun deepCopy() = copy(directions = directions.toMutableList())
}

private typealias Contraption = ItemMaze<Tile>

private val Contraption.energized get() = rows.sumOf { it.count { it.t.energized } }

private fun beam(contraption: Contraption, x: Int = 0, y: Int = 0, direction: Direction = Direction.East) {
    val cell = contraption.at(x, y) ?: return
    if (direction in cell.t.directions) { return }
    cell.t.directions.add(direction)
    val nexts = tileMap[direction to cell.t.t]!!
    for (next in nexts) {
        beam(contraption, x + next.x, y + next.y, next)
    }
}

private fun calculate2(contraption: Contraption): Int {
    var m = 0
    for (x in contraption.xIndices) {
        val x1 = contraption.deepCopy()
        beam(x1, x, 0, Direction.South)
        m = max(m, x1.energized)
    }
    for (x in contraption.xIndices) {
        val x1 = contraption.deepCopy()
        beam(x1, x, contraption.ySize - 1, Direction.North)
        m = max(m, x1.energized)
    }
    for (y in contraption.yIndices) {
        val x1 = contraption.deepCopy()
        beam(x1, 0, y, Direction.East)
        m = max(m, x1.energized)
    }
    for (y in contraption.yIndices) {
        val x1 = contraption.deepCopy()
        beam(x1, contraption.xSize - 1, y, Direction.West)
        m = max(m, x1.energized)
    }
    return m
}

private fun parse(data: List<String>): Contraption {
    val cells = data.mapIndexed { yIndex, s ->
        s.mapIndexed { xIndex, c ->
            Tile(TileType.entries.single { it.c == c })
        }
    }
    return makeItemMaze(cells)
}

private fun part1(input: List<String>): Int {
    val c = parse(input)
    c.show()
    beam(c)
    c.show()
    return c.energized
}

private fun part2(input: List<String>) = calculate2(parse(input))

fun main() {
    day(2023, 16) {
        part1(46, "test", ::part1)
        part1(7979, "input", ::part1)
        part1(51, "test", ::part2)
        part1(8437, "input", ::part2)
    }
}