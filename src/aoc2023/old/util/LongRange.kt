package be.damad.aoc2023.util

import kotlin.math.max
import kotlin.math.min

fun minus(range: LongRange, other: LongRange): List<LongRange> {
    if (range.last < other.first || range.first > other.last) {
        return listOf(range)
    }
    val res = mutableListOf<LongRange>()
    if (range.first < other.first) {
        res.add(LongRange(range.first, other.first - 1))
    }
    if (range.last > other.last) {
        res.add(LongRange(other.last + 1, range.last))
    }
    return res
}

fun longRangeIntersect(range: LongRange, other: LongRange): LongRange? {
    if (other.first > range.last || range.first > other.last) {
        return null
    }
    val oFirst =max(range.first, other.first)
    val oLast = min(range.last, other.last)
    return LongRange(oFirst, oLast)
}