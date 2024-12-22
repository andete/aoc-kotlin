package aoc2024.day22

import day

fun main() {
    day(2024, 22) {
        part1(37327623, "example", ::part1)
        part1(13234715490, "input", ::part1)
        part2(16L, "example", ::part2)
        part2(619970556776002, "input", ::part2)
    }
}

private fun evolve(secret: Long): Long {
    // step 1
    val s1 = secret * 64
    val s2 = s1 xor secret
    val s3 = s2 % 16777216
    // step 2
    val s4 = s3 / 32
    val s5 = s4 xor s3
    val s6 = s5 % 16777216
    // step 3
    val s7 = s6 * 2048
    val s8 = s7 xor s6
    val s9 = s8 % 16777216
    return s9
}

private fun part1(data: List<String>): Long {
    val secrets = data.map { it.toLong() }
    return secrets.sumOf {
        var secret = it
        repeat(2000) {
            secret = evolve(secret)
        }
        secret
    }
}

private fun part2(data: List<String>): Long {
    return 0
}
