package be.damad.aoc2023.aoc21

private val testData = """...........
.....###.#.
.###.##..#.
..#.#...#..
....#.#....
.##..S####.
.##..#...#.
.......##..
.##.#.####.
.##..##.##.
...........""".split('\n')

private data class Plot(val x: Int, val y: Int, val rock: Boolean, val start: Boolean) {
    fun toStringForMap() = if (rock) {
        "#"
    } else if (start) {
        "S"
    } else {
        "."
    }
}

private data class Map(val plots: List<List<Plot>>) {
    override fun toString() = plots.joinToString("\n") {
        it.joinToString("") { it.toStringForMap() }
    }

    fun at(x: Int, y: Int) = plots.getOrNull(y)?.getOrNull(x)

    fun atInfinite(x: Int, y: Int): Plot {
        val y2 = ((y % plots.size) + plots.size) % plots.size
        val x2 = ((x % plots[0].size) + plots[0].size) % plots[0].size
        return plots[y2][x2].copy(x = x, y = y)
    }

    fun neighbours(it: Plot): List<Plot> {
        return listOfNotNull(at(it.x - 1, it.y), at(it.x + 1, it.y), at(it.x, it.y + 1), at(it.x, it.y - 1)).filter {
            !it.rock
        }
    }

    fun infiniteNeighbours(it: Plot): List<Plot> {
        return listOf(
            atInfinite(it.x - 1, it.y),
            atInfinite(it.x + 1, it.y),
            atInfinite(it.x, it.y + 1),
            atInfinite(it.x, it.y - 1)
        ).filter {
            !it.rock
        }
    }

    val start = plots.flatten().single { it.start }
}

private fun parse(data: List<String>): Map {
    val plots = data.mapIndexed { y, s ->
        s.mapIndexed { x, c ->
            Plot(x, y, c == '#', c == 'S')
        }
    }
    return Map(plots)
}

private fun walk(map: Map, amount: Int): List<Int> {
    var steppedOn = listOf(map.start)
    val res = mutableListOf<Int>()
    repeat(amount) {
        steppedOn = steppedOn.flatMap {
            map.neighbours(it)
        }.distinct()
        res.add(steppedOn.size)
    }
    return res
}

private fun walk2(map: Map, amount: Int): List<Int> {
    var steppedOn = setOf(map.start)
    val res = mutableListOf<Int>()
    repeat(amount) {
        val n = mutableSetOf<Plot>()
        steppedOn.forEach {
            n.addAll(map.infiniteNeighbours(it))
        }
        steppedOn = n
        res.add(steppedOn.size)
    }
    return res
}

fun main() {
    run {
        val map = parse(testData)
        println(map)
        val q = walk(map, 6)
        println(q)
        check(16 == q.last())
    }

    run {
        val map = parse(aoc21data)
        val q = walk(map, 64)
        println(q)
        check(3594 == q.last())
    }

    run {
        val map = parse(testData)
        val q = walk2(map, 1000)
        println(q.last())
        check(668697 == q.last())
    }
}