import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, day: Int, name: String = "input"): List<String> {
    val num = "%02d".format(day)
    return Path("src/aoc$year/day$num/$name.txt").readLines()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> runPart(year: Int, day: Int, expected: T, name: String = "input", part: (List<String>) -> T) {
    val input = readInput(year, day, name)
    val res = part(input)
    println(res)
    check(expected == res)
}

data class AocDay<T>(val year: Int, val day: Int) {

    fun part1(expected: T, name: String, lambda: (bla: List<String>) -> T) {
        part(1, expected, name, lambda)
    }

    fun part1(name: String, expected: T, lambda: () -> T) {
        part(name, 1, expected, lambda)
    }

    fun part2(name: String, expected: T, lambda: () -> T) {
        part(name, 2, expected, lambda)
    }

    fun part2(expected: T, name: String, lambda: (List<String>) -> T) {
        part(2, expected, name, lambda)
    }

    fun p2(expected: T, name: String, lambda: (List<String>) -> T) {
        part(2, expected, name, lambda)
    }

    private fun part(i: Int, expected: T, name: String, lambda: (List<String>) -> T) {
        val n2 = name.split(':')
        val input = readInput(year, day, n2[0])
        val res = lambda(input)
        if (res == expected) {
            println("aoc$year/$day/$i: $name -> $res")
        } else {
            println("aoc$year/$day/$i: $name -> $res != $expected")
        }
        check(expected == res)
    }

    fun <U>test(testName: String, name: String, lambda: (List<String>) -> U) {
        val n2 = name.split(':')
        val input = readInput(year, day, n2[0])
        val res = lambda(input)
        println("aoc$year/$day/$testName: $name -> $res")
    }

    private fun part(name: String, i: Int, expected: T, lambda: () -> T) {
        val res = lambda()
        if (res == expected) {
            println("aoc$year/$day/$i: $name -> $res")
        } else {
            println("aoc$year/$day/$i: $name -> $res != $expected")
        }
        check(expected == res)
    }
}

fun <T> day(year: Int, day: Int, lambda: AocDay<T>.() -> Unit) {
    val aoc = AocDay<T>(year, day)
    lambda(aoc)
}
