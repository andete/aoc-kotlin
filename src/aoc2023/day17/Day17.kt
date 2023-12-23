package aoc2023.day17

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

fun main() {

    run {
        val testInput = readInput(2023, 17, "example")
        val map = parse(testInput)
        map.show()
        val res = search(map)
        println(res)
        check(102 == res)
    }

    run {
        val testInput = readInput(2023, 17)
        val map = parse(testInput)
        map.show()
        val res = search(map)
        println(res)
        check(1023 == res)
    }
}