package aoc2024.day09

import day

fun main() {
    day(2024, 9) {
        part1(1928, "example", ::part1)
        part1(6216544403458, "input", ::part1)
        part2(2858, "example", ::part2)
        part2(6237075041489, "input", ::part2)
    }
}

private fun convert(data: String): List<Long?> {
    val s = mutableListOf<Long?>()
    var c = 0L
    for (i in data.indices) {
        if (i % 2 == 0) {
            val size = data[i].toString().toInt()
            repeat(size) {
                s.add(c)
            }
            c++
        } else {
            val skip = data[i].toString().toInt()
            repeat(skip) {
                s.add(null)
            }
        }
    }
    return s
}

private fun compact(memory: List<Long?>): List<Long?> {
    val memory = memory.toMutableList()
    var emptyPointer = memory.indexOf(null)
    var notEmptyPointer = memory.indexOfLast { ch -> ch != null }
    while (emptyPointer < notEmptyPointer) {
        memory[emptyPointer] = memory[notEmptyPointer]
        memory[notEmptyPointer] = null
        // not efficient
        emptyPointer = memory.indexOf(null)
        notEmptyPointer = memory.indexOfLast { ch -> ch != null }
    }
    return memory
}

private fun checksum(memory: List<Long?>): Long {
    return memory.mapIndexed { index, ch -> ch?.let { it * index } ?: 0 }.sum()
}

private fun part1(data: List<String>): Long {
    val data = data[0]
    val memory = convert(data)
    //println(memory)
    val compacted = compact(memory)
    //println(compacted)
    return checksum(compacted)
}

private fun compact2(memory: List<Long?>): List<Long?> {
    val fileIds = memory.filterNotNull().distinct().reversed()
    //println(fileIds)
    val memory = memory.toMutableList()
    for (fileId in fileIds) {
        val start = memory.indexOf(fileId)
        val end = memory.lastIndexOf(fileId)
        val size = end - start + 1
        // find first sequence of 'size' nulls (but before start obviously)
        val slotWithSpace = memory.indices.firstOrNull { index ->
            (0 until size).all { (memory.getOrElse(index + it) { i -> -1 }) == null }
        }
        if (slotWithSpace != null && slotWithSpace < start) {
            for (i in (0 until size)) {
                memory[slotWithSpace + i] = memory[start + i]
                memory[start + i] = null
            }
        }
    }
    return memory
}

private fun part2(data: List<String>): Long {
    val data = data[0]
    val memory = convert(data)
    //println(memory)
    val compacted = compact2(memory)
    //println(compacted)
    return checksum(compacted)
}