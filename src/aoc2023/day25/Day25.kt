package aoc2023.day25

import readInput
import util.unsortedCombinations
import util.unsortedCombinationsFind


private data class Edge(val left: String, val right: String) {
    override fun toString() = "($left,$right)"

    val nodes get() = listOf(left, right)

    fun other(s: String) = if (s == left) {
        right
    } else if (s == right) {
        left
    } else {
        null
    }
}

private fun groups(edges: List<Edge>): List<Set<String>> {
    val res = mutableListOf<HashSet<String>>()
    for (edge in edges) {
        val candidates = res.filter { edge.left in it || edge.right in it }
        if (candidates.isEmpty()) {
            res.add(hashSetOf(edge.left, edge.right))
        } else if (candidates.size == 1) {
            candidates[0].add(edge.left)
            candidates[0].add(edge.right)
        } else if (candidates.size == 2) {
            val c1 = candidates[0]
            val c2 = candidates[1]
            res.remove(c1)
            res.remove(c2)
            val c = (c1 + c2).toHashSet()
            c.add(edge.left)
            c.add(edge.right)
            res.add(c)
        } else {
            error("impossible situation")
        }
    }
    return res
}

private fun edgesToNodes(edges: List<Edge>): Map<String, HashSet<String>> {
    val res = mutableMapOf<String, HashSet<String>>()
    for (edge in edges) {
        if (edge.left !in res) {
            res[edge.left] = hashSetOf(edge.right)
        } else {
            res[edge.left]!!.add(edge.right)
        }
        if (edge.right !in res) {
            res[edge.right] = hashSetOf(edge.left)
        } else {
            res[edge.right]!!.add(edge.left)
        }
    }
    return res
}

private fun groups2(nodes: Map<String, HashSet<String>>, edgesToRemove: List<Edge>): Pair<Int, Int> {
    for (edge in edgesToRemove) {
        nodes[edge.left]!!.remove(edge.right)
        nodes[edge.right]!!.remove(edge.left)
    }
    val one = nodes.keys.first()
    val seen = hashSetOf(one)
    flood(nodes, seen, one)
    for (edge in edgesToRemove) {
        nodes[edge.left]!!.add(edge.right)
        nodes[edge.right]!!.add(edge.left)
    }
    return seen.size to nodes.size - seen.size
}

private fun flood(nodes: Map<String, HashSet<String>>, seen: HashSet<String>, one: String) {
    val candidates = mutableListOf(one)
    val maxSize = nodes.size
    seen.add(one)
    while (candidates.isNotEmpty() && seen.size < maxSize) {
        val f = candidates.removeAt(0)
        val nexts = nodes[f]!!.filter { it !in seen }
        candidates.addAll(nexts)
        seen.addAll(nexts)
    }
}

private fun parse(data: List<String>): List<Edge> {
    val edges = mutableSetOf<Edge>()
    data.map { line ->
        val x = line.split(":")
        val left = x[0]
        val rights = x[1].split(" ", ", ").filter { it.isNotEmpty() }
        val newEdges = rights.map { right ->
            if (left < right) {
                Edge(left, right)
            } else {
                Edge(right, left)
            }
        }
        edges.addAll(newEdges)
    }
    return edges.toList().sortedBy { it.left + it.right }
}

private fun find(edges: List<Edge>): Int {
    val c = unsortedCombinations(edges, 3, compareBy { it.left + it.right })
    println(c.size)
    val sub = c.first {
        val e2 = edges.toMutableList()
        e2.removeAll(it)
        groups(e2).size == 2
    }
    println(sub)
    val e2 = edges.toMutableList()
    e2.removeAll(sub)
    val g = groups(e2)
    return g[0].size * g[1].size
}

private fun find2(edges: List<Edge>): Int {
    val sub = unsortedCombinationsFind(edges, 3, compareBy { it.left + it.left }) {
        val e2 = edges.toMutableList()
        e2.removeAll(it)
        groups(e2).size == 2
    }!!
    val e2 = edges.toMutableList()
    e2.removeAll(sub)
    val g = groups(e2)
    return g[0].size * g[1].size
}

private fun find3(edges: List<Edge>): Int {
    val nodes = edgesToNodes(edges)
    for (e1 in 0 until (edges.size - 2)) {
        val e1e = edges[e1]
        for (e2 in (e1 + 1) until (edges.size - 1)) {
            val e2e = edges[e2]
            if (e2e.nodes.any { it in e1e.nodes }) {
                continue
            }
            println("i: $e1 j: $e2")
            for (e3 in (e2 + 1) until (edges.size)) {
                val e3e = edges[e3]
                if (e3e.nodes.any { it in (e1e.nodes + e2e.nodes) }) {
                    continue
                }
                val g = groups2(nodes, listOf(e1e, e2e, e3e))
                if (g.first > 0 && g.second > 0) {
                    return g.first * g.second
                }
            }
        }
    }
    return 0
}

private fun flood2(nodes: Map<String, HashSet<String>>, one: String, hotness: MutableMap<Edge, Int>) {
    val candidates = mutableListOf(one)
    val maxSize = nodes.size
    val seen = hashSetOf(one)
    while (candidates.isNotEmpty() && seen.size < maxSize) {
        val f = candidates.removeAt(0)
        val node = nodes[f]!!
        val nexts = node.filter { it !in seen }
        for (next in nexts) {
            val edge = if (f < next) {
                Edge(f, next)
            } else {
                Edge(next, f)
            }
            hotness[edge] = hotness[edge]!! + 1
        }
        candidates.addAll(nexts)
        seen.addAll(nexts)
    }
}

private fun find4(edges: List<Edge>): Int {
    val nodes = edgesToNodes(edges)
    val hotness = edges.associateWith { 0 }.toMutableMap()
    for (node in nodes.keys) {
        flood2(nodes, node, hotness)
    }
    val sortedByHotness = hotness.map { it.key to it.value }.sortedBy { it.second }
    val highestHotness = sortedByHotness.takeLast(3).map { it.second }.distinct()
    val removeCandidates = sortedByHotness.filter { it.second in highestHotness }.map { it.first }
    for (i in 0 until (removeCandidates.size - 2)) {
        for (j in (i + 1) until (removeCandidates.size - 1)) {
            for (k in (j + 1) until (removeCandidates.size)) {
                val toRemove = listOf(i, j, k)
                val toRemoveEdge = toRemove.map { removeCandidates[it] }
                val remainingEdges = edges.filter { it !in toRemoveEdge }
                val g = groups(remainingEdges)
                if (g.size > 1) {
                    return g[0].size * g[1].size
                }
            }
        }
    }
    return 0
}

fun main() {

    run {
        println(unsortedCombinations(listOf("A", "B", "C", "D"), 3, compareBy { it }))
        val testInput = readInput(2023, 25, "test")
        val p = parse(testInput)
        println(p)
        val g = groups(p)
        println(g.size)
        println(g)
        val res = find(p)
        println(res)
        check(54 == res)
    }

    run {
        val testInput = readInput(2023, 25, "test")
        val p = parse(testInput)
        val res = find2(p)
        println(res)
        check(54 == res)
    }

    run {
        val testInput = readInput(2023, 25, "test")
        val p = parse(testInput)
        val res = find3(p)
        println(res)
        check(54 == res)
    }

    run {
        val testInput = readInput(2023, 25, "test")
        val p = parse(testInput)
        val res = find4(p)
        println(res)
        check(54 == res)
    }

    run {
        val input = readInput(2023, 25)
        val p = parse(input)
        val res = find4(p)
        println(res)
        check(54 == res)
    }

}