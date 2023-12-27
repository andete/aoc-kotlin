package aoc2023.day05.alt

import be.damad.aoc2023.util.longRangeIntersect
import be.damad.aoc2023.util.minus
import kotlin.math.max
import kotlin.math.min


private data class Data(val dataRanges: List<LongRange>) {
    override fun toString() = "$dataRanges"
}

private data class RangeMap(val fromRange: LongRange, val offset: Long) {
    fun map(i: Long): Long? {
        if (i !in fromRange) {
            return null
        }
        return i + offset
    }

    fun map(r: LongRange) = LongRange(r.first + offset, r.last + offset)

    override fun toString() = "($fromRange|$offset)"
}

private data class Mapping(val from: String, val to: String, val mappingRanges: List<RangeMap>)

private data class Input(val seeds: Data, val mappings: List<Mapping>)



private operator fun LongRange.times(mapping: Mapping): List<LongRange> {
    val inputs = mutableListOf(this)
    val outputs = mutableListOf<LongRange>()
    for (mappingRange in mapping.mappingRanges) {
        inputs.toList().forEach { input ->
            // intersection goes to output
            val int = longRangeIntersect(input, mappingRange.fromRange)
            int?.let {
                outputs.add(mappingRange.map(it))
            }
            inputs.remove(input)
            // remaining goes back to input
            inputs.addAll(minus(input , mappingRange.fromRange))
        }
    }
    return inputs + outputs
}

private fun List<LongRange>.simplify(): List<LongRange> {
    var res = mutableListOf<LongRange>()
    for (range in this) {
        val overlappingOrBordering = res.firstOrNull {
            it.first == range.last + 1
                    || it.last == range.first - 1
                    || it.first in range
                    || it.last in range
                    || range.first in it
                    || range.last in it
        }
        if (overlappingOrBordering != null) {
            res.remove(overlappingOrBordering)
            res.add(
                LongRange(
                    min(range.first, overlappingOrBordering.first),
                    max(range.last, overlappingOrBordering.last)
                )
            )
        } else {
            res.add(range)
        }
    }
    return res
}

private operator fun Data.times(mapping: Mapping): Data {
    return Data(dataRanges = dataRanges.flatMap {
        it * mapping
    }.sortedBy { it.first }.simplify())
}

private fun parse(data: List<String>): Input {
    val seedRanges =
        data[0].split(':')[1].split(' ').filter { it.isNotEmpty() }.chunked(2).map {
            LongRange(it[0].toLong(), it[0].toLong() + it[1].toLong() - 1)
        }.sortedBy { it.first }

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
                    listOf(
                        RangeMap(
                            LongRange(source, source + size - 1),
                            destination - source
                        )
                    )
                )
            )
        }
        ++i
    }
    val joinedMaps = maps.groupBy { it.from }
        .map {
            Mapping(
                it.value.first().from,
                it.value.first().to,
                it.value.flatMap { it.mappingRanges }.sortedBy { it.fromRange.first })
        }
    return Input(Data(seedRanges), joinedMaps)
}

private fun calculate(input: Input): Long {
    val result = input.mappings.fold(input.seeds) { data, mapping ->
        val x = data * mapping
        println("$data * ${mapping.from}-${mapping.to}${mapping.mappingRanges} => $x")
        x
    }
    return result.dataRanges.minByOrNull { it.first }!!.first
}

fun aoc2023day05altPart2(input: List<String>) = calculate(parse(input))


