package aoc2024.day12

import day
import util.CharMaze
import util.CharProvider
import util.ItemMaze
import util.LocatedItem
import util.createItemMaze
import util.location.Direction4
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
        part2(1206, "example2", ::part2)
        part2(902620, "input", ::part2)

    }
}

private fun paint1(visited: MutableList<Location>, maze: CharMaze, cropPlot: LocatedItem<Char>): Pair<Long, Long>? {
    if (cropPlot.location in visited) {
        return null
    }
    return paint1sub(visited, maze, cropPlot, cropPlot.t)
}

private fun paint1sub(
    visited: MutableList<Location>,
    maze: CharMaze,
    cropPlot: LocatedItem<Char>,
    crop: Char
): Pair<Long, Long> {
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
            val (na, nv) = paint1sub(visited, maze, neighbour.second!!, crop)
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
            paint1(visited, maze, maze.at(x, y)!!)?.let { (area, perimeter) ->
                res += area * perimeter
            }
        }
    }
    return res
}

private fun paint(
    maze: ItemMaze<CropPlot>,
    location: Location,
    cropGroup: Int,
    visited: HashSet<Location> = hashSetOf()
): List<Location> {
    val res = mutableListOf<Location>()
    val cropPlot = maze.at(location)!!
    val crop = cropPlot.t.crop
    cropPlot.t.cropGroup = cropGroup
    res.add(location)
    visited.add(location)
    maze.neighbours(cropPlot).forEach { t ->
        if (t.second.t.crop == crop && t.second.location !in visited) {
            res += paint(maze, t.second.location, cropGroup, visited)
        }
    }
    return res
}

private data class CropPlot(
    val crop: Char,
    var fences: MutableMap<Direction4, Int> = mutableMapOf(),
    var cropGroup: Int? = null
) : CharProvider {
    override fun toChar(): String {
        val group = cropGroup ?: " "
        val edges = Direction4.entries.map {
            //val fenceId = fences[it] ?: -1
            //val fenceIdS = if (fenceId != -1) {
            //    fenceId.toString()
            //} else {
            //    " "
            //}
            if (fences.containsKey(it)) {
                it.toChar()
            } else {
                " "
           }
        }.sorted().joinToString("")
        return "$crop$group$edges "
    }
}

private fun part2(data: List<String>): Long {
    val maze = createItemMaze(data[0].length, data.size) { x, y ->
        CropPlot(data[y][x])
    }
    // maze.show()
    var res = 0L

    // identify crop groups and their area
    val numberOfGroups = calculateGroupsAndAreas(maze)
    // maze.show()
    for (group in 0 until numberOfGroups) {
        calculateSidesForGroup(maze, group)
    }
    // maze.show()
    for (group in 0 until numberOfGroups) {
        val sides = calculateFenceNumbersForSidesForGroup(maze, group)
        val area = maze.rows.flatten().count { it.t.cropGroup == group }
        res += sides * area
    }
    // maze.show()
    return res
}

private fun calculateFenceNumbersForSidesForGroup(maze: ItemMaze<CropPlot>, group: Int): Int {
    val cropPlots = maze.rows.flatten().filter { it.t.cropGroup == group && it.t.fences.isNotEmpty() }
    // start with a corner crop plot with North and West side
    var fenceNumber = 0
    while (cropPlots.any { it.t.fences.values.any { it == -1 }}) {
        val start = cropPlots.first {
            val fences = it.t.fences
            val fenceDirs = fences.keys.toList()
            val fenceIds = fences.values.toList()
            // always start on a corner (>= 2) .. can't really for some inside fences
            fenceDirs.isNotEmpty() && fenceIds.any { it == -1 }
        }
        var position = start
        var direction = position.t.fences.keys.first { position.t.fences[it] == -1 }
        var moveDirection = direction.rotate90()
        while (position.t.fences.values.any { it == -1 }) {
            position.t.fences[direction] = fenceNumber
            val nextPosition = maze.at(position.location + moveDirection)

            if (nextPosition?.t?.cropGroup == group && nextPosition.t.fences.keys.contains(direction) == true) {
                position = nextPosition
                continue
            } else {
                fenceNumber++
                if (position.t.fences.keys.contains(direction.rotate90())) {
                    // either rotate clockwise, then we stay on the same cropPlot
                    // _
                    // X|
                    direction = direction.rotate90()
                    moveDirection = direction.rotate90()
                } else {
                    // or rotate anti-clockwise, then we move to another plot
                    direction = direction.rotate90cc()
                    moveDirection = direction.rotate90()
                    position = maze.at(position.location + moveDirection + -direction) ?: error("can't rotate 90cc")
                    // X|_
                    // X X

                }
            }
        }
    }
    return fenceNumber
}

private fun calculateSidesForGroup(maze: ItemMaze<CropPlot>, i: Int) {
    val cropPlots = maze.rows.flatten().filter { it.t.cropGroup == i }
    val crop = cropPlots[0].t.crop
    for (cropPlot in cropPlots) {
        val edges = maze.neighboursWithNull(cropPlot).filter { it.second?.t?.crop != crop }
        for (edge in edges) {
            cropPlot.t.fences[edge.first] = -1
        }
    }
}

private fun calculateGroupsAndAreas(
    maze: ItemMaze<CropPlot>
): Int {
    val visited = hashSetOf<Location>()
    var currentCropGroup = 0
    val areas = mutableMapOf<Int, Int>()
    for (y in maze.yIndices) {
        for (x in maze.xIndices) {
            val location = Location(x, y)
            if (location !in visited) {
                val sameCropLocations = paint(maze, Location(x, y), currentCropGroup)
                sameCropLocations.forEach { t -> visited.add(t) }
                // println("${maze.at(location)!!.t}: $sameCropLocations")
                areas[currentCropGroup] = sameCropLocations.size
                currentCropGroup++
            }
        }
    }
    return currentCropGroup
}