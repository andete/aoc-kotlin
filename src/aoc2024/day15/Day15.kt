package aoc2024.day15

import day
import util.CharMaze
import util.LocatedItem
import util.copyItemsFrom
import util.findAll
import util.findItem
import util.location.Direction4
import util.parseCharMaze

fun main() {
    day(2024, 15) {
        part1(2028, "example1", ::part1)
        part1(10092, "example2", ::part1)
        part1(1499739, "input", ::part1)
        part2(9021, "example2", ::part2)
        part2(1522215, "input", ::part2)
    }
}

private fun part1(data: List<String>): Long {
    val mazeLines = data.subList(0, data.indexOf(""))
    val moveLines = data.subList(data.indexOf("") + 1, data.size)
    val maze = parseCharMaze(mazeLines)
    val moves = parseMovements(moveLines)
    //maze.show()
    //println(moves)
    var robot = maze.findItem('@')!!
    moves.forEach { move -> robot = moveRobot(maze, robot, move) }
    return maze.findAll('O').sumOf { (it.x.toLong() + it.y * 100) }
}

private fun parseMovements(moveLines: List<String>): List<Direction4> = moveLines.joinToString("").map {
    when (it) {
        '^' -> Direction4.North
        '>' -> Direction4.East
        'v' -> Direction4.South
        '<' -> Direction4.West
        else -> error("unknown char $it")
    }
}

private fun moveRobot(maze: CharMaze, robot: LocatedItem<Char>, direction4: Direction4): LocatedItem<Char> {
    val nextPosition = maze.at(robot.location + direction4)!!
    if (nextPosition.t == '#') {
        return robot
    }
    if (nextPosition.t == '.') {
        robot.t = '.'
        nextPosition.t = '@'
        return nextPosition
    }
    if (nextPosition.t == 'O') {
        if (moveBox(maze, nextPosition, direction4)) {
            robot.t = '.'
            nextPosition.t = '@'
            return nextPosition
        } else {
            return robot
        }
    }
    error("Unknown tile ${nextPosition.t}")
}

private fun moveBox(
    maze: CharMaze,
    box: LocatedItem<Char>,
    direction4: Direction4
): Boolean {
    val nextPosition = maze.at(box.location + direction4)!!
    if (nextPosition.t == '#') {
        return false
    }
    if (nextPosition.t == '.') {
        nextPosition.t = 'O'
        box.t = '.'
        return true
    }
    if (nextPosition.t == 'O') {
        if (moveBox(maze, nextPosition, direction4)) {
            nextPosition.t = 'O'
            box.t = '.'
            return true
        } else {
            return false
        }
    }
    error("unknown tile ${nextPosition.t}")
}

private fun scaleUp(line: String): String {
    return line.map {
        when (it) {
            '#' -> "##"
            'O' -> "[]"
            '@' -> "@."
            '.' -> ".."
            else -> error("unknown tile $it")
        }
    }.joinToString("")
}

private fun part2(data: List<String>): Long {
    val mazeLines = data.subList(0, data.indexOf(""))
    val moveLines = data.subList(data.indexOf("") + 1, data.size)
    val mazeLines2 = mazeLines.map(::scaleUp)
    val maze = parseCharMaze(mazeLines2)
    val moves = parseMovements(moveLines)
    //maze.show()
    var robot = maze.findItem('@')!!
    moves.forEach { move -> robot = moveRobot2(maze, robot, move) }
    //maze.show()
    return maze.findAll('[').sumOf { (it.x.toLong() + it.y * 100) }
}

private fun moveRobot2(maze: CharMaze, robot: LocatedItem<Char>, direction: Direction4): LocatedItem<Char> {
    //println(direction)
    //maze.show(listOf(robot))
    val nextPosition = maze.at(robot.location + direction)!!
    if (nextPosition.t == '#') {
        return robot
    }
    if (nextPosition.t == '.') {
        robot.t = '.'
        nextPosition.t = '@'
        return nextPosition
    }
    if (nextPosition.t == '[') {
        if (moveBox2(maze, nextPosition, direction)) {
            robot.t = '.'
            nextPosition.t = '@'
            return nextPosition
        } else {
            return robot
        }
    }
    if (nextPosition.t == ']') {
        val n = maze.at(nextPosition.location + Direction4.West)!!
        if (moveBox2(maze, n, direction)) {
            robot.t = '.'
            nextPosition.t = '@'
            return nextPosition
        } else {
            return robot
        }
    }
    error("Unknown tile ${nextPosition.t}")
}

private fun moveBox2(
    maze: CharMaze,
    box: LocatedItem<Char>,
    direction: Direction4
): Boolean {
    val boxRight = maze.at(box.location + Direction4.East)!!
    when (direction) {
        Direction4.West -> {
            val nextPosition = maze.at(box.location + direction)!!
            if (nextPosition.t == '#') { return false }
            if (nextPosition.t == '.') {
                nextPosition.t = '['
                box.t = ']'
                boxRight.t = '.'
                return true
            }
            if (nextPosition.t == ']') {
                val n = maze.at(nextPosition.location + direction)!!
                if (moveBox2(maze, n, direction)) {
                    nextPosition.t = '['
                    box.t = ']'
                    boxRight.t = '.'
                    return true
                } else {
                    return false
                }
            }
            error("Unknown tile ${nextPosition.t}")
        }
        Direction4.East -> {
            val nextPosition = maze.at(boxRight.location + direction)!!
            if (nextPosition.t == '#') { return false }
            if (nextPosition.t == '.') {
                nextPosition.t = ']'
                boxRight.t = '['
                box.t = '.'
                return true
            }
            if (nextPosition.t == '[') {
                if (moveBox2(maze, nextPosition, direction)) {
                    nextPosition.t = ']'
                    boxRight.t = '['
                    box.t = '.'
                    return true
                } else {
                    return false
                }
            }
            error("Unknown tile ${nextPosition.t}")
        }
        else -> {
            return moveBoxNorthSouth(maze, box, direction)
        }
    }
}

private fun moveBoxNorthSouth(
    maze: CharMaze,
    box: LocatedItem<Char>,
    direction: Direction4
): Boolean {
    val boxRight = maze.at(box.location + Direction4.East)!!
    val nextPosition1 = maze.at(box.location + direction)!!
    val nextPosition2 = maze.at(boxRight.location + direction)!!
    if (nextPosition1.t == '#' || nextPosition2.t == '#') {
        return false
    }
    if (nextPosition1.t == '.' && nextPosition2.t == '.') {
        nextPosition1.t = '['
        nextPosition2.t = ']'
        box.t = '.'
        boxRight.t = '.'
        return true
    }
    if (nextPosition1.t == '[' && nextPosition2.t == ']') {
        if (moveBoxNorthSouth(maze, nextPosition1, direction)) {
            nextPosition1.t = '['
            nextPosition2.t = ']'
            box.t = '.'
            boxRight.t = '.'
            return true
        } else {
            return false
        }
    }
    val maze2 = maze.deepCopy()
    if (nextPosition1.t == ']') {
        val leftBox = maze2.at(nextPosition1.location + Direction4.West)!!
        if (!moveBoxNorthSouth(maze2, leftBox, direction)) {
            return false
        }
    }
    if (nextPosition2.t == '[') {
        val leftBox = maze2.at(nextPosition2.location)!!
        if (!moveBoxNorthSouth(maze2, leftBox, direction)) {
            return false
        }
    }
    maze.copyItemsFrom(maze2)
    nextPosition1.t = '['
    nextPosition2.t = ']'
    box.t = '.'
    boxRight.t = '.'
    return true
}
