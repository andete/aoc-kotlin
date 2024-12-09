package aoc2024.day07

import day

fun main() {
    day(2024, 7) {
        part1(3749, "example", ::part1)
        part1(850435817339, "input", ::part1)
        part2(11387, "example", ::part2)
        part2(104824810233437, "input", ::part2)
    }
}

private fun parse(line: String): Pair<Long, List<Long>> {
    val data = line.split(' ')
    val res = data[0].substring(0, (data[0].length-1)).toLong()
    val other = data.subList(1, data.size).map { it.toLong() }
    return res to other
}

private fun canMake(res: Long, other: List<Long>): Boolean {
    if (other.size == 1) {
        return other[0] == res
    }
    if (other[0] > res) {
        return false
    }
    val a = other[0]
    val b = other[1]
    val x = a + b
    val y = a * b
    val v = canMake(res, listOf(x) + other.subList(2, other.size))
    if (v) { return true }
    val w = canMake(res, listOf(y) + other.subList(2, other.size))
    return w
}

private fun canMake2(res: Long, other: List<Long>): Boolean {
    if (other.size == 1) {
        return other[0] == res
    }
    if (other[0] > res) {
        return false
    }
    val a = other[0]
    val b = other[1]
    val x = a + b
    val y = a * b
    val z = "$a$b".toLong()
    val rem = other.subList(2, other.size)
    val v = canMake2(res, listOf(x) + rem)
    if (v) { return true }
    val w = canMake2(res, listOf(y) + rem)
    if (w) { return true }
    val u = canMake2(res, listOf(z) + rem)
    return u

}

private fun part1(data: List<String>): Long {
    return data.sumOf {
        val (res, other) = parse(it)
        if (canMake(res, other)) {
            res
        } else {
            0L
        }
    }
}

private fun part2(data: List<String>): Long {
    return data.sumOf {
        val (res, other) = parse(it)
        if (canMake2(res, other)) {
            res
        } else {
            0L
        }
    }
}