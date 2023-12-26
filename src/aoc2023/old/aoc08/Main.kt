package be.damad.aoc2023.aoc08

import kotlin.math.max

private val testData1 = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)""".split('\n')

private val testData2 = """LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)""".split('\n')

private val testData3 = """LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)""".split('\n')

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

private fun calculate2(data: List<String>): Long {
    val instructions = Instructions(data[0])
    val transforms = data.subList(2, data.size).associate {
        val j = it.split(' ', ',', '(', '=', ')')
        j[0] to Transform(j[0], j[4], j[6])
    }
    var s = transforms.keys.filter { it.endsWith("A") }
    var i = 0L
    while (!s.all { it[2] == 'Z' }) {
        i++
        val x = instructions.next().first
        s = s.map { transforms[it]!!.map(x) }
    }
    return i
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

private fun calculate3(data: List<String>): Long {
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
    val res1 = calculate(testData1)
    println(res1)
    check(res1 == 2)
    val res2 = calculate(testData2)
    println(res2)
    check(res2 == 6)
    val res3 = calculate(aoc08data)
    println(res3)
    check(res3 == 12643)
    val res4 = calculate2(testData3)
    println(res4)
    check(res4 == 6L)
    val res5 = calculate3(testData3)
    println(res5)
    check(res5 == 6L)
    val res6 = calculate3(aoc08data)
    println(res6)
    check(res6 == 13133452426987L)
}