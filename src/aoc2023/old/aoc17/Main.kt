package be.damad.aoc2023.aoc17

private val testData = """2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533""".split('\n')

private data class Block(
    val x: Int,
    val y: Int,
    val loss: Int,
)

private data class Map(val blocks: List<List<Block>>) {

    fun deepCopy(): Map =
        Map(blocks = blocks.map { row -> row.toList() })

    fun at(x: Int, y: Int) = blocks.getOrNull(y)?.getOrNull(x)

    fun print(visited: List<Block> = emptyList()) {
        for (tile in blocks) {
            val l = tile.joinToString("") {
                if (it in visited) {
                    "*"
                } else {
                    "${it.loss}"
                }
            }
            println(l)
        }
        println()
    }

}

private fun parse(data: List<String>): Map {
    val cells = data.mapIndexed { yIndex, s ->
        s.mapIndexed { xIndex, c ->
            Block(xIndex, yIndex, "$c".toInt())
        }
    }
    return Map(cells)
}

private enum class Direction(val x: Int, val y: Int) {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
}

private fun findPath(
    p: Map,
    x: Int = 0,
    y: Int = 0,
    loss: Int = 0,
    direction: Direction? = null,
    movements: List<Direction> = emptyList(),
    visited: List<Block> = listOf(p.at(0, 0)!!),
    minSoFar: Int = Int.MAX_VALUE,
): Int? {
    if (x == p.blocks[0].size - 1 && y == p.blocks.size - 1) {
        return loss
    }
    //p.print(visited)
    val lastDirections = movements.takeLast(3)
    var banned: Direction? = null
    if (lastDirections.distinct().size == 1) {
        banned = lastDirections.first()
    }
    var minSoFar = minSoFar
    val newDirections = Direction.entries.filter { it != banned }
    val newBlocks = newDirections.mapNotNull { dir ->
        p.at(x + dir.x, y + dir.y)?.let { dir to it }
    }.filter { it.second !in visited }.sortedBy { it.second.loss }
    for (newBlock in newBlocks) {
        val newLoss = loss + newBlock.second.loss
        if (newLoss >= minSoFar) {
            continue
        }
        val newVisited = visited + listOf(newBlock.second)
        val newMovements = movements + listOfNotNull(direction)
        val r = findPath(
            p,
            newBlock.second.x,
            newBlock.second.y,
            newLoss,
            newBlock.first,
            newMovements,
            newVisited
        )
        if (r != null) {
            if (r < minSoFar) {
                println(r)
                p.print(newVisited)
                minSoFar = r
            }
        }
    }
    return minSoFar
}

fun main() {
    run {
        val p = parse(testData)
        p.print()
        val r = findPath(p)
        println(r)
        check(102 == r)
    }
}