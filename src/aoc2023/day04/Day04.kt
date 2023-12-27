package aoc2023.day04

import day
import readInput
import runPart
import kotlin.math.pow

private fun part1(data: List<String>): Int {
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

private fun part2(data: List<String>): Int {
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
    day(2023, 4) {
        part1(13, "example", ::part1)
        part1(21158, "input", ::part1)
        part2(30, "example", ::part2)
        part2(6050769, "input", ::part2)
    }
}