package aoc2023.day08

import day
import kotlin.math.max

private data class Transform(val i: String, val left: String, val right: String) {
    fun map(c: Char): String {
        return if (c == 'L') {
            left
        } else {
            right
        }
    }
}

private data class Instructions(val s: String) {
    private var i = 0

    fun next(): Pair<Char, Int> {
        val res = s[i]
        val j = i
        i = (i + 1) % s.length
        return res to j
    }

}

private fun calculate(data: List<String>): Int {
    val instructions = Instructions(data[0])
    val transforms = data.subList(2, data.size).map {
        val j = it.split(' ', ',', '(', '=', ')')
        j[0] to Transform(j[0], j[4], j[6])
    }.toMap()
    var s = "AAA"
    var i = 0
    while (s != "ZZZ") {
        i++
        s = transforms[s]!!.map(instructions.next().first)
    }
    return i
}

private fun part1(data: List<String>): Int {
    val instructions = Instructions(data[0])
    val transforms = data.subList(2, data.size).map {
        val j = it.split(' ', ',', '(', '=', ')')
        j[0] to Transform(j[0], j[4], j[6])
    }.toMap()
    var s = "AAA"
    var i = 0
    while (s != "ZZZ") {
        i++
        s = transforms[s]!!.map(instructions.next().first)
    }
    return i
}

private fun part2(data: List<String>): Long {
    val instructions = Instructions(data[0])
    val transforms = data.subList(2, data.size).associate {
        val j = it.split(' ', ',', '(', '=', ')')
        j[0] to Transform(j[0], j[4], j[6])
    }
    var s = transforms.keys.filter { it.endsWith("A") }
    println(s)
    val q = s.map { investigate(it, transforms, instructions.copy()) }.map { it.first.toLong() }
    println(q)
    return calculateLCM(q)
}

// LCM calculation roughly copy/pasted from https://www.baeldung.com/kotlin/lcm
private fun calculateLCM(a: Long, b: Long): Long {
    val larger = max(a,b)
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

private fun calculateLCM(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = calculateLCM(result, numbers[i])
    }
    return result
}

private fun investigate(s1: String, transforms: Map<String, Transform>, instructions: Instructions): Pair<Int, Int> {
    var x1 = s1
    val seen = mutableListOf(s1 to 0)

    var i = 0
    var zAt = 0
    while (true) {
        val n = instructions.next()
        x1 = transforms[x1]!!.map(n.first)
        i++
        if (x1[2] == 'Z') {
            println("$s1 Z seen at $i $x1")
            zAt = i
        }
        val x1index = seen.indexOf(x1 to n.second)
        if (x1index >= 0) {
            val offset = i-x1index
            println("$s1 loops at $i $x1 $x1index $offset")
            println("$s1 Z at $zAt, ${zAt + offset}, ${zAt + 2*offset}, ...")
            return zAt to offset
        }
        seen.add(x1 to n.second)
    }
}

fun main() {
    day(2023, 8) {
        part1(2, "test1", ::part1)
        part1(6, "test2", ::part1)
        part1(12643, "input", ::part1)
        part2(6L, "test3", ::part2)
        part2(13133452426987L, "input", ::part2)
    }
}