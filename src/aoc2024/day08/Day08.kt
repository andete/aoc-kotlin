package aoc2024.day08

import day
import util.CharMaze
import util.combinationPairs
import util.findAll
import util.location.Location
import util.parseCharMaze

fun main() {
    day(2024, 8) {
        part1(14, "example", ::part1)
        part1(351, "input", ::part1)
        part2(9, "example0", ::part2)
        part2(34, "example", ::part2)
        part2(1259, "input", ::part2)
    }
}

private fun part1(data: List<String>): Long {
    val maze = parseCharMaze(data)
    val chars = maze.rows.flatten().map { it.t }.distinct().filter { it != '.' }
    println(chars)
    var antiNodeLocations = mutableSetOf<Location>()
    for (c in chars) {
        val locationsForC = maze.findAll(c)
        val combinations = combinationPairs(locationsForC)
        for (combination in combinations) {
            antiNodeLocations += calculateAntiNodes(maze, combination)
        }
    }
    return antiNodeLocations.size.toLong()
}

private fun calculateAntiNodes(maze: CharMaze, pair: Pair<Location, Location>): Set<Location> {
    val res = mutableSetOf<Location>()
    val (l1, l2) = pair
    val dx = l2 - l1
    val a1 = l1 - dx
    val a2 = l2 + dx
    maze.at(a1)?.let { res.add(a1) }
    maze.at(a2)?.let { res.add(a2) }
    return res
}

private fun part2(data: List<String>): Long {
    val maze = parseCharMaze(data)
    val chars = maze.rows.flatten().map { it.t }.distinct().filter { it != '.' }
    println(chars)
    var antiNodeLocations = mutableSetOf<Location>()
    for (c in chars) {
        val locationsForC = maze.findAll(c)
        val combinations = combinationPairs(locationsForC)
        for (combination in combinations) {
            antiNodeLocations += calculateAntiNodes2(maze, combination)
        }
    }
    return antiNodeLocations.size.toLong()
}

private fun calculateAntiNodes2(maze: CharMaze, pair: Pair<Location, Location>): Set<Location> {
    val res = mutableSetOf<Location>()
    val (l1, l2) = pair
    val dx = l2 - l1
    var a1 = l1
    var a2 = l2
    while (true) {
        a1 = a1 - dx
        maze.at(a1)?.let { res.add(a1) } ?: break
    }
    while (true) {
        a2 = a2 + dx
        maze.at(a2)?.let { res.add(a2) } ?: break
    }
    return res + setOf(l1, l2)
}