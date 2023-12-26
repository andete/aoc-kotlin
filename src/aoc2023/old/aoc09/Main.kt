package be.damad.aoc2023.aoc09

private val testData = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45""".split('\n')

private fun reduce(input: List<Long>): List<Long> {
    val result = mutableListOf<Long>()
    for (index in 0 until input.size - 1) {
        result.add(input[index + 1] - input[index])
    }
    return result
}

private fun analyse(input: List<Long>): Long {
    val ranges = calculateRanges(input)
    val rev = ranges.reversed()
    rev[0].add(0)
    for (i in 1 until rev.size) {
        rev[i].add(rev[i-1].last() + rev[i].last())
    }
    return rev.last().last()
}

private fun calculateRanges(input: List<Long>): MutableList<MutableList<Long>> {
    val ranges = mutableListOf(input.toMutableList())
    var calc = input.toList()
    while (true) {
        calc = reduce(calc)
        ranges.add(calc.toMutableList())
        if (calc.all { it == 0L }) {
            break
        }
    }
    return ranges
}

private fun analyse2(input: List<Long>): Long {
    val ranges = calculateRanges(input)
    val rev = ranges.reversed().map { it.reversed().toMutableList() }
    rev[0].add(0)
    for (i in 1 until rev.size) {
        rev[i].add(rev[i].last() - rev[i-1].last())
    }
    return rev.last().last()
}

private fun calculate(data: List<String>, analyse: (List<Long>) -> Long): Long {
    return data.sumOf { line ->
        val one = line.split(' ').map { it.toLong() }
        analyse(one)
    }
}

fun main() {
    val res = calculate(testData, ::analyse)
    println(res)
    check(114L == res)

    val res1 = calculate(aoc09data, ::analyse)
    println(res1)
    check(1853145119L == res1)

    val res2 = calculate(testData, ::analyse2)
    println(res2)
    check(2L == res2)

    val res3 = calculate(aoc09data, ::analyse2)
    println(res3)
    check(923L == res3)
}