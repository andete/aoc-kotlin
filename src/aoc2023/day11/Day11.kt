package aoc2023.day11

import day
import util.location.Location
import util.combinationPairs
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

private fun expand(map: List<String>): List<String> {
    val expanded = map.toMutableList()
    for (chars in map) {
        expanded.remove(chars)
        if (chars.all { it == '.' }) {
            expanded.add(chars)
        }
        expanded.add(chars)
    }
    var i = 0
    while (i < expanded[0].length) {
        if (expanded.all { it[i] == '.' }) {
            for (j in expanded.indices) {
                val chars = expanded[j]
                expanded[j] = chars.substring(0 until i) + '.' + chars.substring(i)
            }
            ++i
        }
        ++i
    }
    return expanded
}

private fun expand2(map: List<String>): Pair<HashSet<Int>, HashSet<Int>> {
    val yIndices = map.indices.filter { i ->
        map[i].all { it == '.' }
    }
    val xIndices = map[0].indices.filter { i ->
        map.all {
            it[i] == '.'
        }
    }
    return xIndices.toHashSet() to yIndices.toHashSet()
}

private fun locations(map: List<String>): List<Location> {
    return map.flatMapIndexed { yIndex: Int, s: String ->
        s.mapIndexedNotNull { xIndex, c ->
            if (c == '#') {
                Location(xIndex, yIndex)
            } else {
                null
            }
        }
    }
}

private fun distance(a: Location, b: Location): Int {
    return (a.x - b.x).absoluteValue + (a.y - b.y).absoluteValue
}

private fun distance2(xIndices: HashSet<Int>, yIndices: HashSet<Int>, a: Location, b: Location, factor: Int): Long {
    var distance = 0L
    for (x in min(a.x, b.x) until max(a.x, b.x)) {
        if (x in xIndices) {
            distance += factor
        } else {
            distance += 1
        }
    }
    for (y in min(a.y, b.y) until max(a.y, b.y)) {
        if (y in yIndices) {
            distance += factor
        } else {
            distance += 1
        }
    }
    return distance
}

private fun part1(map: List<String>): Long {
    val e = expand(map)
    val l = locations(e)
    val c = combinationPairs(l)
    return c.sumOf { distance2(hashSetOf(), hashSetOf(), it.first, it.second, 1) }
}

private fun part2(map: List<String>, factor: Int): Long {
    val (xIndices, yIndices) = expand2(map)
    val l = locations(map)
    val c = combinationPairs(l)
    return c.sumOf { distance2(xIndices, yIndices, it.first, it.second, factor) }
}

fun main() {
    day(2023, 11) {
        part1(374L, "test", ::part1)
        part1(9609130L, "input", ::part1)
        part2(1030L, "test") { part2(it, 10) }
        part2(8410L, "test") { part2(it, 100) }
        part2(702152204842L, "input") { part2(it, 1000000) }

    }
}