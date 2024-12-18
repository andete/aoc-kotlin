package aoc2024.day13

import day
import util.location.Location
import util.location.LocationLong
import kotlin.math.absoluteValue
import kotlin.math.min

fun main() {
    day(2024, 13) {
        part1(480, "example", ::part1)
        part1(28059, "input", ::part1)
        part2(875318608908, "example", ::part2)
        part2(102255878088512, "input", ::part2)
    }
}

private data class ClawMachine(val a: LocationLong, val b: LocationLong, val prize: LocationLong)

private fun parse(data: List<String>, extra: Long = 0): List<ClawMachine> {
    val res = mutableListOf<ClawMachine>()
    var a: LocationLong? = null
    var b: LocationLong? = null
    var prize: LocationLong? = null
    for (line in data) {
        if (line == "") continue
        val sp = line.split(" ", "+", ",", "=")
        if (sp[0] == "Button") {
            if (sp[1] == "A:") {
                a = LocationLong(sp[3].toLong(), sp[6].toLong())
            } else {
                b = LocationLong(sp[3].toLong(), sp[6].toLong())
            }
        } else {
            prize = LocationLong(sp[2].toLong() + extra, sp[5].toLong() + extra)
            res.add(ClawMachine(a!!, b!!, prize))
        }
    }
    return res
}

private fun minTokens1(m: ClawMachine): Long? {
    var min: Long? = null
    for (ai in 0L..100) {
        for (bi in 0L..100) {
            if (m.a * ai + m.b * bi == m.prize) {
                val tokens = ai * 3 + bi * 1
                if (min == null || tokens < min) {
                    min = tokens
                }
            }
        }
    }
    return min
}

private fun minTokens2(m: ClawMachine, start: LocationLong): Pair<Long, Long>? {
    for (ai in -100000L..100000) {
        for (bi in -100000L..100000) {
            if (start + m.a * ai + m.b * bi == m.prize) {
                return ai to bi
            }
        }
    }
    return null
}

private fun converge(
    m: ClawMachine,
    currentA: Long = 0L,
    currentB: Long = 0L,
    current: LocationLong = LocationLong(0, 0),
    depth: Int = 0
): Long? {
    if (depth == 100) {
        return null
    }
    if (current == m.prize) {
        return currentA * 3 + currentB
    }
    if (depth == 0) {
        val mx = m.prize.x / m.b.x
        val my = m.prize.y / m.b.y
        val mm = min(mx, my)
        val rem = m.prize - m.b * mm
        return converge(m, currentA, mm, rem, depth + 1)
    }
    if (current.x.absoluteValue < 100 && current.y.absoluteValue < 100) {
        minTokens2(m, current)?.let { (a, b) ->
            val a = a + currentA
            val b = b + currentB
            return a * 3 + b
        } ?: return null
    }
    if (current.x.absoluteValue > current.y.absoluteValue) {
        if (m.a.x > m.b.x) {
            val mx = current.x / m.a.x
            val rem = current - m.a * mx
            return converge(m, currentA + mx, currentB, rem, depth + 1)
        } else {
            val mx = current.x / m.b.x
            val rem = current - m.b * mx
            return converge(m, currentA, currentB + mx, rem, depth + 1)
        }
    } else {
        if (m.a.y > m.b.y) {
            val my = current.y / m.a.y
            val rem = current - m.a * my
            return converge(m, currentA + my, currentB, rem, depth + 1)
        } else {
            val my = current.y / m.b.y
            val rem = current - m.b * my
            return converge(m, currentA, currentB + my, rem, depth + 1)
        }
    }
}

private fun solve(m: ClawMachine): Pair<Long, Long>? {

    // equation solved via wolfram alpha: a * x1 + b * x2 = x3 and a * y1 + b * y2 = y3 solve for a and b
    // a = (x3 y2 - x2 y3)/(x1 y2 - x2 y1) and b = (x3 y1 - x1 y3)/(x2 y1 - x1 y2) and x2 y1!=x1 y2 and x2!=0

    val a1 = m.prize.x * m.b.y - m.b.x * m.prize.y
    val a2 = m.a.x * m.b.y - m.b.x * m.a.y
    if (a1 % a2 != 0L) {
        return null
    }
    val a = a1 / a2
    val b1 = m.prize.x * m.a.y - m.a.x * m.prize.y
    val b2 = m.b.x * m.a.y - m.a.x * m.b.y
    if (b1 % b2 != 0L) {
        return null
    }
    val b = b1 / b2
    return a to b

}

private fun part1(data: List<String>): Long {
    val clawMachines = parse(data)
    return clawMachines.sumOf { minTokens1(it) ?: 0L }
}

private fun part2(data: List<String>): Long {
    val clawMachines = parse(data, 10000000000000)
    return clawMachines.sumOf { solve(it)?.let { (a, b) -> a * 3 + b } ?: 0L }
    return 0
}