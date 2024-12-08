package aoc2024.day04

import day

fun main() {
    day(2024, 4) {
        part1(18, "example", ::part1)
        part1(2532, "input", ::part1)
        part2(9, "example", ::part2)
        part2(1941, "input", ::part2)

    }
}

private fun part1(data: List<String>): Long {
    var res = 0L
    for (x in data[0].indices) {
        for (y in data.indices) {
            res += xmasAt(data, x, y)
        }
    }
    return res
}

private fun charAt(data: List<String>, x: Int, y: Int): Char {
    val row = data.getOrNull(y) ?: return '.'
    return row.getOrNull(x) ?: '.'
}

private fun xmasAt(data: List<String>, x: Int, y: Int): Long {
    var res = 0L
    val c = charAt(data, x, y)
    if (c != 'X') {
        return 0
    }
    if (masAtLocations(data, listOf(x + 1 to y, x + 2 to y, x + 3 to y))) {
        res++
    }
    if (masAtLocations(data, listOf(x - 1 to y, x - 2 to y, x - 3 to y))) {
        res++
    }
    if (masAtLocations(data, listOf(x to y + 1, x to y + 2, x to y + 3))) {
        res++
    }
    if (masAtLocations(data, listOf(x to y - 1, x to y - 2, x to y - 3))) {
        res++
    }
    if (masAtLocations(data, listOf(x + 1 to y + 1, x + 2 to y + 2, x + 3 to y + 3))) {
        res++
    }
    if (masAtLocations(data, listOf(x - 1 to y - 1, x - 2 to y - 2, x - 3 to y - 3))) {
        res++
    }
    if (masAtLocations(data, listOf(x + 1 to y - 1, x + 2 to y - 2, x + 3 to y - 3))) {
        res++
    }
    if (masAtLocations(data, listOf(x - 1 to y + 1, x - 2 to y + 2, x - 3 to y + 3))) {
        res++
    }
    return res
}

private fun masAtLocations(
    strings: List<String>,
    pairs: List<Pair<Int, Int>>
): Boolean {
    if (charAt(strings, pairs[0].first, pairs[0].second) != 'M') { return false }
    if (charAt(strings, pairs[1].first, pairs[1].second) != 'A') { return false }
    if (charAt(strings, pairs[2].first, pairs[2].second) != 'S') { return false }
    return true
}

private fun xmasAt2(data: List<String>, x: Int, y: Int): Boolean {
    val c = charAt(data, x, y)
    if (c != 'A') {
        return false
    }
    val l1 = listOf(x - 1 to y - 1, x + 1 to y + 1)
    val l2 = listOf(x + 1 to y - 1, x - 1 to y + 1)
    val ms1 = l1.map { charAt(data, it.first, it.second) }.sorted().joinToString("")
    val ms2 = l2.map { charAt(data, it.first, it.second) }.sorted().joinToString("")

    return ms1 == "MS" && ms2 == "MS"
}

private fun part2(data: List<String>): Long {
    var res = 0L
    for (x in data[0].indices) {
        for (y in data.indices) {
            if (xmasAt2(data, x, y)) {
                res++
            }
        }
    }
    return res
}