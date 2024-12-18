package aoc2024.day18

import day
import util.AStar
import util.location.Direction4
import util.location.Location

fun main() {
    day(2024, 18) {
        part1(22, "example") { part1(12, Location(6,6), it) }
        part1(700, "input") { part1(1024, Location(70,70), it) }
//        part2(117440, "example2", ::part2)
//        part2(84893551, "input", ::part2)

    }
}

private fun part1(steps: Int, end: Location, data: List<String>): Int {
    val pushes = data.map { it.split(",").map { it.toInt() } }.map { Location(it[0], it[1]) }
    val executed = pushes.take(steps)
    fun neighbours(l: Location, visited: List<Location>): List<Location> {
        return Direction4.entries.map {
            l + it
        }.filter { it.x >= 0 && it.y >= 0 && it.x <= end.x && it.y <= end.y }.filter { it !in visited && it !in executed }
    }
    val res = AStar.path(Location(0,0), end, { _,_ -> 1 }, ::neighbours)
    return res.size - 1
}