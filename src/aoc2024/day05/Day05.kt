package aoc2024.day05

import day

fun main() {
    day(2024, 5) {
        part1(143, "example", ::part1)
        part1(7074, "input", ::part1)
        part2(123, "example", ::part2)
        part2(4828, "input", ::part2)
    }
}

private data class Before(val a: Long, val b: Long)
private data class Update(val pages: List<Long>)

private data class World(val before: List<Before>, val updates: List<Update>)

private fun parse(data: List<String>): World {
    var inUpdates = false
    val before = mutableListOf<Before>()
    val updates = mutableListOf<Update>()
    for (line in data) {
        val line = line.trim()
        if (line == "") {
            inUpdates = true
        } else if (inUpdates) {
            updates.add(Update(line.split(",").map { it.toLong() }))
        } else {
            val x = line.split("|").map { it.toLong() }
            before.add(Before(x[0], x[1]))
        }
    }
    before.sortBy { before -> before.a }
    return World(before, updates)
}

private fun correct(update: Update, before: List<Before>): Boolean {
    for (i in 0 until update.pages.size - 1) {
        val v = update.pages[i]
        val other = update.pages.subList(i + 1, update.pages.size)
        if (other.any { Before(it, v) in before }) {
            return false
        }
    }
    return true
}

private fun part1(data: List<String>): Long {
    val world = parse(data)
    val correctUpdates = world.updates.filter { correct(it, world.before) }
    //println(correctUpdates)
    return correctUpdates.sumOf { it.pages[it.pages.size/2]}
}



private fun part2(data: List<String>): Long {
    val world = parse(data)
    val incorrectUpdates = world.updates.filter { !correct(it, world.before) }
    val fixedUpdates = incorrectUpdates.map { fixUpdate(it, world.before) }
    return fixedUpdates.sumOf { it.pages[it.pages.size/2]}
}

private fun fixUpdate(update: Update, beforeList: List<Before>): Update {
    val pages = update.pages.toMutableList()
    while (!correct(Update(pages), beforeList)) {
        for (before in beforeList) {
            val ia = pages.indexOf(before.a)
            val ib = pages.indexOf(before.b)
            if (ia != -1 && ib != -1) {
                if (ib < ia) {
                    pages[ia] = before.b
                    pages[ib] = before.a
                    break
                }
            }
        }
    }
    return Update(pages)
}

