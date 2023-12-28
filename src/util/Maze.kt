package util

data class Maze<L>(val rows: List<List<L>>) where L : Located, L : CharProvider {

    val ySize = rows.size
    val xSize = rows[0].size
    val size = rows.size * rows[0].size

    val yIndices get() = rows.indices
    val xIndices get() = rows[0].indices

    fun show(visited: List<L> = emptyList()) {
        for (row in rows) {
            println(row.joinToString("") {
                val c = it.toChar()
                if (it in visited) {
                    "\u001B[31m$c\u001B[0m"
                } else {
                    c
                }
            })
        }
        println()
    }

    fun at(location: Location) = rows.getOrNull(location.y)?.getOrNull(location.x)
    fun at(x: Int, y: Int) = rows.getOrNull(y)?.getOrNull(x)

    fun peekNorthOf(cell: L) = at(cell.location.x, cell.location.y - 1)
    fun peekSouthOf(cell: L) = at(cell.location.x, cell.location.y + 1)
    fun peekWestOf(cell: L) = at(cell.location.x - 1, cell.location.y)
    fun peekEastOf(cell: L) = at(cell.location.x + 1, cell.location.y)

    fun neighbours(cell: L): List<Pair<Direction, L>> {
        return Direction.entries.mapNotNull { dir -> at(cell.location + dir)?.let { dir to it } }
    }

    fun neighbours2(cell: L): List<Pair<Direction2, L>> {
        return Direction2.entries.mapNotNull { dir -> at(cell.location + dir)?.let { dir to it } }
    }
}

data class LocatedItem<T>(override val location: Location, var t: T) : Located, CharProvider {
    override fun toChar() = if (t is Char) {
        "$t"
    } else if (t is WithChar) {
        val t2 = t as WithChar
        "${t2.c}"
    } else {
        "$t"
    }

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

inline fun <reified T> Char.toEnum(): T where T : WithChar, T : Enum<T> {
    val c = enumValues<T>()
    return c.first { it.c == this }
}

typealias EnumMaze<T> = ItemMaze<Enum<T>>

inline fun <reified T> parseEnumMaze(input: List<String>): EnumMaze<T> where T : WithChar, T : Enum<T> {
    return EnumMaze(input.mapIndexed { y, s ->
        s.mapIndexed { x, c ->
            val t: T = c.toEnum()
            LocatedItem(Location(x, y), t)
        }
    })
}

inline fun <reified T> makeItemMaze(input: List<List<T>>): ItemMaze<T> {
    return ItemMaze(input.mapIndexed { y, s ->
        s.mapIndexed { x, t ->
            LocatedItem(Location(x, y), t)
        }
    })
}