package aoc2024.day16

import day
import util.AStar2
import util.findItem
import util.location.Direction4
import util.location.Location
import util.parseCharMaze

fun main() {
    day(2024, 16) {
        part1(7036, "example", ::part1)
        part1(11048, "example2", ::part1)
        part1(82460, "input", ::part1)
        //part2(48, "example1", ::part2)
        //part2(84893551, "input", ::part2)

    }
}

private data class Reindeer(val at: Location, val d: Direction4)

private fun part1(data: List<String>): Long {
    val maze = parseCharMaze(data)
    val start = maze.findItem('S')!!
    val end = maze.findItem('E')!!
    val reindeer = Reindeer(start.location, Direction4.East)
    maze.show(listOf(start, end))
    fun goal(r: Reindeer) = r.at == end.location
    fun cost(r1: Reindeer, r2: Reindeer): Int {
        if (r1.at != r2.at) {
            return 1
        }
        return 1000
    }
    fun neighbours(r: Reindeer, visited: List<Reindeer>): List<Reindeer> {
        val res = mutableListOf<Reindeer>()
        maze.at(r.at + r.d)?.let {
            if (it.t != '#') {
                res += Reindeer(it.location, r.d)
            }
        }
        res += Reindeer(r.at, r.d.rotate90())
        res += Reindeer(r.at, r.d.rotate90cc())
        res.filter { it !in visited }
        return res
    }
    val path = AStar2.path(reindeer, ::goal, ::cost, ::neighbours)
    val s1 = path.subList(0, path.size - 1)
    val s2 = path.subList(1, path.size)
    val totalCost = s1.zip(s2).sumOf { (a, b) -> cost(a, b) }
    maze.show(path.map { maze.at(it.at)!! })
    return totalCost.toLong()
}

private fun part2(data: List<String>): Long {
    return 0
}