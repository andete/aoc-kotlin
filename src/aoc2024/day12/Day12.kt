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
        part2(1206, "example2", ::part1)
        part2(23177084, "input", ::part2)

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

private fun paint(maze: ItemMaze<CropPlot>, location: Location, cropGroup: Int, visited: HashSet<Location> = hashSetOf()): List<Location> {
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

private data class CropPlot(val crop: Char, var fences: MutableMap<Direction4, Int> = mutableMapOf(), var cropGroup: Int? = null): CharProvider {
    override fun toChar(): String {
        val group = cropGroup ?: " "
        val edges = Direction4.entries.map {
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
    val maze = createItemMaze(data[0].length, data.size) { x,y ->
        CropPlot(data[y][x])
    }
    maze.show()
    var res = 0L

    // identify crop groups and their area
    val numberOfGroups = calculateGroupsAndAreas(maze)
    maze.show()
    for (group in 0 until numberOfGroups) {
        calculateSidesForGroup(maze, group)
    }
    maze.show()
    for (group in 0 until numberOfGroups) {
        calculateFenceNumbersForSidesForGroup(maze, group)
    }
    return res
}

private fun calculateFenceNumbersForSidesForGroup(maze: ItemMaze<CropPlot>, i: Int) {
    val cropPlots = maze.rows.flatten().filter { it.t.cropGroup == i }
}

private fun calculateSidesForGroup(maze: ItemMaze<CropPlot>, i: Int) {
    val cropPlots = maze.rows.flatten().filter { it.t.cropGroup == i }
    val crop = cropPlots[0].t.crop
    for (cropPlot in cropPlots) {
        val edges = maze.neighboursWithNull(cropPlot).filter { it.second?.t?.crop != crop }
        for (edge in edges) {
            cropPlot.t.fences[edge.first] = - 1
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
                println("${maze.at(location)!!.t}: $sameCropLocations")
                areas[currentCropGroup] = sameCropLocations.size
                currentCropGroup++
            }
        }
    }
    return currentCropGroup
}

private data class EdgeCorner(val direction: Direction4, val first: Location, val second: Location) {
    constructor(direction: Direction4, x1: Int, y1: Int, x2: Int, y2: Int) : this(
        direction,
        Location(x1, y1),
        Location(x2, y2)
    )
}

fun calculateSides(maze: CharMaze, locations: List<Location>): Int {
    val crop = maze.at(locations[0])!!.t
    val edgeLocations = locations.flatMap { location ->
        val cropPlot = maze.at(location)!!
        maze.neighboursWithNull(cropPlot).filter { it.second?.t != crop }.map {
            location to it.first
        }
    }
    val edgeCorners = edgeLocations.map { (loc, dir) ->
        when (dir) {
            Direction4.East -> EdgeCorner(dir, loc.x + 1, loc.y, loc.x + 1, loc.y + 1)
            Direction4.North -> EdgeCorner(dir, loc.x, loc.y, loc.x + 1, loc.y)
            Direction4.West -> EdgeCorner(dir, loc.x, loc.y, loc.x, loc.y + 1)
            Direction4.South -> EdgeCorner(dir, loc.x, loc.y + 1, loc.x + 1, loc.y + 1)
        }
    }
    var sides = 0
    val edgeCornersToSee = edgeCorners.toHashSet()
    while (edgeCornersToSee.isNotEmpty()) {
        val connected = mutableListOf<Location>()
        val start = edgeCornersToSee.first()
        edgeCornersToSee.remove(start)
        var edge = start
        var location = when (edge.direction) {
            Direction4.East -> listOf(edge.first, edge.second).minBy { it.y }
            Direction4.South -> listOf(edge.first, edge.second).maxBy { it.x }
            Direction4.West -> listOf(edge.first, edge.second).maxBy { it.y }
            Direction4.North -> listOf(edge.first, edge.second).minBy { it.x }

        }
        var connector = if (location == edge.first) { edge.second } else { edge.first }
        connected.add(location)
        while (true) {
            val nextEdges = edgeCorners.filter {
                it != edge && (it.first == connector || it.second == connector)
            }
            val nextEdge = if (nextEdges.size == 1) {
                nextEdges[0]
            } else {
                nextEdges.single { it.direction == edge.direction.rotate90() }
            }
            if (nextEdge == start) {
                break
            }
            edgeCornersToSee.remove(nextEdge)
            connected.add(connector)
            edge = nextEdge
            location = connector
            connector = if (edge.first != connector) {
                edge.first
            } else {
                edge.second
            }
        }
        val second = (connected + listOf(connected[0])).subList(1, connected.size + 1)
        val pairs = connected.zip(second)
        var direction: Direction4? = null
        var localSides = 0
        for (pair in pairs) {
            val newDirection = pair.first.direction(pair.second)
            if (newDirection != direction) {
                direction = newDirection
                localSides++
            }
        }
        // hack because we count a side twice if we start in the middle...
        if (localSides % 2 == 1) {
            localSides--
        }
        sides += localSides
    }
    return sides
}
