package util.location

enum class Direction4(val location: Location) {
    North(Location(0, -1)),
    South(Location(0, 1)),
    East(Location(1, 0)),
    West(Location(-1, 0));

    fun toChar() = when (this) {
        North -> "N"
        South -> "S"
        East -> "E"
        West -> "W"
    }

    operator fun unaryMinus() = when (this) {
        North -> South
        South -> North
        East -> West
        West -> East
    }

    val x get() = location.x
    val y get() = location.y

    fun rotate90() = when (this) {
        North -> East
        East -> South
        South -> West
        West -> North
    }
}

enum class Direction8(val location: Location) {
    North(Location(0, -1)),
    South(Location(0, 1)),
    East(Location(1, 0)),
    West(Location(-1, 0)),
    NorthEast(Location(1, -1)),
    SouthEast(Location(1, 1)),
    NorthWest(Location(-1, -1)),
    SouthWest(Location(-1, 1));

    val x get() = location.x
    val y get() = location.y
}