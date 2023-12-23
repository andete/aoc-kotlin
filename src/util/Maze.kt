package util

data class Maze<L : Located>(val cells: List<List<L>>) {
    fun show(visited: List<L> = emptyList()) {
        for (row in cells) {
            println(row.joinToString("") { "${it.toChar(visited)}" })
        }
        println()
    }

    fun at(location: Location) = cells.getOrNull(location.y)?.getOrNull(location.x)
    fun at(x: Int, y: Int) = cells.getOrNull(y)?.getOrNull(x)


    fun neighbours(cell: L): List<Pair<Direction, L>> {
        return Direction.entries.mapNotNull { dir -> at(cell.location + dir)?.let { dir to it } }
    }
}