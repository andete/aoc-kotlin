package aoc2023.day05

import aoc2023.day05.alt.aoc2023day05altPart2
import day
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
fun main() {
    day(2023, 5) {
        part1(35L, "test", ::part1)
        part1(177942185L, "input", ::part1)
        part2(46L, "test", ::aoc2023day05altPart2)
        part2(69841803L, "input", ::aoc2023day05altPart2)
    }
}