package aoc2022.day01

import day

fun main() {
    day(2022, 1) {
        part1(24000, "example", ::part1)
        part1(70613, "input", ::part1)
        part2(45000, "example", ::part2)
        part2(205805, "input", ::part2)

    }
}

private fun part1(data: List<String>): Long {
    val res = mutableListOf<Long>()
    var acc = 0L
    data.forEach { t ->
        if (t == "") {
            res.add(acc)
            acc = 0L
        } else {
            acc += t.toLong()
        }
    }
    res.add(acc)
    return res.max()
}

private fun part2(data: List<String>): Long {
    val res = mutableListOf<Long>()
    var acc = 0L
    data.forEach { t ->
        if (t == "") {
            res.add(acc)
            acc = 0L
        } else {
            acc += t.toLong()
        }
    }
    res.add(acc)
    return res.sorted().takeLast(3).sum()
}