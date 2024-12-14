package aoc2024.day14

import day
import util.location.Location

fun main() {
    day(2024, 14) {
        //part1(12, "example0") { part1(11, 7, it) }
        part1(12, "example") { part1(11, 7, it) }
        part1(218295000, "input") { part1(101, 103, it) }
        part2(6870, "input", ::part2)
    }
}

private data class Robot(var location: Location, val velocity: Location) {

    fun move(wide: Int, tall: Int) {
        location = Location((location.x + velocity.x + wide) % wide, (location.y + velocity.y + tall) % tall)
    }

    override fun toString(): String {
        return "R(${location.x},${location.y}|${velocity.x},${velocity.y})"
    }
}

private fun parseRobots(data: List<String>): List<Robot> {
    return data.map {
        val l = it.split(" ", ",", "=")
        val location = Location(l[1].toInt(), l[2].toInt())
        val velocity = Location(l[4].toInt(), l[5].toInt())
        Robot(location, velocity)
    }
}

private fun robotsInQuadrant(robots: List<Robot>, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    return robots.count {
        it.location.x in (xMin..xMax) && it.location.y in (yMin..yMax)
    }
}

private fun printRobotMaze(wide: Int, tall: Int, robots: List<Robot>) {
    for (y in 0 until tall) {
        val line = (0 until wide).joinToString("") { x ->
            val c = robots.count { it.location == Location(x, y) }
            if (c == 0) {
                "."
            } else {
                c.toString()
            }
        }
        println(line)
    }
    println()
}

private fun part1(wide: Int, tall: Int, data: List<String>): Long {
    val robots = parseRobots(data)
    printRobotMaze(wide, tall, robots)
    repeat(100) { robots.forEach { it.move(wide, tall) } }
    println(robots)
    printRobotMaze(wide, tall, robots)
    val q1 = robotsInQuadrant(robots, 0, wide / 2 - 1, 0, tall / 2 - 1)
    val q2 = robotsInQuadrant(robots, wide / 2 + 1, wide - 1, 0, tall / 2 - 1)
    val q3 = robotsInQuadrant(robots, 0, wide / 2 - 1, tall / 2 + 1, tall - 1)
    val q4 = robotsInQuadrant(robots, wide / 2 + 1, wide - 1, tall / 2 + 1, tall - 1)
    return q1.toLong() * q2 * q3 * q4
}

private fun part2(data: List<String>): Long {
    val wide = 101
    val tall = 103
    val robots = parseRobots(data)
    // number found by visually checking using the commented filter below
    repeat(6870) {
        robots.forEach { it.move(wide, tall) }
//        val q1 = robotsInQuadrant(robots, wide / 2 - 1, wide / 2 + 1, 0, tall - 1)
//        if (q1 > tall / 2) {
//            println(it + 1)
//            printRobotMaze(wide, tall, robots)
//            0 + 0
//        }
    }
    printRobotMaze(wide, tall, robots)
    return 6870
}