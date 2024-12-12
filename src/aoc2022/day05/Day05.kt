package aoc2022.day05

import day

fun main() {
    day(2022, 5) {
        part1("CMZ", "example", ::part1)
        part1(424, "input", ::part1)
        part2(4, "example", ::part2)
        part2(2342, "input", ::part2)

    }
}

private data class Stack(val crates: MutableList<Char> = mutableListOf())
private data class Move(val amount: Int, val fromStack: Int, val toStack: Int)

private fun parse(data: List<String>): Pair<List<Stack>, List<Move>> {
    val stacks = mutableMapOf<Int, Stack>()
    var i = 0
    while (data[i] != "") {
        val stackLine = data[i]
        var j = 1
        var k = 0
        var c: Char? = null
        while(true) {
            val c = stackLine.getOrNull(j) ?: break
            if (c != ' ') {
                val stack = stacks.get(k) ?: Stack()
                stack.crates.add(c)
                stacks[k] = stack
            }
            k++
            j += 4
        }
        i++
    }
    i++
    val moves = data.subList(i, data.size).map {
        val moveLine = it.split(' ')
        val amount = moveLine[1].toInt()
        val fromStack = moveLine[3].toInt() - 1
        val toStack = moveLine[5].toInt() - 1
        Move(amount, fromStack, toStack)
    }
    val stacksList = stacks.keys.sorted().map { stacks[it]!! }
    return stacksList to moves
}

private fun executeMoves(stacks: List<Stack>, moves: List<Move>) {
    for (move in moves) {
    }
}

private fun part1(data: List<String>): String {
    val (stacks, moves) = parse(data)
    println(stacks)
    println(moves)
    return ""

}

private fun part2(data: List<String>): String {
    return ""
}