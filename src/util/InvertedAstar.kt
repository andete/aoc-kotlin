package util

import java.util.PriorityQueue

object InvertedAStar {

    interface NeighboursProvider<E> {
        fun neighbours(visited: List<E>): List<NeighboursProvider<E>>
    }

    data class Node<E : NeighboursProvider<E>>(
        val e: E,
        val parent: Node<E>?,
    ) {
        fun neighbours(visited: List<E>) = e.neighbours(visited).map { Node(it as E, this) }

        override fun toString() = "Node($e, ${parent?.e})"
    }

    fun <E : NeighboursProvider<E>> path(
        start: E,
        goal: E,
        heuristic: (E) -> Int,
        distance: (E, E) -> Int
    ): List<E> {
        val startNode = Node(start, null)
        if (start == goal) {
            return listOf(start)
        }

        val gScoreMap = mutableMapOf(startNode to 0)
        val fScoreMap = mutableMapOf(startNode to heuristic(start))
        fun gScore(n: Node<E>) = gScoreMap.getOrDefault(n, Int.MIN_VALUE)
        fun fScore(n: Node<E>) = fScoreMap.getOrDefault(n, Int.MIN_VALUE)
        val openSet = PriorityQueue<Node<E>> { o1, o2 -> -fScore(o1).compareTo(fScore(o2)) }
        val closedSet = hashSetOf<Node<E>>()
        var solutions = mutableListOf<Node<E>>()
        openSet.add(startNode)
        while (openSet.isNotEmpty()) {
            val current = openSet.poll() ?: break
            closedSet.add(current)
            if (current.e == goal) {
                solutions.add(current)
                continue
            }
            val visited = reconstruct(current)
            val neighbours = current.neighbours(visited)
            for (neighbour in neighbours) {
                if (neighbour in closedSet) {
                    continue
                }
                val tentative = gScore(current) + distance(current.e, neighbour.e)
                if (tentative > gScore(neighbour)) {
                    gScoreMap[neighbour] = tentative
                    fScoreMap[neighbour] = tentative + heuristic(neighbour.e)
                    if (neighbour !in openSet) {
                        openSet.add(neighbour)
                    }
                }
            }
        }
        if (solutions.isEmpty()) {
            error("No solution")
        }
        return reconstruct(solutions.maxBy { gScore(it) })
    }

    private fun <E : NeighboursProvider<E>> reconstruct(current: Node<E>): List<E> {
        val list = mutableListOf(current)
        var p = current.parent
        while (p != null) {
            list.add(p)
            p = p.parent
        }
        return list.map { it.e }.reversed()
    }
}