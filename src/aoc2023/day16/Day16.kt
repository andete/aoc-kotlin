package aoc2023.day16

import day
import util.*
import util.location.Direction4
import kotlin.math.max

private enum class TileType(override val c: Char): WithChar {
    EMPTY('.'),
    NS('|'),
    WE('-'),
    NWSE('\\'),
    SWNE('/'),
}

private val tileMap = mapOf(
    (Direction4.North to TileType.EMPTY) to listOf(Direction4.North),
    (Direction4.South to TileType.EMPTY) to listOf(Direction4.South),
    (Direction4.East to TileType.EMPTY) to listOf(Direction4.East),
    (Direction4.West to TileType.EMPTY) to listOf(Direction4.West),

    (Direction4.North to TileType.NS) to listOf(Direction4.North),
    (Direction4.South to TileType.NS) to listOf(Direction4.South),
    (Direction4.East to TileType.NS) to listOf(Direction4.North, Direction4.South),
    (Direction4.West to TileType.NS) to listOf(Direction4.North, Direction4.South),

    (Direction4.West to TileType.WE) to listOf(Direction4.West),
    (Direction4.East to TileType.WE) to listOf(Direction4.East),
    (Direction4.North to TileType.WE) to listOf(Direction4.West, Direction4.East),
    (Direction4.South to TileType.WE) to listOf(Direction4.West, Direction4.East),

    (Direction4.North to TileType.NWSE) to listOf(Direction4.West),
    (Direction4.South to TileType.NWSE) to listOf(Direction4.East),
    (Direction4.East to TileType.NWSE) to listOf(Direction4.South),
    (Direction4.West to TileType.NWSE) to listOf(Direction4.North),

    (Direction4.North to TileType.SWNE) to listOf(Direction4.East),
    (Direction4.South to TileType.SWNE) to listOf(Direction4.West),
    (Direction4.East to TileType.SWNE) to listOf(Direction4.North),
    (Direction4.West to TileType.SWNE) to listOf(Direction4.South),

    )

private data class Tile(
    val t: TileType,
    var directions: MutableList<Direction4> = mutableListOf()
): DeepCopyable<Tile> {
    val energized get() = directions.isNotEmpty()

    override fun deepCopy() = copy(directions = directions.toMutableList())
}

private typealias Contraption = ItemMaze<Tile>

private val Contraption.energized get() = rows.sumOf { it.count { it.t.energized } }

private fun beam(contraption: Contraption, x: Int = 0, y: Int = 0, direction: Direction4 = Direction4.East) {
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
        beam(x1, x, 0, Direction4.South)
        m = max(m, x1.energized)
    }
    for (x in contraption.xIndices) {
        val x1 = contraption.deepCopy()
        beam(x1, x, contraption.ySize - 1, Direction4.North)
        m = max(m, x1.energized)
    }
    for (y in contraption.yIndices) {
        val x1 = contraption.deepCopy()
        beam(x1, 0, y, Direction4.East)
        m = max(m, x1.energized)
    }
    for (y in contraption.yIndices) {
        val x1 = contraption.deepCopy()
        beam(x1, contraption.xSize - 1, y, Direction4.West)
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