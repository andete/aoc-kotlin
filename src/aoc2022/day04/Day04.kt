package aoc2022.day04

import day

fun main() {
    day(2022, 4) {
        part1(2, "example", ::part1)
        part1(424, "input", ::part1)
        part2(4, "example", ::part2)
        part2(2342, "input", ::part2)

    }
}



private fun part1(data: List<String>): Int {
    return data.count {
        val sections = it.split(',').map { it.split('-').map { it.toLong() } }
        val section1 = sections[0][0]..sections[0][1]
        val section2 = sections[1][0]..sections[1][1]
        val section1ContainsSection2 = section1.contains(section2.start) && section1.contains(section2.endInclusive)
        val section2ContainsSection1 = section2.contains(section1.start) && section2.contains(section1.endInclusive)
        section2ContainsSection1 || section1ContainsSection2
    }
}

private fun part2(data: List<String>): Int {
    return data.count {
        val sections = it.split(',').map { it.split('-').map { it.toLong() } }
        val section1 = sections[0][0]..sections[0][1]
        val section2 = sections[1][0]..sections[1][1]
        val section1ContainsSection2 = section1.contains(section2.start) || section1.contains(section2.endInclusive)
        val section2ContainsSection1 = section2.contains(section1.start) || section2.contains(section1.endInclusive)
        section2ContainsSection1 || section1ContainsSection2
    }
}
