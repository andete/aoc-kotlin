package aoc2023.day17

import readInput
import util.AStar
import util.Located
import util.Location
import util.Maze

private data class Cell(override val location: Location, val loss: Int) : Located {
    override fun toChar(visited: List<Located>): Char {
        if (this in visited) {
            return 'O'
        } else {
            return "$loss"[0]
        }
    }

    override fun equals(other: Any?) = (other as? Cell)?.location == location
    override fun hashCode() = location.hashCode()
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

private fun search(map: Map): Int {
    val start = map.at(0, 0)!!
    val end = map.at(map.xSize - 1, map.ySize - 1)!!
    fun distance(c: Cell, d: Cell) = c.loss
    fun neighbours(c: Cell, visited: List<Cell>): List<Cell> {
        if (visited.isEmpty()) {
            return map.neighbours(c).map { it.second }
        }
        if (visited.size < 2) {
            return map.neighbours(c).map { it.second }.filter { it !in visited }
        }
        val s1 = visited.subList(0, visited.size - 1)
        val s2 = visited.subList(1, visited.size)
        val directions = s1.zip(s2).map { it.first.location.direction(it.second.location) }
        val lastDirection = directions.last()
        // we can't make a U-turn
        val forbiddenDirections = mutableListOf(-lastDirection)
        // if we ended with moving 3 times in the same direction, we can't continue in the same direction
        val needToTurn = if (directions.size >= 3) {
            val lastDirections = directions.takeLast(3).distinct()
            lastDirections.size == 1
        } else {
            false
        }
        if (needToTurn) {
            forbiddenDirections.add(lastDirection)
        }
        return map.neighbours(c).filter { it.first !in forbiddenDirections }.map { it.second }
    }
    fun heuristic(c: Cell): Int {
        return map.xSize - c.location.x + map.ySize - c.location.y
    }

    val path = AStar.path(start, end, ::distance, ::neighbours)
    map.show(path)
    val loss = path.filter { it != start }.sumOf { it.loss }
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
        check(102 == res)
    }
}