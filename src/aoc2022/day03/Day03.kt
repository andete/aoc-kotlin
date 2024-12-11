package aoc2022.day03

import day

fun main() {
    day(2022, 3) {
        part1(157, "example", ::part1)
        part1(8153, "input", ::part1)
        part2(70, "example", ::part2)
        part2(2342, "input", ::part2)

    }
}

private fun priority(c: Char): Int {
    return if (c.isLowerCase()) {
        1 + c.code - 'a'.code
    } else {
        27 + c.code - 'A'.code
    }
}

private fun oddOne(rucksack: String): Char {
    val l = rucksack.length
    val compartment1 = rucksack.substring(0 until l/2).toSet()
    val compartment2 = rucksack.substring(l/2 until l).toSet()
    val inBoth = compartment1.intersect(compartment2)
    return inBoth.single()
}

private fun part1(data: List<String>): Long {
    val inBoth = data.map { oddOne(it) }
    return inBoth.sumOf {
        priority(it).toLong()
    }
}

private fun part2(data: List<String>): Long {
    return data.chunked(3).map {
        badge(it)
    }.sumOf { priority(it).toLong() }
}

private fun badge(strings: List<String>): Char {
    val elf1 = strings[0].toSet()
    val elf2 = strings[1].toSet()
    val elf3 = strings[2].toSet()
    return elf1.intersect(elf2).intersect(elf3).single()
}
