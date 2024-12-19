package aoc2024.day19

import day

fun main() {
    day(2024, 19) {
        part1(6, "example", ::part1)
        part1(296, "input", ::part1)
        part2(16L, "example", ::part2)
        part2(619970556776002, "input", ::part2)
    }
}

private fun possible(patterns: List<String>, design: String): Boolean {
    if (design.isEmpty()) {
        return true
    }
    for (pattern in patterns) {
        if (design.startsWith(pattern)) {
            if (possible(patterns, design.substring(pattern.length, design.length))) {
                return true
            }
        }
    }
    return false
}

private fun ways(patterns: List<String>, design: String, cache: MutableMap<String,Long>): Long {
    if (design.isEmpty()) {
        return 1
    }
    var ways = 0L
    for (pattern in patterns) {
        if (design.startsWith(pattern)) {
            val remainder = design.substring(pattern.length, design.length)
            val c = cache[remainder]
            if (c != null) {
                ways += c
            } else {
                val d = ways(patterns, remainder, cache)
                cache[remainder] = d
                ways += d
            }
        }
    }
    return ways
}

private fun part1(data: List<String>): Int {
    val patterns = data[0].split(", ")
    val designs = data.subList(2, data.size)
    println("$patterns $designs")
    return designs.count { possible(patterns, it) }
}

private fun part2(data: List<String>): Long {
    val patterns = data[0].split(", ")
    val designs = data.subList(2, data.size)
    println("$patterns $designs")
    val cache = mutableMapOf<String, Long>()
    return designs.sumOf { ways(patterns, it, cache) }
}
