package be.damad.aoc2023.aoc12

// import be.damad.aoc2023.aoc12.data.aoc12data

private val testData = """???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1""".split('\n')

private enum class SpringState(val c: Char) {
    OK('.'),
    FAIL('#'),
    UNKNOWN('?'),
    ASSIGN_FAIL('*');

    override fun toString() = "$c"
    val failPossible get() = this == FAIL || this == UNKNOWN
    val okPossible get() = this == OK || this == UNKNOWN
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

    val maybePossible: Boolean get() {
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

private fun possibilities3(record: Record): Int {
    if (record.sizes.isEmpty()) {
        return if (record.valid) {
            1
        } else {
            0
        }
    }
    val splitted = mutableListOf<List<SpringState>>()
    val add = mutableListOf<SpringState>()
    for (c in record.f) {
        if (c == SpringState.OK) {
            if (add.isNotEmpty()) {
                splitted.add(add.toList())
                add.clear()
            }
        } else {
            add.add(c)
        }
    }
    if (add.isNotEmpty()) {
        splitted.add(add.toList())
        add.clear()
    }
    val (size, record2) = record.takeSize()
    val fittings: List<Record> = fittings(record2, size)
    return fittings.sumOf { possibilities2(it) }
}

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

private fun calculate(data: List<Record>): Long {
    var res = 0L
    for (d in data) {
        res += possibilities(d, 0)
    }
    return res
}

private fun calculate3(data: List<Record>): Pair<Long, Long> {
    var res = 0L
    var eRes = 0L
    for (d in data) {
        print("$d ")
        System.out.flush()
        val p = possibilities2(d)
        val p2 = possibilities2(explodeOne(d))
        println("$p $p2")
        res += p
        eRes += p2
    }
    return res to eRes
}

private fun calculate4(data: List<Record>): Pair<Long, Long> {
    var res = 0L
    var eRes = 0L
    for (d in data) {
        print("$d ")
        System.out.flush()
        val p = possibilities3(d)
        val p2 = possibilities3(explodeOne(d))
        println("$p $p2")
        res += p
        eRes += p2
    }
    return res to eRes
}

private fun calculate2(data: List<Record>): Long {
    var res = 0L
    for (d in data) {
        val p = possibilities2(d)
        println("$d $p")
        res += p
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

fun main() {
    run {
        val res = calculate(parse(testData))
        println(res)
        check(21L == res)
    }

    run {
        val res = calculate3(parse(testData))
        println(res)
        check(21L == res.first)
        check(525152L == res.second)
    }

    run {
        val res = calculate4(parse(testData))
        println(res)
        check(21L == res.first)
        check(525152L == res.second)
    }

    run {
        val p = parse(aoc12data)
        val res2 = calculate2(p)
        println(res2)
        check(8075L == res2)
    }

    run {
        val res3 = calculate2(explode(parse(aoc12data)))
        println(res3)
        check(525152L == res3)
    }
}