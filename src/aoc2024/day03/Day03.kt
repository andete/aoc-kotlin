package aoc2024.day03

import day

fun main() {
    day(2024, 3) {
        part1(161, "example", ::part1)
        part1(160672468, "input", ::part1)
        part2(48, "example2", ::part2)
        part2(84893551, "input", ::part2)

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

private fun part2(data: List<String>): Long {
    val data = data.joinToString("")
    val regex = Regex("(mul\\(\\d\\d?\\d?,\\d\\d?\\d?\\))|(do\\(\\))|(don't\\(\\))")
    val instructions = regex.findAll(data).map { it.value }.toList()
    var disabled = false
    var sum = 0L
    for (instruction in instructions) {
        if (instruction == "do()") {
            disabled = false
        } else if (instruction == "don't()") {
            disabled = true
        } else if (!disabled) {
            val x = instruction.substring(4, instruction.length - 1).split(",").map { it.toLong() }
            sum += x[0] * x[1]
        }
    }
    return sum
}