package util.location

interface LocationBase<I : Number, T : LocationBase<I, T>> {
    val x: I
    val y: I

    operator fun plus(other: T): T
    operator fun plus(other: Direction4): T
    operator fun plus(other: Direction8): T

//    operator fun plusAssign(other: Direction4)
//    operator fun plusAssign(other: Direction8)

    operator fun times(i: Int): T
    operator fun times(i: Long): T

}


