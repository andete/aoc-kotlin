package aoc2023.day02

import day
import readInput
import kotlin.math.max


fun main() {

    day(2023, 2) {
        part1(8, "example", ::part1)
        part1(2156, "input", ::part1)
        part2(2286, "example", ::part2)
        part2(66909, "input", ::part2)
    }
}

private data class Draw(val red: Int, val green: Int, val blue: Int)
private data class Game(val id: Int, val draws: List<Draw>)

private fun parse(games: List<String>): List<Game> {
    return games.map {
        val subs1 = it.split(':')
        val id = subs1[0].split(' ')[1].toInt()
        val drawsText = subs1[1].split(';').map { it.trim() }
        val draws = drawsText.map {
            var red: Int = 0;
            var green: Int = 0;
            var blue: Int = 0
            val colors = it.split(',').map { it.trim() }
            for (color in colors) {
                val c = color.split(' ')
                val amount = c[0].toInt()
                when (c[1]) {
                    "red" -> red += amount
                    "green" -> green += amount
                    "blue" -> blue += amount
                }
            }
            Draw(red = red, green = green, blue = blue)
        }
        Game(id, draws)
    }
}

private fun possible(game: Game, red: Int = 12, green: Int = 13, blue: Int = 14): Boolean {
    return game.draws.all {
        it.red <= red && it.blue <= blue && it.green <= green
    }
}

private fun part1(gamesData: List<String>)= parse(gamesData).filter { possible(it) }.sumOf { it.id }

private fun Game.minimalSet(): Draw {
    val red = draws.fold(0) { a, it -> max(a, it.red ) }
    val green = draws.fold(0) { a, it -> max(a, it.green ) }
    val blue = draws.fold(0) { a, it -> max(a, it.blue ) }
    return Draw(red = red, green = green, blue = blue)
}

private val Draw.power get() = red * green * blue

private fun part2(gamesData: List<String>) = parse(gamesData).sumOf { it.minimalSet().power }