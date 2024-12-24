package aoc2024.day24


import day

fun main() {
    day(2024, 24) {
        part1(4L, "example", ::part1)
        part1(2024L, "example2", ::part1)
        part1(53325321422566, "input", ::part1)
//        part2("co,de,ka,ta", "example", ::part2)
//        part2("cb,df,fo,ho,kk,nw,ox,pq,rt,sf,tq,wi,xz", "input", ::part2)

    }
}

private enum class Operation(val f: (Long, Long) -> Long) {
    AND({ i1, i2 -> i1 and i2 }),
    OR({ i1, i2 -> i1 or i2 }),
    XOR({ i1, i2 -> i1 xor i2 }),
}

private data class Gate(val i1: String, val i2: String, val op: Operation, val o: String)

private fun part1(data: List<String>): Long {
    val split = data.indexOf("")
    val wireValues = mutableMapOf<String, Long>()
    data.subList(0, split).forEach { wireStart ->
        val x = wireStart.split(": ")
        val wireName = x[0]
        val wireInitialValue = x[1].toLong()
        wireValues[wireName] = wireInitialValue
    }
    val gates = data.subList(split + 1, data.size).map { gateString ->
        val x = gateString.split(" ")
        val i1 = x[0]
        val op = x[1].let { Operation.valueOf(it) }
        val i2 = x[2]
        val o = x[4]
        Gate(i1, i2, op, o)
    }
    val zWires = gates.filter { it.o[0] == 'z' }.map { it.o }
    var changed = true
    while (changed) {
        changed = false
        for (gate in gates) {
            val i1 = wireValues[gate.i1]
            val i2 = wireValues[gate.i2]
            if (i1 != null && i2 != null) {
                val oldO = wireValues[gate.o]
                val newO = gate.op.f(i1, i2)
                if (newO != oldO) {
                    wireValues[gate.o] = newO
                    changed = true
                }
            }
        }
    }
    println(wireValues)
    println(gates)
    val zValues = wireValues.filter { it.key[0] == 'z' }.map {
        it.key to it.value
    }.sortedBy { it.first }
    println(zValues)
    var res = 0L
    var q = 1L
    zValues.forEach { (s,i) ->
        res += q*i
        q *= 2
    }
    return res
}