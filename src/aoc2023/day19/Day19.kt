package aoc2023.day19

import be.damad.aoc2023.util.longRangeIntersect
import be.damad.aoc2023.util.minus
import day

private enum class Category(val c: Char) {
    X('x'),
    M('m'),
    A('a'),
    S('s'),
}

private enum class CheckOperation(val c: Char) {
    BIGGER('>'),
    SMALLER('<'),
}

private data class Check(val category: Category, val operation: CheckOperation, val value: Long) {
    override fun toString() = "${category.c}${operation.c}$value"

    fun check(part: Part): Boolean {
        val l = part.get(category)
        return when (operation) {
            CheckOperation.BIGGER -> l > value
            CheckOperation.SMALLER -> l < value
        }
    }

    private val range: LongRange = when (operation) {
        CheckOperation.BIGGER -> (value + 1)..4000
        CheckOperation.SMALLER -> 1L until value
    }

    fun check(other: PartRange): Pair<PartRange?, PartRange?> {
        val l = other.get(category)
        val inside = longRangeIntersect(l, range)?.let {
            other.copyWith(category, it)
        }
        val outside = minus(l, range).singleOrNull()?.let {
            other.copyWith(category, it)
        }
        return inside to outside
    }
}

private enum class Action {
    NEXT,
    ACCEPT,
    REJECT,
}

private data class Rule(val check: Check?, val action: Action, val next: String?)
private data class Workflow(val name: String, val rules: List<Rule>)
private data class Part(val x: Long, val m: Long, val a: Long, val s: Long) {
    fun get(c: Category): Long {
        return when (c) {
            Category.X -> x
            Category.M -> m
            Category.A -> a
            Category.S -> s
        }
    }

    val rating = x + m + a + s
}

private data class PartRange(val x: LongRange, val m: LongRange, val a: LongRange, val s: LongRange) {
    val xSize = 1 + x.last - x.first
    val mSize = 1 + m.last - m.first
    val aSize = 1 + a.last - a.first
    val sSize = 1 + s.last - s.first
    val rating get() = xSize * mSize * aSize * sSize

    fun get(c: Category): LongRange {
        return when (c) {
            Category.X -> x
            Category.M -> m
            Category.A -> a
            Category.S -> s
        }
    }

    fun copyWith(c: Category, v: LongRange): PartRange {
        return when(c) {
            Category.X -> copy(x = v)
            Category.M -> copy(m = v)
            Category.A -> copy(a = v)
            Category.S -> copy(s = v)
        }
    }
}

private fun parse(data: List<String>): Pair<List<Workflow>, List<Part>> {
    val lineIterator = data.iterator()
    var line = lineIterator.next()
    val workflows = mutableListOf<Workflow>()
    while (line.isNotEmpty()) {
        workflows.add(parseWorkflow(line))
        line = lineIterator.next()
    }
    val parts = mutableListOf<Part>()
    while (lineIterator.hasNext()) {
        line = lineIterator.next()
        parts.add(parsePart(line))
    }
    return workflows to parts
}

private fun parsePart(line: String): Part {
    // {x=787,m=2655,a=1222,s=2876}
    val x1 = line.split(",", "{", "}", "=").filter { it.isNotEmpty() }
    val x = x1[1].toLong()
    val m = x1[3].toLong()
    val a = x1[5].toLong()
    val s = x1[7].toLong()
    return Part(x, m, a, s)
}

private fun parseWorkflow(line: String): Workflow {
    // px{a<2006:qkq,m>2090:A,rfg}
    val x1 = line.split("{", "}", ",").filter { it.isNotEmpty() }
    val name = x1[0]
    val rules: List<Rule> = x1.subList(1, x1.size).map {
        parseRule(it)
    }
    return Workflow(name, rules)
}

private fun parseRule(data: String): Rule {
    val operation = CheckOperation.entries.firstOrNull { it.c in data }
    if (operation != null) {
        val x1 = data.split('<', '>', ':')
        val category = Category.entries.single { it.c == x1[0][0] }
        val value = x1[1].toLong()
        val check = Check(category, operation, value)
        val (action, next) = actionAndNext(x1[2])
        return Rule(check, action, next)
    }
    val (action, next) = actionAndNext(data)
    return Rule(null, action, next)
}

private fun actionAndNext(x1: String) = if (x1 == "R") {
    Action.REJECT to null
} else if (x1 == "A") {
    Action.ACCEPT to null
} else {
    Action.NEXT to x1
}

private fun accepted(part: Part, workflows: List<Workflow>): Boolean {
    var workflow = workflows.single { it.name == "in" }
    while (true) {
        val (a: Action, next: String?) = process(part, workflow)
        if (a == Action.ACCEPT) {
            return true
        } else if (a == Action.REJECT) {
            return false
        } else {
            workflow = workflows.single { it.name == next }
        }
    }
}

private fun process(part: Part, workflow: Workflow): Pair<Action, String?> {
    for (rule in workflow.rules) {
        if (rule.check?.check(part) != false) {
            return rule.action to rule.next
        }
    }
    error("workflow faulty: $workflow")
}

private fun applyWorkflows(workflows: List<Workflow>): List<PartRange> {
    val initialRange = PartRange(1L..4000, 1L..4000, 1L..4000, 1L..4000)
    val ranges = listOf(initialRange)
    val workflow = workflows.single { it.name == "in" }
    val result = mutableListOf<PartRange>()
    applyWorkflow(result, workflows, ranges, workflow)
    return result
}

private fun applyWorkflow(result: MutableList<PartRange>, workflows: List<Workflow>, ranges: List<PartRange>, workflow: Workflow) {
    for (range in ranges) {
        applyWorkflow(result, workflows, range, workflow)
    }
}

private fun applyWorkflow(result: MutableList<PartRange>, workflows: List<Workflow>, range: PartRange, workflow: Workflow) {
    var range = range
    for (rule in workflow.rules) {
        rule.check?.check(range)?.let { (inside, outside) ->
            inside?.let {
                when(rule.action) {
                    Action.NEXT -> applyWorkflow(result, workflows, it, workflows.single { it.name == rule.next})
                    Action.ACCEPT -> result.add(it)
                    Action.REJECT -> {}
                }
            }
            if (outside != null) {
                // continue to next rule with outside
                range = outside
            }
        }
        if (rule.check == null) {
            when(rule.action) {
                Action.NEXT -> applyWorkflow(result, workflows, range, workflows.single { it.name == rule.next})
                Action.ACCEPT -> {
                    result.add(range)
                    return
                }
                Action.REJECT -> {
                    return
                }
            }
        }
    }
}

private fun part1(data: List<String>): Long {
    val (workflows, parts) = parse(data)
    val accepted = parts.filter { accepted(it, workflows) }
    return accepted.sumOf { it.rating }
}

private fun part2(data: List<String>): Long {
    val (workflows, parts) = parse(data)
    val ranges = applyWorkflows(workflows)
    return ranges.sumOf { it.rating }
}

fun main() {
    day(2023, 19) {
        part1(19114L, "example", ::part1)
        part1(432434L, "input", ::part1)
        part2(167409079868000L, "example", ::part2)
        part2(132557544578569L, "input", ::part2)
    }
}