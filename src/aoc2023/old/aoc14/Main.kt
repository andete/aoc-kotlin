package be.damad.aoc2023.aoc14

private val testData = """O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....""".split('\n')

enum class RockType(val c: Char) {
    EMPTY('.'),
    ROUND('O'),
    SQUARE('#'),
}

private data class Cell(val x: Int, val y: Int, var t: RockType) {
    val round get() = t == RockType.ROUND
    val empty get() = t == RockType.EMPTY
    val square get() = t == RockType.SQUARE

}

private data class Platform(val cells: List<List<Cell>>) {
    fun at(x: Int, y: Int) = cells.getOrNull(y)?.getOrNull(x)

    fun print() {
        for (cell in cells) {
            val line = cell.map { it.t.c }.joinToString("")
            println(line)
        }
        println()
    }

    val load
        get() = cells.mapIndexed { index, cells ->
            cells.count { it.round } * (cells.size - index)
        }.sum()
}

private fun tiltNorth(platform: Platform) {
    for (repeat in platform.cells.indices) {
        for (y in platform.cells.indices.reversed()) {
            val row = platform.cells[y]
            for (x in row.indices) {
                if (row[x].round && platform.at(x, y - 1)?.empty == true) {
                    row[x].t = RockType.EMPTY
                    platform.at(x, y - 1)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun tiltSouth(platform: Platform) {
    for (repeat in platform.cells.indices) {
        for (y in platform.cells.indices) {
            val row = platform.cells[y]
            for (x in row.indices) {
                if (row[x].round && platform.at(x, y + 1)?.empty == true) {
                    row[x].t = RockType.EMPTY
                    platform.at(x, y + 1)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun tiltEast(platform: Platform) {
    for (repeat in platform.cells[0].indices) {
        for (x in platform.cells[0].indices) {
            for (y in platform.cells.indices) {
                if (platform.at(x, y)!!.round && platform.at(x + 1, y)?.empty == true) {
                    platform.at(x, y)!!.t = RockType.EMPTY
                    platform.at(x + 1, y)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun tiltWest(platform: Platform) {
    for (repeat in platform.cells[0].indices) {
        for (x in platform.cells[0].indices.reversed()) {
            for (y in platform.cells.indices) {
                if (platform.at(x, y)!!.round && platform.at(x - 1, y)?.empty == true) {
                    platform.at(x, y)!!.t = RockType.EMPTY
                    platform.at(x - 1, y)!!.t = RockType.ROUND
                }
            }
        }
    }
}

private fun parse(data: List<String>): Platform {
    val cells = data.mapIndexed { yIndex, s ->
        s.mapIndexed { xIndex, c ->
            Cell(xIndex, yIndex, RockType.entries.single { it.c == c })
        }
    }
    return Platform(cells)
}

private fun repeatingSequence(ps: List<Platform>): Int? {
    var i = 1
    val j = ps.indices.last
    while (j  - i * 2 >= 0) {
        val x1 = ps.subList(j - i * 2, j - i)
        val x2 = ps.subList(j - i, j)
        if (x1 == x2) {
            return i
        }
        i++
    }
    return null
}

private fun manyTilts(p: Platform, amount: Int = 1000000000): Platform? {
    val seen = mutableListOf<Platform>()
    repeat(amount) {
        val p2 = p.copy(cells = p.cells.map { it.map { it.copy() } })
        seen.add(p2)
        repeatingSequence(seen)?.let { interval ->
            return seen[it - interval + (amount - it) % interval]
        }
        tiltNorth(p)
        tiltWest(p)
        tiltSouth(p)
        tiltEast(p)
    }
    return p
}

fun main() {
    run {
        val p = parse(testData)
        tiltNorth(p)
        val l = p.load
        println(l)
        check(136 == l)
    }

    run {
        val p = parse(aoc14data)
        tiltNorth(p)
        val l = p.load
        println(l)
        check(105003 == l)
    }

    run {
        val p = parse(testData)
        val res = manyTilts(p)!!
        val l = res.load
        println(l)
        check(64 == l)
    }

    run {
        val p = parse(aoc14data)
        val res = manyTilts(p)!!
        val l = res.load
        println(l)
        check(93742 == l)
    }
}