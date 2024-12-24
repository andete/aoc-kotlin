package aoc2024.day23

import day
import util.combinationPairs
import util.combinationSets

fun main() {
    day(2024, 23) {
        part1(7L, "example", ::part1)
        part1(1302L, "input", ::part1)
        part2("co,de,ka,ta", "example", ::part2)
        part2("cb,df,fo,ho,kk,nw,ox,pq,rt,sf,tq,wi,xz", "input", ::part2)

    }
}

private data class Interconnect(val a: String, val b: String) {
    override fun toString() = "$a-$b"
    operator fun contains(x: String) = x == a || x == b
}

private fun parse(data: List<String>): List<Interconnect> {
    return data.map {
        val computers = it.split("-").sorted()
        Interconnect(computers[0], computers[1])
    }
}

private fun part1(data: List<String>): Long {
    val interconnects = parse(data)
    println(interconnects)
    val computerSets = mutableSetOf<Set<String>>()
    for (interconnect in interconnects) {
        val containingAonA = interconnects.filter { it != interconnect }.filter {
            it.a == interconnect.a
        }.filter { it.b != interconnect.b }.map { it.b }.toSet()
        val containingAonB = interconnects.filter { it != interconnect }.filter {
            it.b == interconnect.a
        }.filter { it.a != interconnect.b }.map { it.a }.toSet()
        val containingBonB = interconnects.filter { it != interconnect }.filter {
            it.b == interconnect.b
        }.filter { it.a != interconnect.a }.map { it.a }.toSet()
        val containingBonA = interconnects.filter { it != interconnect }.filter {
            it.a == interconnect.b
        }.filter { it.b != interconnect.a }.map { it.b }.toSet()
        val thirdsA = containingAonA + containingAonB
        val thirdsB = containingBonA + containingBonB
        val thirds = thirdsA.intersect(thirdsB)
        computerSets.addAll(thirds.map { setOf(interconnect.a, interconnect.b, it) })
    }
    //println(computerSets)
    return computerSets.count { it.any { it[0] == 't' } }.toLong()
}

private fun part2(data: List<String>): String {
    // TODO: optimize part2, it takes 1/2 minute
    val interconnects = parse(data)
    println(interconnects)
    var computerSets = mutableListOf<MutableSet<String>>()
    for (interconnect in interconnects) {
        for (set in computerSets) {
            val allA = set.filter { it != interconnect.a }.all {
                val newInterconnect = if (interconnect.a < it) {
                    Interconnect(interconnect.a, it)
                } else {
                    Interconnect(it, interconnect.a)
                }
                newInterconnect in interconnects
            }
            val allB = set.filter { it != interconnect.b }.all {
                val newInterconnect = if (interconnect.b < it) {
                    Interconnect(interconnect.b, it)
                } else {
                    Interconnect(it, interconnect.b)
                }
                newInterconnect in interconnects
            }
            if (allA && allB) {
                set.add(interconnect.a)
                set.add(interconnect.b)
            }
        }
        computerSets.add(mutableSetOf(interconnect.a, interconnect.b))
        computerSets = computerSets.distinct().toMutableList()
    }
    val maxSize = computerSets.maxOfOrNull { it.size }!!
    val password = computerSets.first { it.size == maxSize }.toList().sorted().joinToString(",")
    return password
}