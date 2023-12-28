package util

fun <T>combinationPairs(l: List<T>): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()
    for (i in 0 until (l.size - 1)) {
        for (t in l.subList(i + 1, l.size)) {
            result.add(l[i] to t)
        }
    }
    return result
}