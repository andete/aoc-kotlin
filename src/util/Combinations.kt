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

fun <T>combinationSets(s: Set<T>, size: Int): List<Set<T>> {
    val res = mutableSetOf<Set<T>>()
    if (size == 1) { return s.map { setOf(it) } }
    for (a in s) {
        val rem = s - setOf(a)
        val c = combinationSets(rem, size - 1)
        val setsWithA = c.map { it + setOf(a) }
        res.addAll(setsWithA)
    }
    return res.toList()
}