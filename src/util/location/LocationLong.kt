package util.location

data class LocationLong(override val x: Long, override val y: Long) : LocationBase<Long, LocationLong> {
    override operator fun plus(other: LocationLong) = LocationLong(x + other.x, y + other.y)
    override operator fun minus(other: LocationLong) = LocationLong(x - other.x, y - other.y)
    operator fun plus(other: Location) = LocationLong(x + other.x, y + other.y)

    override operator fun plus(other: Direction4) = LocationLong(x + other.x, y + other.y)
    override operator fun plus(other: Direction8) = LocationLong(x + other.x, y + other.y)
    override operator fun times(i: Int) = LocationLong(x * i, y * i)

    override operator fun times(i: Long) = LocationLong(x * i, y * i)

}