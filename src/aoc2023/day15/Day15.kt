package aoc2023.day15

import day

private fun hash(data: String): Int {
    var i = 0
    for (d in data) {
        i += d.code
        i *= 17
        i %= 256
    }
    return i
}

private data class Lens(val label: String, var focal: Int)

private data class Box(val lenses: MutableList<Lens> = mutableListOf())

private data class Facility(val boxes: List<Box> = (0..255).map { Box() }) {
    fun focussingPower(): Int {
        return boxes.mapIndexed { boxIndex, box ->
            box.lenses.mapIndexed { lensIndex, lens ->
                (boxIndex + 1) * (lensIndex + 1) * lens.focal
            }.sum()
        }.sum()
    }
}

private fun calculate(data: List<String>): Int {
    val facility = Facility()
    for (d in data) {
        if (d.contains('-')) {
            val label = d.substring(0, d.length - 1)
            val hash = hash(label)
            val box = facility.boxes[hash]
            box.lenses.singleOrNull { it.label == label }?.let {
                box.lenses.remove(it)
            }
        } else {
            val s = d.split('=')
            val label = s[0]
            val hash = hash(label)
            val focal = s[1].toInt()
            val box = facility.boxes[hash]
            box.lenses.singleOrNull { it.label == label }?.let {
                it.focal = focal
            } ?: run {
                box.lenses.add(Lens(label, focal))
            }

        }
    }
    return facility.focussingPower()
}

fun main() {
    day(2023, 15) {
        part1(1320, "test") {
            it[0].split(',').sumOf { hash(it) }
        }
        part1(513172, "input") {
            it[0].split(',').sumOf { hash(it) }
        }
        part2(145, "test") {
            calculate(it[0].split(','))
        }
        part2(237806, "input") {
            calculate(it[0].split(','))
        }
    }
}