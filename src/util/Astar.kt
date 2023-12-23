package util

import java.util.PriorityQueue

object AStar {

    data class Node<E>(
        val e: E,
        val parent: Node<E>?,
    ) {
        override fun toString() = "Node($e, ${parent?.e})"

        // need explicit equals and hashCode to avoid recursively going into the parent node
        // when comparing or hashing

        override fun equals(other: Any?) = when (other) {
            is Node<*> -> other.e == e && other.parent?.e == parent?.e
            else -> false
        }

        override fun hashCode(): Int {
            var result = e.hashCode()
            result = 31 * result + (parent?.e?.hashCode() ?: 0)
            return result
        }

    }

    fun <E>path(
        start: E,
        goal: E,
        cost: (E, E) -> Int,
        neighbours: (E, List<E>) -> List<E>,
        heuristic: (E) -> Int = { 0 },
        show: (List<E>) -> Unit = {}
    ): List<E> {
        val startNode = Node(start, null)
        if (start == goal) {
            return listOf(start)
        }

        val gScoreMap = hashMapOf(startNode to 0)
        val fScoreMap = hashMapOf(startNode to heuristic(start))
        fun gScore(n: Node<E>) = gScoreMap.getOrDefault(n, Int.MAX_VALUE)
        fun fScore(n: Node<E>) = fScoreMap[n]!!
        val openSet = PriorityQueue<Node<E>> { o1, o2 -> fScore(o1).compareTo(fScore(o2)) }
        val closedSet = hashSetOf<Node<E>>()

        openSet.add(startNode)
        while (openSet.isNotEmpty()) {
            val current = openSet.poll() ?: break
            closedSet.add(current)
            if (current.e == goal) {
                return reconstruct(current)
            }
            fScoreMap.remove(current)
            val score = gScore(current)
            gScoreMap.remove(current)
            val visited = reconstruct(current)
            // println(score)
            show(visited)
            val neighboursNodes = neighbours(current.e, visited).map { Node(it, current) }
            for (neighbour in neighboursNodes) {
                if (closedSet.contains(neighbour)) {
                    continue
                }
                val tentative = score + cost(current.e, neighbour.e)
                if (tentative < gScore(neighbour)) {
                    gScoreMap[neighbour] = tentative
                    fScoreMap[neighbour] = tentative + heuristic(neighbour.e)
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour)
                    }
                }
            }
        }
        error("No solution")
    }

    private fun <E> reconstruct(current: Node<E>): List<E> {
        val list = mutableListOf(current)
        var p = current.parent
        while (p != null) {
            list.add(p)
            p = p.parent
        }
        return list.map { it.e }.reversed()
    }
}