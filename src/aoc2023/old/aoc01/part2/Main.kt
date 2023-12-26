package be.damad.aoc2023.aoc01.part2


private val digitMap = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

private fun digits(data: String): List<Int> {
    val res = mutableListOf<Int>()
    for (i in data.indices) {
        val sub = data.substring(i)
        if (sub[0].isDigit()) {
            res.add(sub[0].digitToInt())
        } else {
            for (d in digitMap.keys) {
                if (sub.startsWith(d)) {
                    res.add(digitMap[d]!!)
                }
            }
        }
    }
    return res
}

private fun aoc02sample() {
    val data = """two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen""".split('\n')
    val res = calculate(data)
    println(res)
    check(281 == res)
}

private fun calculate(data: List<String>) = data.sumOf {
    val e = digits(it)
    e.first() * 10 + e.last()
}

private fun aoc02() {
    val res = calculate(data)
    println(res)
    check(55652 == res)
}

fun main() {
    aoc02sample()
    aoc02()
}