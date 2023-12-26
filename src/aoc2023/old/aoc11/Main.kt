package be.damad.aoc2023.aoc11

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

private val testData = """...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....""".split('\n')


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
    val yIndices = map.indices.filter {i ->
        map[i].all { it == '.' }
    }
    val xIndices = map[0].indices.filter { i ->
        map.all {
            it[i] == '.'
        }
    }
    return xIndices.toHashSet() to yIndices.toHashSet()
}

private data class Location(val x: Int, val y: Int)

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


private fun <T>combinations(l: List<T>): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()
    for (i in 0 until (l.size - 1)) {
        for (t in l.subList(i + 1, l.size)) {
            result.add(l[i] to t)
        }
    }
    return result
}

private fun calculate(map: List<String>): Long {
    val e = expand(map)
    val l = locations(e)
    val c = combinations(l)
    return c.sumOf { distance2(hashSetOf(), hashSetOf(), it.first, it.second, 1) }
}

private fun calculate2(map: List<String>, factor: Int): Long {
    val (xIndices, yIndices) = expand2(map)
    val l = locations(map)
    val c = combinations(l)
    return c.sumOf { distance2(xIndices, yIndices, it.first, it.second, factor) }
}

fun main() {
    println(expand(testData).joinToString("\n"))
    val testLocations = locations(expand(testData))
    println(distance(testLocations[0], testLocations[6]))
    val res = calculate(testData)
    println(res)
    check(374L == res)
    val res1 = calculate(aoc11data)
    println(res1)
    check(9609130L == res1)
    val res2 = calculate2(testData, 10)
    println(res2)
    check(1030L == res2)
    val res3 = calculate2(testData, 100)
    println(res3)
    check(8410L == res3)
    val res4 = calculate2(aoc11data, 1000000)
    println(res4)
    check(702152204842L == res4)
}