package be.damad.aoc2023.aoc07

import kotlin.streams.toList

private val testData = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483""".split('\n')

private val cards: List<Char> = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()

private enum class Kind {
    HIGH,
    PAIR,
    TWO_PAIR,
    THREE,
    FULL_HOUSE,
    FOUR,
    FIVE,
}

private fun kind(hand: String): Kind {
    val sortedHand = hand.toCharArray().toList().sortedBy { cards.indexOf(it) }
    val js = sortedHand.count { it == 'J' }
    val amounts = sortedHand.map { c ->
        c to sortedHand.count { it == c }
    }.distinctBy { it.first }.toMap()
    val largestAmount = amounts.filterKeys { it != 'J' }.maxOfOrNull { it.value } ?: return Kind.FIVE
    if (js == 5 || largestAmount + js == 5) {
        return Kind.FIVE
    }
    if (js == 4 || largestAmount + js == 4) {
        return Kind.FOUR
    }
    if (amounts.size == 2 || (amounts.size == 3 && js > 0)) {
        return Kind.FULL_HOUSE
    }
    if (js == 3 || largestAmount + js == 3) {
        return Kind.THREE
    }
    if (amounts.size == 3 || amounts.size == 4 && js > 0) {
        return Kind.TWO_PAIR
    }
    if (largestAmount + js == 2) {
        return Kind.PAIR
    }
    return Kind.HIGH
}

private class HandComparator : Comparator<String> {
    override fun compare(o1: String, o2: String): Int {
        val k1 = kind(o1)
        val k2 = kind(o2)
        if (k1 != k2) {
            return k1.ordinal.compareTo(k2.ordinal)
        }
        for (i in o1.indices) {
            if (o1[i] != o2[i]) {
                return cards.indexOf(o1[i]).compareTo(cards.indexOf(o2[i]))
            }
        }
        return 0
    }

}

private fun calculate(data: List<String>): Long {
    val input = data.map {
        val x = it.split(' ')
        x[0] to x[1].toLong()
    }.toMap()
    val sortedHands = input.keys.sortedWith(HandComparator())
    println(sortedHands)
    var i = 1
    var res = 0L
    for (hand in sortedHands) {
        println("$i $hand ${kind(hand)} ${input[hand]}")
        res += input[hand]!! * i
        i++
    }
    return res

}

fun main() {
    println(testData)
    val testResult = calculate(testData)
    println(testResult)
    check(5905L == testResult)
    val res = calculate(aoc07data)
    println(res)
    check(252898370L == res)
}