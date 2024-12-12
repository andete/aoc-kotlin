package aoc2024.day12

import day
import util.CharMaze
import util.LocatedItem
import util.location.Location
import util.parseCharMaze

fun main() {
    day(2024, 12) {
        part1(140, "example1", ::part1)
        part1(772, "example1a", ::part1)
        part1(1930, "example2", ::part1)
        part1(1473620, "input", ::part1)
        part2(80, "example1", ::part2)
        part2(436, "example1a", ::part2)
        part2(236, "example1e", ::part2)
        part2(368, "example1ab", ::part2)
        part2(1206, "example2", ::part1)
        part2(23177084, "input", ::part2)

    }
}

private fun paint(visited: MutableList<Location>, maze: CharMaze, cropPlot: LocatedItem<Char>): Pair<Long, Long>? {
    if (cropPlot.location in visited) {
        return null
    }
    return paint2(visited, maze, cropPlot, cropPlot.t)
}

private fun paint2(visited: MutableList<Location>, maze: CharMaze, cropPlot: LocatedItem<Char>, crop: Char): Pair<Long, Long> {
    if (cropPlot.location in visited) {
        return 0L to 0L
    }
    if (cropPlot.t != crop) {
        return 0L to 0L
    }
    var area = 1L
    visited.add(cropPlot.location)
    var perimeter = 0L
    for (neighbour in maze.neighboursWithNull(cropPlot)) {
        if (neighbour.second?.t != crop) {
            perimeter++
        } else {
            val (na, nv) = paint2(visited, maze, neighbour.second!!, crop)
            area += na
            perimeter += nv
        }
    }
    return area to perimeter
}

private fun part1(data: List<String>): Long {
    val maze = parseCharMaze(data)
    val visited = mutableListOf<Location>()
    var res = 0L
    for (y in maze.yIndices) {
        for (x in maze.xIndices) {
            paint(visited, maze, maze.at(x, y)!!)?.let { (area, perimeter) ->
                res += area * perimeter
            }
        }
    }
    return res
}

private fun part2(data: List<String>): Long {
    return 0
}