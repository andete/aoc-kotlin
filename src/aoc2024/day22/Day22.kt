package aoc2024.day22

import day

fun main() {
    day(2024, 22) {
        part1(37327623, "example", ::part1)
        part1(13234715490, "input", ::part1)
        part2(6, "example0") { part2(10, it) }
        part2(23, "example") { part2(2000, it) }
        part2(619970556776002, "input") { part2(2000, it) }
    }
}

private fun evolve(secret: Long): Long {
    // step 1
    val s1 = secret * 64
    val s2 = s1 xor secret
    val s3 = s2 % 16777216
    // step 2
    val s4 = s3 / 32
    val s5 = s4 xor s3
    val s6 = s5 % 16777216
    // step 3
    val s7 = s6 * 2048
    val s8 = s7 xor s6
    val s9 = s8 % 16777216
    return s9
}

private fun part1(data: List<String>): Long {
    val secrets = data.map { it.toLong() }
    return secrets.sumOf {
        var secret = it
        repeat(2000) {
            secret = evolve(secret)
        }
        secret
    }
}

private data class BuyerPrice(val secret: Long, val change: Long, val amount: Long = secret % 10)

private data class Buyer(val prices: List<BuyerPrice>)

private fun part2(amount: Int, data: List<String>): Long {
    val startSecrets = data.map { it.toLong() }
    val buyers =  startSecrets.map {
        var secret = it
        val buyerPrices = mutableListOf<BuyerPrice>()
        var previous = BuyerPrice(secret, 0)
        (0 until amount).map {
            val newSecret = evolve(secret)
            val newPrice = BuyerPrice(newSecret, (newSecret % 10) - previous.amount)
            buyerPrices.add(newPrice)
            previous = newPrice
            secret = newSecret
        }
        Buyer(buyerPrices)
    }
    var sequences = mutableSetOf<List<Long>>()
    for (buyer in buyers) {
        for (i in (0 until (buyer.prices.size - 3))) {
            sequences.add(listOf(buyer.prices[i].change, buyer.prices[i+1].change, buyer.prices[i+2].change, buyer.prices[i+3].change))
        }
    }
    //println(sequences)
    //return -1
    var maxBananas = 0L
    var maxSequence = listOf<Long>()
    var amountsForEach = listOf<Long>()
    for (sequence in sequences) {
        val bl = buyers.map { bananas(it, sequence) }
        val b = bl.sum()
        if (b > maxBananas) {
            maxBananas = b
            maxSequence = sequence
            amountsForEach = bl
            println("$maxBananas $maxSequence $bl")
        }
    }
    println(maxSequence)
    return maxBananas
}

private fun bananas(buyer: Buyer, changes: List<Long>): Long {
    for (i in (0 until (buyer.prices.size - 3))) {
        val x = buyer.prices.subList(i, i + 4)
        check(x.size == 4)
        if (x.map { it.change } == changes) {
            return x.last().amount
        }
    }
    return 0
}
