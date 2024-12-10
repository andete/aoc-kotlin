package aoc2024.day10

import day
import util.CharMaze
import util.LocatedItem
import util.findAll
import util.findAllItems
import util.location.Location
import util.parseCharMaze

fun main() {
    day(2024, 10) {
        part1(1, "example", ::part1)
        part1(36, "example2", ::part1)
        part1(496, "input", ::part1)
        part2(81, "example2", ::part2)
        part2(1120, "input", ::part2)
    }
}

private fun score(seen: MutableSet<Location>, maze: CharMaze, trailHead: LocatedItem<Char>): Long {
    if (trailHead.t == '9') {
        if (trailHead.location !in seen) {
            seen.add(trailHead.location)
            return 1L
        } else {
            return 0L
        }
    }
    val d = trailHead.t + 1
    val n = maze.neighbours(trailHead).map { it.second }.filter { it.t == d }
    return n.sumOf { score(seen, maze, it) }
}

private fun part1(data: List<String>): Long {
    val maze = parseCharMaze(data)
    val trailHeads = maze.findAllItems('0')
    return trailHeads.sumOf { score(mutableSetOf<Location>(), maze, it)  }
}

private fun rating(maze: CharMaze, trailHead: LocatedItem<Char>): Long {
    if (trailHead.t == '9') {
        return 1L
    }
    val d = trailHead.t + 1
    val n = maze.neighbours(trailHead).map { it.second }.filter { it.t == d }
    return n.sumOf { rating(maze, it) }
}

private fun part2(data: List<String>): Long {
    val maze = parseCharMaze(data)
    val trailHeads = maze.findAllItems('0')
    return trailHeads.sumOf { rating(maze, it)  }
}