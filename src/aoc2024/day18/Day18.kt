package aoc2024.day18

import day
import util.AStar
import util.location.Direction4
import util.location.Location

fun main() {
    day(2024, 18) {
        part1(22, "example") { part1(12, Location(6, 6), it) }
        part1(292, "input") { part1(1024, Location(70, 70), it) }
        p2("6,1", "example") { part2(Location(6, 6), it) }
        p2("58,44", "input") { part2(Location(70, 70), it) }
    }
}

private fun part1(steps: Int, end: Location, data: List<String>): Int {
    val pushes = data.map { it.split(",").map { it.toInt() } }.map { Location(it[0], it[1]) }
    val executed = pushes.take(steps)
    fun neighbours(l: Location, visited: List<Location>): List<Location> {
        return Direction4.entries.map {
            l + it
        }.filter { it.x >= 0 && it.y >= 0 && it.x <= end.x && it.y <= end.y }
            .filter { it !in visited && it !in executed }
    }

    val res = AStar.path(Location(0, 0), end, { _, _ -> 1 }, ::neighbours)
    return res.size - 1
}

private fun part2(end: Location, data: List<String>): String {
    val pushes = data.map { it.split(",").map { it.toInt() } }.map { Location(it[0], it[1]) }
    println(pushes.size)
    val executed = mutableListOf<Location>()
    fun neighbours(l: Location, visited: List<Location>): List<Location> {
        return Direction4.entries.map {
            l + it
        }.filter { it.x >= 0 && it.y >= 0 && it.x <= end.x && it.y <= end.y }
            .filter { it !in visited && it !in executed }
    }
    var i = 0
    for (location in pushes) {
        print("*")
        if (i % 10 == 0) { println(i) }
        i++
        executed.add(location)
        try {
            AStar.path(Location(0, 0), end, { _, _ -> 1 }, ::neighbours)
        } catch (e: IllegalStateException) {
            return "${location.x},${location.y}"
        }
    }
    return ""
}