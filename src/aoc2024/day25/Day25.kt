package aoc2024.day25

import day

fun main() {
    day(2024, 25) {
        part1(3L, "example", ::part1)
        part1(317L, "input", ::part1)
//        part2("co,de,ka,ta", "example", ::part2)
//        part2("cb,df,fo,ho,kk,nw,ox,pq,rt,sf,tq,wi,xz", "input", ::part2)

    }
}

private fun part1(data: List<String>): Long {
    val locks = mutableListOf<List<Int>>()
    val keys = mutableListOf<List<Int>>()
    for (d in data.chunked(8)) {
        val d = d.subList(0, d.size - 1)
        if (d[0] == "#####") {
            locks.add((0 until 5).map { i ->
                d.count { it[i] == '#' } - 1
            })
        } else if (d[6] == "#####") {
            keys.add((0 until 5).map { i ->
                d.count { it[i] == '#' } - 1
            })
        }
    }
    println(locks)
    println(keys)
    var res = 0L
    for (lock in locks) {
        for (key in keys) {
            if (lock.zip(key).map { it.first + it.second }.none { it >= 6 }) {
                res++
            }
        }
    }
    return res
}