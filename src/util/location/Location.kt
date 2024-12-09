package util.location

data class Location(override val x: Int, override val y: Int): LocationBase<Int, Location> {
    override operator fun plus(other: Location) = Location(x + other.x, y + other.y)
    override operator fun minus(other: Location) = Location(x - other.x, y - other.y)
    override operator fun plus(other: Direction4) = Location(x + other.location.x, y + other.location.y)
    override operator fun plus(other: Direction8) = Location(x + other.location.x, y + other.location.y)
    override operator fun times(i: Int) = Location(x * i, y * i)

    override operator fun times(i: Long) = Location(x * i.toInt(), y * i.toInt())

    fun direction(other: Location): Direction4 {
        return if (other.x > x) {
            Direction4.East
        } else if (other.x < x) {
            Direction4.West
        } else if (other.y > y) {
            Direction4.South
        } else if (other.y < y) {
            Direction4.North
        } else {
            error("can't calculate direction")
        }
    }
}

interface Located {
    val location: Location
    val x get() = location.x
    val y get() = location.y

}