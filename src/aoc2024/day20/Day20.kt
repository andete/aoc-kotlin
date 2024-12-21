package aoc2024.day20

import day
import util.AStar
import util.CharMaze
import util.LocatedItem
import util.findItem
import util.parseCharMaze

fun main() {
    day(2024, 20) {
        part1(10, "example") { part1(10, it) }
        part1(1399, "input") { part1(100, it) }
        //part2(16L, "example", ::part2)
        //part2(619970556776002, "input", ::part2)
    }
}

private fun part1(saved: Int, data: List<String>): Long {
    val maze = parseCharMaze(data)
    val start = maze.findItem('S')!!
    val goal = maze.findItem('E')!!
    maze.show(listOf(start, goal))
    fun neighboursNoCheat(e: LocatedItem<Char>, visited: List<LocatedItem<Char>>): List<LocatedItem<Char>> {
        return maze.neighbours(e).map { it.second }.filter { it.t != '#' && it !in visited }
    }

    val fastestWithoutCheats = AStar.path(start, goal, { a, _ -> 1 }, ::neighboursNoCheat)
    val moves = fastestWithoutCheats.size - 1
    println("fastest without cheats: $moves: ")
    maze.show(fastestWithoutCheats)
    val cheats = mutableListOf<Int>()
    for (position in fastestWithoutCheats.subList(0, fastestWithoutCheats.size - 1)) {
        cheats.addAll(cheatsAt(position, maze,fastestWithoutCheats))
    }
    val grouped = cheats.groupBy { moves - it }.map {
        it.key to it.value.size
    }.sortedBy { it.first }
    println("(saved,amount)")
    for (g in grouped) {
        println(g)
    }
    return grouped.filter { it.first >= saved }.sumOf { it.second.toLong() }
}

private fun cheatsAt(
    position: LocatedItem<Char>,
    maze: CharMaze,
    fastestWithoutCheats: List<LocatedItem<Char>>,
): List<Int> {
    val cheats = mutableListOf<Int>()
    val i = fastestWithoutCheats.indexOf(position)
    val neighbourWalls = maze.neighbours(position).filter {
        it.second.t == '#'
    }
    neighbourWalls.forEach { (direction, neighbour) ->
        maze.at(neighbour.location + direction)?.let { oneFurther ->
            val i2 = fastestWithoutCheats.indexOf(oneFurther)
            if (i2 > i && (oneFurther.t == '.' || oneFurther.t == 'E')) {
                cheats.add(fastestWithoutCheats.size - 1 -(i2 - i) + 2)
            }
        }
    }
    return cheats
}


private fun part2(data: List<String>): Long {
    return 0
}