package be.damad.aoc2023.aoc02

import kotlin.math.max

private var gamesSample = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".split('\n')

private data class Draw(val red: Int, val green: Int, val blue: Int)
private data class Game(val id: Int, val draws: List<Draw>)

private fun parse(games: List<String>): List<Game> {
    return games.map {
        val subs1 = it.split(':')
        val id = subs1[0].split(' ')[1].toInt()
        val drawsText = subs1[1].split(';').map { it.strip() }
        val draws = drawsText.map {
            var red: Int = 0;
            var green: Int = 0;
            var blue: Int = 0
            val colors = it.split(',').map { it.strip() }
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

private fun calculatePart1(gamesData: List<String> = gamesSample)= parse(gamesData).filter { possible(it) }.sumOf { it.id }

private fun Game.minimalSet(): Draw {
    val red = draws.fold(0) { a, it -> max(a, it.red )}
    val green = draws.fold(0) { a, it -> max(a, it.green )}
    val blue = draws.fold(0) { a, it -> max(a, it.blue )}
    return Draw(red = red, green = green, blue = blue)
}

private val Draw.power get() = red * green * blue

private fun calculatePart2(gamesData: List<String> = gamesSample) = parse(gamesData).sumOf { it.minimalSet().power }

fun main() {
    println(calculatePart1(data))
    println(calculatePart2(data))
}