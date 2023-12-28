package util

import kotlin.math.E

data class Location(val x: Int, val y: Int) {
    operator fun plus(other: Location) = Location(x + other.x, y + other.y)
    operator fun plus(other: Direction) = Location(x + other.location.x, y + other.location.y)
    operator fun plus(other: Direction2) = Location(x + other.location.x, y + other.location.y)

    fun direction(other: Location): Direction {
        return if (other.x > x) {
            Direction.East
        } else if (other.x < x) {
            Direction.West
        } else if (other.y > y) {
            Direction.South
        } else if (other.y < y) {
            Direction.North
        } else {
            error("can't calculate direction")
        }
    }
}

enum class Direction(val location: Location) {
    North(Location(0, -1)),
    South(Location(0, 1)),
    East(Location(1, 0)),
    West(Location(-1, 0));

    operator fun unaryMinus() = when (this) {
        North -> South
        South -> North
        East -> West
        West -> East
    }
}

interface Located {
    val location: Location
}

interface CharProvider {
    fun toChar(): String
}

enum class Direction2(val location: Location) {
    North(Location(0, -1)),
    South(Location(0, 1)),
    East(Location(1, 0)),
    West(Location(-1, 0)),
    NorthEast(Location(1, -1)),
    SouthEast(Location(1, 1)),
    NorthWest(Location(-1, -1)),
    SouthWest(Location(-1, 1)),
}