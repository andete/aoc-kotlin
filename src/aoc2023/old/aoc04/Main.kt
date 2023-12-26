package be.damad.aoc2023.aoc04

import kotlin.math.pow

private val testData = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""".split("\n")

private fun calculatePart1(data: List<String>): Int {
    var result = 0
    for (line in data) {
        val card = line.split(':')[1].split('|')
        val winning = card[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        val draws = card[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        val winningDraws = draws.intersect(winning)
        val score = if (draws.intersect(winning).isEmpty()) {
            0
        } else {
            2.0.pow(winningDraws.size - 1).toInt()
        }
        result += score
    }
    return result
}

private fun calculatePart2(data: List<String>): Int {
    class Card(val i: Int, val winning: Set<Int>, val draws: Set<Int>, var amount: Int = 1) {
        val score = draws.intersect(winning).size
    }

    val cards = mutableMapOf<Int, Card>()
    for (line in data) {
        val c1 = line.split(':')
        val i = c1[0].split(' ').filter { it.isNotEmpty() }[1].toInt()
        val card = c1[1].split('|')
        val winning = card[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        val draws = card[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        cards[i] = Card(i, winning, draws)
    }
    for (card in cards.values) {
        for (i in 0 until card.score) {
            cards[card.i + 1 + i]?.let { it.amount += card.amount }
        }
    }
    return cards.values.sumOf { it.amount }
}

fun main() {
    check(13 == calculatePart1(testData))
    val res1 = calculatePart1(aoc04data)
    println(res1)
    check(21158 == res1)
    check(30 == calculatePart2(testData))
    val res2 = calculatePart2(aoc04data)
    println(res2)
    check(6050769 == res2)

}