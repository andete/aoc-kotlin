package aoc2023.day17

import day
import readInput
import util.*

private data class Cell(override val location: Location, val loss: Int) : Located {
    override fun toChar(visited: List<Located>): String {
        return if (this in visited) {
            "\u001b[31m$loss\u001b[0m"
        } else {
            "$loss"
        }
    }
}


private typealias Map = Maze<Cell>

private fun parse(input: List<String>): Map {
    val cells = input.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            Cell(Location(x, y), "$c".toInt())
        }
    }
    return Map(cells)
}

private data class WrappedCell(val cell: Cell, val direction: Direction?, val lastDirectionSteps: Int?) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is WrappedCell -> {
                if (lastDirectionSteps != null && other.lastDirectionSteps != null) {
                    lastDirectionSteps == other.lastDirectionSteps && cell == other.cell && direction == other.direction
                } else {
                    cell == other.cell
                }
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = cell.hashCode()
        result = 31 * result + (direction?.hashCode() ?: 0)
        result = 31 * result + (lastDirectionSteps ?: 0)
        return result
    }
}

private fun search(map: Map): Int {
    val start = WrappedCell(map.at(0, 0)!!, null, 0)
    val end = WrappedCell(map.at(map.xSize - 1, map.ySize - 1)!!, null, null)
    fun cost(c: WrappedCell, d: WrappedCell) = d.cell.loss
    fun neighbours(c: WrappedCell, visited: List<WrappedCell>): List<WrappedCell> {
        // if only one, visited is our starting cell, so just take all the neighbours
        if (visited.size == 1) {
            return map.neighbours(c.cell).map { WrappedCell(it.second, it.first, 1) }
        }
        val lastDirection = c.direction!!
        // we can't make a U-turn
        val forbiddenDirections = mutableListOf(-lastDirection)
        // if we ended with moving 3 times in the same direction, we can't continue in the same direction
        if (c.lastDirectionSteps == 3) {
            forbiddenDirections.add(lastDirection)
        }
        return map.neighbours(c.cell).filter { it.first !in forbiddenDirections }.map {
            if (it.first != lastDirection) {
                WrappedCell(it.second, it.first, 1)
            } else {
                WrappedCell(it.second, lastDirection, c.lastDirectionSteps!! + 1)
            }
        }
    }
    fun heuristic(c: WrappedCell): Int {
        return map.xSize - c.cell.location.x + map.ySize - c.cell.location.y
    }
    fun show(visited: List<Cell>) {
        map.show(visited)
    }

    val path = AStar.path(start, end, ::cost, ::neighbours, show = {})
    map.show(path.map { it.cell })
    val loss = path.sumOf { it.cell.loss } - start.cell.loss
    return loss
}

private fun search2(map: Map): Int {
    val start = WrappedCell(map.at(0, 0)!!, null, 0)
    val end = WrappedCell(map.at(map.xSize - 1, map.ySize - 1)!!, null, null)
    fun cost(c: WrappedCell, d: WrappedCell) = d.cell.loss
    fun neighbours(c: WrappedCell, visited: List<WrappedCell>): List<WrappedCell> {
        // if only one, visited is our starting cell, so just take all the neighbours
        if (visited.size == 1) {
            return map.neighbours(c.cell).map { WrappedCell(it.second, it.first, 1) }
        }
        val lastDirection = c.direction!!
        // we can't make a U-turn
        val forbiddenDirections = mutableListOf(-lastDirection)
        // if we ended with moving 10 times in the same direction, we can't continue in the same direction
        if (c.lastDirectionSteps == 10) {
            forbiddenDirections.add(lastDirection)
        }
        // if we move less then 4 steps in the same direction, we can only continue further in the same direction
        else if (c.lastDirectionSteps!! < 4) {
            forbiddenDirections.addAll(Direction.entries.filter { it != lastDirection})
        }
        return map.neighbours(c.cell).filter { it.first !in forbiddenDirections }.mapNotNull {
            if (it.second == end.cell && (c.lastDirectionSteps < 3 || it.first != lastDirection)) {
                null
            } else {
                if (it.first != lastDirection) {
                    WrappedCell(it.second, it.first, 1)
                } else {
                    WrappedCell(it.second, lastDirection, c.lastDirectionSteps!! + 1)
                }
            }
        }
    }
    fun heuristic(c: WrappedCell): Int {
        return map.xSize - c.cell.location.x + map.ySize - c.cell.location.y
    }
    fun show(visited: List<Cell>) {
        map.show(visited)
    }

    val path = AStar.path(start, end, ::cost, ::neighbours, show = {})
    map.show(path.map { it.cell })
    val loss = path.sumOf { it.cell.loss } - start.cell.loss
    return loss
}

private fun part1(input: List<String>) = search(parse(input))
private fun part2(input: List<String>) = search2(parse(input))

fun main() {
    day(2023, 17) {
        part1(102, "example", ::part1)
        part1(1013, "input", ::part1)
        part2(94, "example", ::part2)
        part2(71, "example2", ::part2)
        part2(1215, "input", ::part2)
    }
}