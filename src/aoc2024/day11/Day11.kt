package aoc2024.day11

import day

fun main() {
    day(2024, 11) {
        part1(7, "example") { part1(1, it) }
        part1(22, "example2") { part1(6, it) }
        part1(183248, "input") { part1(25, it) }
        part2(7, "example") { part2(1, it) }
        part2(183248, "input") { part2(25, it) }
        part2(183248, "input") { part2(75, it) }
    }
}

private fun blinkOne(input: Long): List<Long> {
    if (input == 0L) { return listOf(1L) }
    val s = input.toString()
    if (s.length % 2 == 0) {
        val s1 = s.substring(0 until (s.length/2))
        val s2 = s.substring((s.length/2) until s.length)
        return listOf(s1.toLong(), s2.toLong())
    }
    return listOf(input * 2024)
}

private fun blinkAll(stones: List<Long>): List<Long> {
    return stones.map { blinkOne(it) }.flatten()
}

private fun part1(times: Int, data: List<String>): Long {
    var stones = data[0].split(' ').map { it.toLong() }
    repeat(times) { i ->
        stones = blinkAll(stones)
    }
    return stones.size.toLong()
}

private fun blinkAll2(stones: Map<Long, Long>): Map<Long, Long> {
    val result = mutableMapOf<Long, Long>()
    stones.forEach { stone, amount ->
        val newStones = blinkOne(stone)
        newStones.forEach { t -> result[t] = result.getOrDefault(t, 0L) + amount }
    }
    return result
}


private fun part2(times: Int, data: List<String>): Long {
    var stones = data[0].split(' ').associate { it.toLong() to 1L }
    repeat(times) { i ->
        stones = blinkAll2(stones)
    }
    return stones.values.sum()
}