package util

import util.location.*

interface DeepCopyable<T> {
    fun deepCopy(): T
}

interface CharProvider {
    fun toChar(): String
}

data class Maze<L>(val rows: List<List<L>>) where L : Located, L : CharProvider {

    val ySize = rows.size
    val xSize = rows[0].size
    val size = rows.size * rows[0].size

    val yIndices get() = rows.indices
    val xIndices get() = rows[0].indices

    var visitedChar: Char? = null

    fun show(visited: Collection<L> = emptyList()) {
        for (row in rows) {
            println(row.joinToString("") {
                val c = it.toChar()
                if (it in visited) {
                    val c2 = visitedChar ?: c
                    "\u001B[31m$c2\u001B[0m"
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

    fun neighbours(cell: L): List<Pair<Direction4, L>> {
        return Direction4.entries.mapNotNull { dir -> at(cell.location + dir)?.let { dir to it } }
    }
    fun neighboursWithNull(cell: L): List<Pair<Direction4, L?>> {
        return Direction4.entries.map { dir -> at(cell.location + dir)?.let { dir to it } ?: (dir to null) }
    }

    fun neighbours2(cell: L): List<Pair<Direction8, L>> {
        return Direction8.entries.mapNotNull { dir -> at(cell.location + dir)?.let { dir to it } }
    }

    fun deepCopy(): Maze<L> =
        Maze(rows = rows.map { row ->
            row.map { t ->
                if (t is DeepCopyable<*>) {
                    t.deepCopy() as L
                } else {
                    t
                }
            }
        })
}

data class LocatedItem<T>(override val location: Location, var t: T) : Located, CharProvider, DeepCopyable<LocatedItem<T>> {
    override fun toChar() = if (t is Char) {
        "$t"
    } else if (t is WithChar) {
        val t2 = t as WithChar
        "${t2.c}"
    } else if (t is CharProvider) {
        (t as CharProvider).toChar()
    } else {
        "$t"
    }

    override fun deepCopy(): LocatedItem<T> {
        val newT = if (t is DeepCopyable<*>) {
            (t as DeepCopyable<T>).deepCopy()
        } else {
            t
        }
        return copy(t = newT)
    }

}

typealias ItemMaze<T> = Maze<LocatedItem<T>>

fun<T> ItemMaze<T>.find(t: T): Location? {
    for (row in rows) {
        for (x in row) {
            if (x.t == t) {
                return x.location
            }
        }
    }
    return null
}

fun<T> ItemMaze<T>.findAll(t: T): List<Location> {
    val res = mutableListOf<Location>()
    for (row in rows) {
        for (x in row) {
            if (x.t == t) {
                res.add(x.location)
            }
        }
    }
    return res
}

fun<T> ItemMaze<T>.findAllItems(t: T): List<LocatedItem<T>> {
    val res = mutableListOf<LocatedItem<T>>()
    for (row in rows) {
        for (x in row) {
            if (x.t == t) {
                res.add(x)
            }
        }
    }
    return res
}

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

inline fun <reified T> parseEnumMaze(input: List<String>): ItemMaze<T> where T : WithChar, T : Enum<T> {
    return ItemMaze(input.mapIndexed { y, s ->
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