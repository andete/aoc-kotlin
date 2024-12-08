package aoc2024.day03

import day

fun main() {
    day(2024, 3) {
        part1(161, "example", ::part1)
        part1(479, "input", ::part1)
        //part2(4, "example", ::part2)
        //part2(531, "input", ::part2)

    }
}

private fun part1(data: List<String>): Long {
    val data = data.joinToString("")
    val regex = Regex("mul\\(\\d\\d?\\d?,\\d\\d?\\d?\\)")
    val muls = regex.findAll(data).map { it.value }.toList()
    return muls.sumOf {
        val x = it.substring(4, it.length - 1).split(",").map { it.toLong() }
        x[0] * x[1]
    }
}