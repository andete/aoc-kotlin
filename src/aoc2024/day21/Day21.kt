package aoc2024.day21

import day
import util.AStar
import util.CharMaze
import util.LocatedItem
import util.find
import util.findItem
import util.location.Direction4
import util.location.Location
import util.parseCharMaze

fun main() {
    day(2024, 21) {
        part1(10, "example", ::part1)
        part1(1399, "input", ::part1)
        //part2(16L, "example", ::part2)
        //part2(619970556776002, "input", ::part2)
    }
}

private val keypad = """
789
456
123
 0A""".split("\n").filter { it.isNotEmpty() }

private val directionalPad = """
 ^A
<v>""".split("\n").filter { it.isNotEmpty() }

private fun part1(data: List<String>): Long {
    // The door.
    val doorKeypad = parseCharMaze(keypad)
    val robot1Location = doorKeypad.find('A')!!
    // robot 1 is at the door, hovering "A" on the doorKeypad
    val directionalPadRobot1 = parseCharMaze(directionalPad)
    val robot2Location = directionalPadRobot1.find('A')!!
    // robot2 is at the control of robot 1, hovering the "A"
    val directionalPadRobot2 = parseCharMaze(directionalPad)
    // I am at the control of robot 2
    val directionalPadMe = parseCharMaze(directionalPad)
    for (keypadPresses in data) {
        var robot1Location = robot1Location
        val robot1Moves = mutableListOf<Char>()
        for (keyPress in keypadPresses) {
            moveToKeyAndPress(doorKeypad, keyPress, robot1Location)
        }
    }
    doorKeypad.show()
    directionalPadRobot1.show()
    return 0
}

private fun moveToKeyAndPress(maze: CharMaze, ch: Char, location: Location): Pair<Location, List<Char>> {
    val entry = maze.at(location)!!
    if (maze.at(location)!!.t == ch) {
        return location to listOf('A')
    }
    val destination = maze.findItem(ch)!!
    fun neighbours(x: LocatedItem<Char>, visited: List<LocatedItem<Char>>): List<LocatedItem<Char>> {
        return maze.neighbours(entry).map { it.second }.filter { it.t != ' ' }
    }
    val path = AStar.path(entry, destination, { _,_ -> 1 }, ::neighbours)
    TODO()
}
