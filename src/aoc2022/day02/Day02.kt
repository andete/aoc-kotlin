package aoc2022.day02

import day

fun main() {
    day(2022, 2) {
        part1(15, "example", ::part1)
        part1(11449, "input", ::part1)
        part2(12, "example", ::part2)
        part2(13187, "input", ::part2)

    }
}

enum class RPS(val score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3),
}

private fun score(you: RPS, opponent: RPS): Int {
    val playScore = when (you to opponent) {
        RPS.Rock to RPS.Scissors -> 6
        RPS.Scissors to RPS.Paper -> 6
        RPS.Paper to RPS.Rock -> 6
        RPS.Scissors to RPS.Rock -> 0
        RPS.Paper to RPS.Scissors -> 0
        RPS.Rock to RPS.Paper -> 0
        else -> 3
    }
    val itemScore = you.score
    return playScore + itemScore
}

private fun part1(data: List<String>): Long {
    return data.sumOf {
        val opponent = when (it[0]) {
            'A' -> RPS.Rock
            'B' -> RPS.Paper
            'C' -> RPS.Scissors
            else -> error("unknown type ${it[0]}")
        }
        val you = when (it[2]) {
            'X' -> RPS.Rock
            'Y' -> RPS.Paper
            'Z' -> RPS.Scissors
            else -> error("unknown type ${it[2]}")
        }
        score(you, opponent)
    }.toLong()
}

private fun part2(data: List<String>): Long {
    return data.sumOf {
        val opponent = when (it[0]) {
            'A' -> RPS.Rock
            'B' -> RPS.Paper
            'C' -> RPS.Scissors
            else -> error("unknown type ${it[0]}")
        }
        val you = when (it[2]) {
            'X' -> when (opponent) { // loopse
                RPS.Rock -> RPS.Scissors
                RPS.Paper -> RPS.Rock
                RPS.Scissors -> RPS.Paper
            }
            'Y' -> opponent
            'Z' -> when (opponent) {
                RPS.Rock -> RPS.Paper
                RPS.Paper -> RPS.Scissors
                RPS.Scissors -> RPS.Rock
            }
            else -> error("unknown type ${it[2]}")
        }
        score(you, opponent)
    }.toLong()
}