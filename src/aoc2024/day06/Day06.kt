package aoc2024.day06

import day
import util.CharMaze
import util.find
import util.location.Direction4
import util.location.Location
import util.parseCharMaze

fun main() {
    day(2024, 6) {
        part1(41, "example", ::part1)
        part1(5153, "input", ::part1)
        part2(6, "example", ::part2)
        part2(1711, "input", ::part2)
        part2(6, "example", ::part2alt)
        part2(1711, "input", ::part2alt)
    }
}

private fun part1(data: List<String>): Int {
    val maze = parseCharMaze(data)
    var position = maze.find('^')!!
    val visitedPositions = mutableSetOf<Location>()
    var direction = Direction4.North
    while (true) {
        visitedPositions.add(position)
        val newPosition = position + direction
        val newContent = maze.at(newPosition) ?: break
        if (newContent.t == '#') {
            direction = direction.rotate90()
        } else {
            position = newPosition
        }
    }
    return visitedPositions.size
}

private fun loopDetection(maze: CharMaze): Boolean {
    val visitedPositions = mutableSetOf<Pair<Location, Direction4>>()
    var position = maze.find('^')!!
    var direction = Direction4.North
    var rotated = false
    while (true) {
        if (!rotated && position to direction in visitedPositions) {
            return true
        }
        visitedPositions.add(position to direction)
        val newPosition = position + direction
        val newContent = maze.at(newPosition) ?: return false
        if (newContent.t == '#' || newContent.t == 'O') {
            direction = direction.rotate90()
            rotated = true
        } else {
            position = newPosition
            rotated = false
        }
    }
    return false
}

private fun part2(data: List<String>): Int {
    val sy = data.indexOfFirst { string -> string.contains('^') }
    val sx = data[sy].indexOf('^')
    var res = 0
    for (y in data.indices) {
        for (x in data[0].indices) {
            if (!(x == sx && y == sy)) {
                val maze = parseCharMaze(data)
                maze.at(x, y)!!.t = 'O'
                if (loopDetection(maze)) {
                    res++
                }
            }
        }
    }
    return res
}

private fun part2alt(data: List<String>): Int {
    // 1. calculate visited positions
    val maze = parseCharMaze(data)
    val startPosition = maze.find('^')!!
    var position = startPosition
    val visitedPositions = mutableListOf<Location>()
    var direction = Direction4.North
    while (true) {
        visitedPositions.add(position)
        val newPosition = position + direction
        val newContent = maze.at(newPosition) ?: break
        if (newContent.t == '#') {
            direction = direction.rotate90()
        } else {
            position = newPosition
        }
    }
    var res = mutableSetOf<Location>()
    for (position in visitedPositions) {
        if (position != startPosition) {
            val maze = parseCharMaze(data)
            maze.at(position)!!.t = 'O'
            if (loopDetection(maze)) {
                res.add(position)
            }
        }
    }

    return res.size
}