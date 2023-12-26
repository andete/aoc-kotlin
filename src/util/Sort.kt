package util

fun <T> unsortedCombinations(
    l: List<T>,
    len: Int,
    comp: Comparator<T>
): List<List<T>> {
    if (len == 0) {
        return emptyList()
    }
    if (len == 1) {
        return l.map { listOf(it) }
    }
    val res = mutableListOf<List<T>>()
    for (t in l) {
        val l2 = l.toMutableList()
        l2.remove(t)
        for (combination in unsortedCombinations(l2, len - 1, comp)) {
            val new = (listOf(t) + combination).sortedWith(comp)
            if (new !in res) {
                res.add(new)
            }
        }
    }
    return res
}

fun <T> unsortedCombinationsFind(
    l: List<T>,
    len: Int,
    comp: Comparator<T>,
    check: (List<T>) -> Boolean,
): List<T>? {
    return unsortedCombinationsFind2(l, len, comp, check, emptyList())
}

private fun <T> unsortedCombinationsFind2(
    l: List<T>,
    len: Int,
    comp: Comparator<T>,
    check: (List<T>) -> Boolean,
    collect: List<T>,
): List<T>? {
    if (len == 0) {
        if (check(collect)) {
            return collect
        }
        return null
    }
    val res = mutableListOf<List<T>>()
    for (t in l) {
        val l2 = l.toMutableList()
        l2.remove(t)
        val c2 = collect + listOf(t)
        unsortedCombinationsFind2(l2, len - 1, comp, check, c2)?.let {
            return it
        }
    }
    return null
}