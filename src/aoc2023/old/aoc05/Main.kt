package be.damad.aoc2023.aoc05

import kotlin.math.max
import kotlin.math.min

private data class Mapping(val from: String, val to: String, var fromRange: LongRange, val toRange: LongRange) {
    fun map(i: Long) = toRange.first + i - fromRange.first
    fun revMap(i: Long) = fromRange.first + i - toRange.first
}

private fun part1(data: List<String>, part2: Boolean = false): Long {

    val seeds = if (!part2) {
        data[0].split(':')[1].split(' ').filter { it.isNotEmpty() }.map { it.toLong() }
    } else {
        data[0].split(':')[1].split(' ').filter { it.isNotEmpty() }.chunked(2).flatMap {
            LongRange(it[0].toLong(), it[0].toLong() + it[1].toLong() - 1).toList()
        }
    }

    var i = 1
    var name = ""
    var from = ""
    var to = ""
    val maps = mutableListOf<Mapping>()
    while (i < data.size) {
        val line = data[i]
        if (line.contains("map")) {
            name = line.split(' ')[0]
            val nameElements = name.split('-')
            from = nameElements[0]
            to = nameElements[2]
        }
        if (line.firstOrNull()?.isDigit() == true) {
            val x = line.split(' ')
            val destination = x[0].toLong()
            val source = x[1].toLong()
            val size = x[2].toLong()
            maps.add(
                Mapping(
                    from,
                    to,
                    LongRange(source, source + size - 1),
                    LongRange(destination, destination + size - 1)
                )
            )
        }
        ++i
    }
    val mapsByType = maps.groupBy { it.from }
    var result = Long.MAX_VALUE
    for (seed in seeds) {
        var i = seed
        for (maps in mapsByType.values) {
            val map = maps.firstOrNull { i in it.fromRange }
            i = map?.map(i) ?: i
        }
        result = min(i, result)
    }
    return result
}

private fun part2(data: List<String>): Long {

    val seedRanges =
        data[0].split(':')[1].split(' ').filter { it.isNotEmpty() }.chunked(2).map {
            LongRange(it[0].toLong(), it[0].toLong() + it[1].toLong() - 1)
        }


    var i = 1
    var name = ""
    var from = ""
    var to = ""
    val maps = mutableListOf<Mapping>()
    while (i < data.size) {
        val line = data[i]
        if (line.contains("map")) {
            name = line.split(' ')[0]
            val nameElements = name.split('-')
            from = nameElements[0]
            to = nameElements[2]
        }
        if (line.firstOrNull()?.isDigit() == true) {
            val x = line.split(' ')
            val destination = x[0].toLong()
            val source = x[1].toLong()
            val size = x[2].toLong()
            maps.add(
                Mapping(
                    from,
                    to,
                    LongRange(source, source + size - 1),
                    LongRange(destination, destination + size - 1)
                )
            )
        }
        ++i
    }
    val mapsByType =
        maps.groupBy { it.from }.map { entry -> entry.key to entry.value.sortedBy { it.fromRange.first } }.toMap()
    val result = mutableListOf<Long>()
    for (seedRange in seedRanges) {
        println(seedRange)
        var ranges = listOf(seedRange)
        for (mapType in mapsByType.keys) {
            val maps = mapsByType[mapType]!!
            ranges = ranges.flatMap { range ->
                val start = maps.firstOrNull { range.first in it.fromRange }
                val finish = maps.firstOrNull { range.last in it.fromRange }
                if (start == null && finish == null) {
                    listOf(range)
                } else if (start != null && start == finish) {
                    listOf(LongRange(start.map(range.first), start.map(range.last)))
                } else if (start != null && finish == null) {
                    listOf(
                        LongRange(start.map(range.first), start.map(start.fromRange.last)),
                        LongRange(start.fromRange.last + 1, range.last)
                    )
                } else if (start == null && finish != null) {
                    listOf(
                        LongRange(range.first, finish.fromRange.first - 1),
                        LongRange(finish.map(finish.fromRange.first), finish.map(range.last)),
                    )
                } else if (start != finish) {
                    listOf(
                        LongRange(start!!.map(range.first), start.map(start.fromRange.last)),
                        LongRange(start.fromRange.last + 1, finish!!.fromRange.first - 1),
                        LongRange(finish.map(finish.fromRange.first), finish.map(range.last)),
                    )
                } else {
                    error("illegal")
                }
            }.filter { it.first <= it.last }.sortedBy { it.first }
            println(ranges)
        }
        result.add(ranges.minOfOrNull { it.first }!!)
    }
    println(result)
    return result.min()
}

fun main() {
    check(35L == part1(aoc05TestData))
    check(177942185L == part1(aoc05data))
    check(46L == part1(aoc05TestData, true))
    check(46L == part2(aoc05TestData))
    println(part2(aoc05data))
}