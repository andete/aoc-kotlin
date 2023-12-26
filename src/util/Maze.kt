package util

data class Maze<L : Located>(val rows: List<List<L>>) {

    val ySize = rows.size
    val xSize = rows[0].size
    val size = rows.size * rows[0].size

    val yIndices get() = rows.indices
    val xIndices get() = rows[0].indices

    fun show(visited: List<L> = emptyList()) {
        for (row in rows) {
            println(row.joinToString("") { it.toChar(visited) })
        }
        println()
    }

    fun at(location: Location) = rows.getOrNull(location.y)?.getOrNull(location.x)
    fun at(x: Int, y: Int) = rows.getOrNull(y)?.getOrNull(x)


    fun neighbours(cell: L): List<Pair<Direction, L>> {
        return Direction.entries.mapNotNull { dir -> at(cell.location + dir)?.let { dir to it } }
    }

    fun neighbours2(cell: L): List<Pair<Direction2, L>> {
        return Direction2.entries.mapNotNull { dir -> at(cell.location + dir)?.let { dir to it } }
    }
}

data class LocatedItem<T>(override val location: Location, var t: T) : Located {
    override fun toChar(visited: List<Located>) = "$t"
}

typealias ItemMaze<T> = Maze<LocatedItem<T>>

typealias CharMaze = ItemMaze<Char>

fun <T> createItemMaze(xSize: Int, ySize: Int, valueProvider: (Int, Int) -> T): ItemMaze<T> {
    val items = (0 until ySize).map { y ->
        (0 until xSize).map { x ->
            LocatedItem(Location(x, y), valueProvider(x, y))
        }
    }
    return ItemMaze(items)
}

fun parseCharMaze(input: List<String>) = CharMaze(input.mapIndexed { y, s ->
    s.mapIndexed { x, c ->
        LocatedItem(Location(x, y), c)
    }
})

interface WithChar {
    val c: Char
}
inline fun <reified T>Char.toEnum(): T where T:WithChar, T:Enum<T> {
    val c = enumValues<T>()
    return c.first { it.c == this }
}