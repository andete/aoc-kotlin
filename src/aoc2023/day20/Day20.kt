package aoc2023.day20

import day

private enum class Operation(val s: String) {
    FLIP_FLOP("%"),
    CONJUNCTION("&"),
    BROADCASTER("broadcaster"),
    BUTTON("button"),
    OUTPUT("output"),
}

private data class Rule(
    val name: String,
    val operation: Operation,
    val next: List<String>,
    var flipFlopState: Boolean = false,
    val conjunctionInputs: MutableMap<String, Boolean> = mutableMapOf(),
) {
    fun toShortString() = when (operation) {
        Operation.FLIP_FLOP -> "${operation.s}$name"
        Operation.CONJUNCTION -> "${operation.s}$name"
        Operation.BROADCASTER -> name
        Operation.BUTTON -> name
        Operation.OUTPUT -> name
    }

    override fun toString() = when (operation) {
        Operation.FLIP_FLOP -> "${operation.s}$name -> $next"
        Operation.CONJUNCTION -> "$conjunctionInputs${operation.s}$name -> $next"
        Operation.BROADCASTER -> "$name -> $next"
        Operation.BUTTON -> name
        Operation.OUTPUT -> name
    }
}

private fun parse(data: String): Rule {
    val x1 = data.split(" -> ")
    val next = x1[1].split(", ")
    return if (data.startsWith(Operation.FLIP_FLOP.s)) {
        Rule(x1[0].substring(1), Operation.FLIP_FLOP, next)
    } else if (data.startsWith(Operation.CONJUNCTION.s)) {
        Rule(x1[0].substring(1), Operation.CONJUNCTION, next)
    } else if (data.startsWith(Operation.BROADCASTER.s)) {
        Rule("broadcaster", Operation.BROADCASTER, next)
    } else {
        error("unknown rule $data")
    }
}

private fun parse(data: List<String>): Map<String, Rule> {
    val m = data.map { parse(it) }.associateBy { it.name }.toMutableMap()
    val outputs = mutableListOf<String>()
    for (value in m.values) {
        if (value.operation == Operation.CONJUNCTION) {
            for (rule in m.values.filter { value.name in it.next }) {
                value.conjunctionInputs[rule.name] = false
            }
        }
        for (next in value.next) {
            if (!m.containsKey(next) && next !in outputs) {
                outputs.add(next)
            }
        }
    }
    println(outputs)
    for (output in outputs) {
        m[output] = Rule(output, Operation.OUTPUT, listOf())
    }
    return m

}

private data class Pulse(val input: Rule?, val b: Boolean, val output: Rule) {
    override fun toString() = "${input?.toShortString()} $b ${output.name}"
}

private var part2Mode = false

private fun pulses(rules: Map<String, Rule>): List<Pulse> {
    val pulses = mutableListOf<Pulse>()
    val flipFlops = rules.values.filter { it.operation == Operation.FLIP_FLOP }
    val broadcaster = rules["broadcaster"]!!
    val button = Rule("button", Operation.BUTTON, listOf("broadcaster"))
    pulses.add(Pulse(button, false, broadcaster))
    var current = listOf(false to broadcaster)
    var i = 1
    do {
        current = current.flatMap { (signal, rule) ->
            pulse(rules, pulses, rule, signal)
        }
        i++
    } while (current.isNotEmpty())
    current.flatMap { (signal, rule) ->
        pulse(rules, pulses, rule, signal)
    }
    return pulses
}

private class StopException(val pulses: List<Pulse>) : Exception()

private fun pulse(
    rules: Map<String, Rule>,
    pulses: MutableList<Pulse>,
    rule: Rule,
    b: Boolean
): List<Pair<Boolean, Rule>> {
    if (part2Mode) {
        if (rule.name == "rx" && !b) {
            throw StopException(pulses)
        }
    }
    val nextRules = when (rule.operation) {
        Operation.FLIP_FLOP -> {
            if (b) {
                emptyList<String>() to false
            } else {
                rule.flipFlopState = !rule.flipFlopState
                pulses.addAll(rule.next.map { Pulse(rule, rule.flipFlopState, rules[it]!!) })
                rule.next to rule.flipFlopState
            }
        }

        Operation.CONJUNCTION -> {
            val out = !rule.conjunctionInputs.values.all { it }
            pulses.addAll(rule.next.map { Pulse(rule, out, rules[it]!!) })
            rule.next to out
        }

        Operation.BROADCASTER -> {
            pulses.addAll(rule.next.map { Pulse(rule, b, rules[it]!!) })
            rule.next to b
        }

        Operation.BUTTON -> {
            error("not expecting button")
        }

        Operation.OUTPUT -> {
            emptyList<String>() to false
        }
    }

    return nextRules.first.map {
        val nextRule = rules[it]!!
        if (nextRule.operation == Operation.CONJUNCTION) {
            nextRule.conjunctionInputs[rule.name] = nextRules.second
        }
        nextRules.second to nextRule
    }
}

private fun part1(data: List<String>): Long {
    val rules = parse(data)
    var low = 0L
    var high = 0L
    repeat(1000) {
        val p = pulses(rules)
        low += p.count { !it.b }
        high += p.count { it.b }
    }
    return low * high
}

private fun part2(data: List<String>): Long {
    part2Mode = true
    val rules = parse(data)
    var res = 0L
    try {
        while (true) {
            res++
            val p = pulses(rules)
        }
    } catch (e: StopException) {
    }
    return res
}

fun main() {
    day(2023, 20) {
        part1(32000000L, "example1", ::part1)
        part1(11687500L, "example2", ::part1)
        part1(812609846L, "input", ::part1)
        part2(81260984L, "input", ::part2)
    }
}