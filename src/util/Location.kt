package util

data class Location(val x: Int, val y: Int) {
    operator fun plus(other: Location) = Location(x + other.x, y + other.y)
    operator fun plus(other: Direction) = Location(x + other.location.x, y + other.location.y)
}

enum class Direction(val location: Location) {
    North(Location(0, -1)),
    South(Location(0, 1)),
    East(Location(1, 0)),
    West(Location(-1, 0)),
}

interface Located {
    val location: Location
    fun toChar(visited: List<Located>): Char

}