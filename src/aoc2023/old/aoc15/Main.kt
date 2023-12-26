package be.damad.aoc2023.aoc15

private val testData = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(',')

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
    run {
        val hs = testData.map { hash(it) }
        println(hs)
        check(1320 == hs.sum())
    }
    run {
        val res = aoc15data.sumOf { hash(it) }
        println(res)
        check(513172 == res)
    }

    run {
        val res = calculate(testData)
        println(res)
        check(res == 145)
    }

    run {
        val res = calculate(aoc15data)
        println(res)
        check(res == 237806)
    }
}