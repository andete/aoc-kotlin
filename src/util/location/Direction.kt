package util.location

enum class Direction4(val location: Location) {
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

    val x get() = location.x
    val y get() = location.y
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