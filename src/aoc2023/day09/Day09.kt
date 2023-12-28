package aoc2023.day09

import day

private fun reduce(input: List<Long>): List<Long> {
    val result = mutableListOf<Long>()
    for (index in 0 until input.size - 1) {
        result.add(input[index + 1] - input[index])
    }
    return result
}

private fun calculateRanges(input: List<Long>): MutableList<MutableList<Long>> {
    val ranges = mutableListOf(input.toMutableList())
    var calc = input.toList()
    while (true) {
        calc = reduce(calc)
        ranges.add(calc.toMutableList())
        if (calc.all { it == 0L }) {
            break
        }
    }
    return ranges
}

private fun analyse(input: List<Long>): Long {
    val ranges = calculateRanges(input)
    val rev = ranges.reversed()
    rev[0].add(0)
    for (i in 1 until rev.size) {
        rev[i].add(rev[i-1].last() + rev[i].last())
    }
    return rev.last().last()
}

private fun analyse2(input: List<Long>): Long {
    val ranges = calculateRanges(input)
    val rev = ranges.reversed().map { it.reversed().toMutableList() }
    rev[0].add(0)
    for (i in 1 until rev.size) {
        rev[i].add(rev[i].last() - rev[i-1].last())
    }
    return rev.last().last()
}

private fun calculate(data: List<String>, analyse: (List<Long>) -> Long): Long {
    return data.sumOf { line ->
        val one = line.split(' ').map { it.toLong() }
        analyse(one)
    }
}

fun main() {
    day(2023, 9) {
        part1(114L, "example") { calculate(it, ::analyse) }
        part1(1853145119L, "input") { calculate(it, ::analyse) }
        part2(2L, "example") { calculate(it, ::analyse2) }
        part2(923L, "input") { calculate(it, ::analyse2) }

    }
}