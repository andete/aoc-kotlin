package aoc2023.day12

import day

private enum class SpringState(val c: Char) {
    OK('.'),
    FAIL('#'),
    UNKNOWN('?'),
    ASSIGN_FAIL('*');

    override fun toString() = "$c"
    val failPossible get() = this == FAIL || this == UNKNOWN
    val okPossible get() = this == OK || this == UNKNOWN

    val ok get() = this == OK
    val fail get() = this == FAIL || this == ASSIGN_FAIL
}

private data class Record(val f: List<SpringState>, val sizes: List<Int>) {
    override fun toString() = "(${f.joinToString("")},$sizes)"
    fun fail(i: Int, size: Int): Record {
        val next = f.getOrNull(i + size)?.let { listOf(SpringState.OK) } ?: emptyList()
        val tail = if (i + size + 1 < f.size) {
            f.subList(i + size + 1, f.size)
        } else {
            emptyList()
        }
        val head = f.subList(0, i).map {
            if (it == SpringState.UNKNOWN) {
                SpringState.OK
            } else {
                it
            }
        }
        val fill = (0 until size).map { SpringState.ASSIGN_FAIL }
        val newF =
            head + fill + next + tail
        return this.copy(f = newF)
    }

    fun takeSize(): Pair<Int, Record> {
        val s = sizes.first()
        return s to this.copy(sizes = sizes.subList(1, sizes.size))
    }

    fun clear(i: Int): Record {
        return this.copy(f = f.subList(0, i) + listOf(SpringState.OK) + f.subList(i + 1, f.size))
    }

    val maybePossible: Boolean
        get() {
            return sizes.all {
                fit(it)
            }
        }

    private fun fit(size: Int): Boolean {
        for (i in 0 until f.size - size + 1) {
            val sub = f.subList(i, i + size)
            if (sub.all { it.failPossible }) {
                return true
            }
        }
        return false
    }

    val valid = sizes.isEmpty() && f.none { it == SpringState.FAIL }
}

private fun parse(data: List<String>): List<Record> {
    return data.map { line ->
        val s1 = line.split(' ')
        val f = s1[0]
        val sizes = s1[1].split(',').map { it.toInt() }
        Record(f.map { c -> SpringState.entries.first { it.c == c } }, sizes)
    }
}

private fun valid(record: Record): Boolean {
    val s = record.f.map { it.c }.joinToString("")
    val sizes = s.split(".").filter { it.isNotEmpty() }.map { it.length }
    return sizes == record.sizes
}

private fun possibilities(record: Record, index: Int): Int {
    if (index == record.f.size) {
        if (valid(record)) {
            return 1
        }
        return 0
    }
    var res = 0
    val f = record.f[index]
    if (f != SpringState.FAIL) {
        val nOk = record.f.mapIndexed { i, springState ->
            if (i == index) {
                SpringState.OK
            } else {
                springState
            }
        }
        res += possibilities(record.copy(f = nOk), index + 1)
    }

    if (f != SpringState.OK) {
        val nFail = record.f.mapIndexed { i, springState ->
            if (i == index) {
                SpringState.FAIL
            } else {
                springState
            }
        }
        res += possibilities(record.copy(f = nFail), index + 1)
    }
    return res
}


private fun possibilities2(record: Record): Int {
    if (record.sizes.isEmpty()) {
        return if (record.valid) {
            1
        } else {
            0
        }
    }
    val (size, record2) = record.takeSize()
    val fittings: List<Record> = fittings(record2, size)
    return fittings.sumOf { possibilities2(it) }
}

private fun subPartsForRecord(record: Record): List<Record> {
    val reduced = mutableListOf<Record>()
    val current = mutableListOf<SpringState>()
    for (f in record.f) {
        if (f == SpringState.OK) {
            if (current.isNotEmpty()) {
                reduced.add(Record(current.toList(), emptyList()))
            }
            current.clear()
        } else {
            current.add(f)
        }
    }
    if (current.isNotEmpty()) {
        reduced.add(Record(current.toList(), emptyList()))
    }
    return reduced
}

//private fun possibleAmountsOfSizesForSubPart(sub: Record, sizes: List<Int>): Map<Int, Int> {
//    val res = mutableMapOf<Int, Int>()
//    for (i in sizes.indices) {
//        val s = sizes.subList(0, i + 1)
//        val j = possibleToFitForSubRecord(sub, s)
//        if (j != null) {
//            res[i + 1] = j
//        }
//    }
//    return res
//}

private fun possibilitiesToFitForSubRecord(sub: Record, size: Int): List<Record> {
    val result = mutableListOf<Record>()
    for (i in 0..sub.f.size - size) {
        val okAtEntries = sub.f.subList(i, i + size).all { it.failPossible }
        val beforeOk = sub.f.getOrNull(i - 1)?.let { it.okPossible } ?: true
        val afterOk = sub.f.getOrNull(i + size)?.let { it.okPossible } ?: true
        if (okAtEntries && beforeOk && afterOk) {
            val newF = sub.f.mapIndexed { index, springState ->
                if (index in i until i + size) {
                    if (springState == SpringState.UNKNOWN) {
                        SpringState.ASSIGN_FAIL
                    } else {
                        springState
                    }
                } else if (index == i - 1 || index == i + size) {
                    SpringState.OK
                } else {
                    springState
                }
            }
            result.add(Record(newF, emptyList()))
        }
    }
    return result
}

private fun possibleToFitForSubRecord(sub: Record, sizes: List<Int>): List<Record>? {
    var res: MutableList<Record>? = null
    if (sizes.isEmpty()) {
        return emptyList()
    }
    val size = sizes.first()
    val rem = sizes.subList(1, sizes.size)
    for (fit in possibilitiesToFitForSubRecord(sub, size)) {
        val j = possibleToFitForSubRecord(fit, rem)
        if (j != null) {
            for (k in j) {
                val existing = res?.contains(k) ?: false
                if (!existing) {
                    res = res ?: mutableListOf()
                    res.add(k)
                }
            }
        }
    }
    return res
}

//private fun handleSubParts(subParts: List<Record>, sizes: List<Int>): Int {
//    if (subParts.isEmpty() && sizes.isEmpty()) {
//        return 1
//    }
//    if (sizes.isEmpty() || subParts.isEmpty()) {
//        return 0
//    }
//    var res = 0
//    val firstSubPart = subParts.first()
//    val remainingSubParts = subParts.subList(1, subParts.size)
//    val s = possibleAmountsOfSizesForSubPart(firstSubPart, sizes)
//    for (x in s) {
//        val remainingSizes = sizes.takeLast(sizes.size - x.key)
//        val z = handleSubParts(remainingSubParts, remainingSizes)
//        res += x.value * z
//
//    }
//    return res
//}

//private fun possibilities3(record: Record): Int {
//    return handleSubParts(subPartsForRecord(record), record.sizes)
//}

private fun fittings(record: Record, size: Int): List<Record> {
    val res = mutableListOf<Record>()
    var i = 0
    var record = record.copy()
    while (i < record.f.size - size + 1) {
        val x = record.f[i]
        // if we spot a fail, we _have_ to use it
        if (x == SpringState.FAIL) {
            val sub = record.f.subList(i, i + size)
            val next = record.f.getOrNull(i + size)
            val nextOk = next == null || next.okPossible
            if (sub.all { it.failPossible } && nextOk) {
                val newRecord: Record = record.fail(i, size)
                if (newRecord.maybePossible) {
                    res.add(newRecord)
                }
            }
            return res
        } else if (x == SpringState.UNKNOWN) {
            val sub = record.f.subList(i, i + size)
            val next = record.f.getOrNull(i + size)
            val nextOk = next == null || next.okPossible
            if (sub.all { it.failPossible } && nextOk) {
                val newRecord: Record = record.fail(i, size)
                if (newRecord.maybePossible) {
                    res.add(newRecord)
                }
            }
            record = record.clear(i)
            i++
        } else {
            i++
        }
    }
    return res
}

private fun explode(data: List<Record>): List<Record> {
    return data.map {
        explodeOne(it)
    }
}

private fun explodeOne(it: Record) = Record(
    f = it.f + listOf(SpringState.UNKNOWN)
            + it.f + listOf(SpringState.UNKNOWN)
            + it.f + listOf(SpringState.UNKNOWN)
            + it.f + listOf(SpringState.UNKNOWN) + it.f,
    sizes = it.sizes + it.sizes + it.sizes + it.sizes + it.sizes
)

private fun part1(it: List<String>): Long {
    return parse(it).sumOf { possibilities(it, 0).toLong() }
}

private fun part1alt(it: List<String>): Long {
    return parse(it).sumOf {
        possibilities2(it).toLong()
    }
}

//private fun part1alt2(it: List<String>): Long {
//    return parse(it).sumOf {
//        val res = possibilities3(it).toLong()
//        println("$it -> $res")
//        res
//    }
//}

fun main() {
    day(2023, 12) {
        part1(21, "test", ::part1)
        part1(21, "test", ::part1alt)
//        part1(21, "test", ::part1alt2)
        part1(8075, "input", ::part1alt)

        // TODO part2
    }
}