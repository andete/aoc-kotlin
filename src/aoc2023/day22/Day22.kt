package aoc2023.day22

import day
import kotlin.math.max
import kotlin.math.min

private data class Location(val x: Int, val y: Int, val z: Int)

private data class Block(val i: Int, var p: Location, var q: Location) {
    val minX get() = min(p.x, q.x)
    val minY get() = min(p.y, q.y)
    val minZ get() = min(p.z, q.z)
    val maxX get() = max(p.x, q.x)
    val maxY get() = max(p.y, q.y)
    val maxZ get() = max(p.z, q.z)

    fun cells(): List<Location> {
        return (minX..maxX).flatMap { x ->
            (minY..maxY).flatMap { y ->
                (minZ..maxZ).map { z ->
                    Location(x, y, z)
                }
            }
        }
    }
}

private fun parse(data: List<String>) = data.mapIndexed { index, it ->
    val a = it.split("~")
    val a1 = a[0]
    val c1 = a1.split(",").map { it.toInt() }
    val a2 = a[1]
    val c2 = a2.split(",").map { it.toInt() }
    Block(index + 1, Location(c1[0], c1[1], c1[2]), Location(c2[0], c2[1], c2[2]))
}

private data class Space(val s: MutableList<MutableList<MutableList<Int>>> = mutableListOf()) {

    fun at(location: Location) = at(location.x, location.y, location.z)
    fun at(x: Int, y: Int, z: Int) = s.getOrNull(z)?.getOrNull(y)?.getOrNull(x)

    fun mark(cell: Location, i: Int) {
        for (i1 in ((s.size - 1)..cell.z)) {
            s.add(mutableListOf())
        }
        val z = s[cell.z]
        for (i1 in ((z.size - 1)..cell.y)) {
            z.add(mutableListOf())
        }
        val y = z[cell.y]
        for (i1 in ((y.size - 1)..cell.x)) {
            y.add(0)
        }
        y[cell.x] = i
    }

    override fun toString(): String {
        var b = ""
        for (i in s.indices) {
            val z = s[i]
            b += "$i\n"
            for (y in z) {
                b += y.toString()
                b += '\n'
            }
            b += '\n'
        }
        return b
    }
}

private fun space(blocks: List<Block>): Space {
    val s = Space()
    for (block in blocks) {
        for (cell in block.cells()) {
            s.mark(cell, block.i)
        }
    }
    return s
}

private fun drop(space: Space, blocks: List<Block>): Int {
    var dropped = true
    val blocksDropped: MutableList<Block> = mutableListOf()
    while (dropped) {
        dropped = false
        val candidates = blocks.sortedBy { it.minZ }.filter { it.minZ > 1 }
        for (candidate in candidates) {
            val minZ = candidate.minZ
            val bottom = candidate.cells().filter { it.z == minZ }
            val under = bottom.map { it.copy(z = it.z - 1) }
            val underBlocks = under.mapNotNull { space.at(it) }
            if (underBlocks.all { it == 0 }) {
                dropOne(space, candidate)
                if (candidate !in blocksDropped) {
                    blocksDropped.add(candidate)
                }
                dropped = true
            }
        }
    }
    return blocksDropped.size
}

private fun dropOne(space: Space, block: Block) {
    for (cell in block.cells()) {
        space.mark(cell, 0)
    }
    block.p = block.p.copy(z = block.p.z - 1)
    block.q = block.q.copy(z = block.q.z - 1)
    for (cell in block.cells()) {
        space.mark(cell, block.i)
    }
}


private fun safeToDisintegrate(space: Space, blocks: List<Block>): Int {
    return blocks.count {
        safeToDisintegrateOne(space, blocks, it)
    }
}

private fun safeToDisintegrateOne(space: Space, blocks: List<Block>, block: Block): Boolean {
    val locationsAboveBlock = block.cells().filter { it.z == block.maxZ }.map {
        it.copy(z = it.z + 1)
    }
    val blocksAboveBlock = locationsAboveBlock.mapNotNull { space.at(it) }.filter { it != 0 }.distinct().map {
        blocks[it - 1]
    }
    val safe=  blocksAboveBlock.all { above ->
        val locationsBelowBlock = above.cells().filter { it.z == above.minZ }.map {
            it.copy(z = it.z - 1)
        }
        val blocksBelowBlock = locationsBelowBlock.mapNotNull { space.at(it) }.filter { it != 0 }.distinct().map {
            blocks[it - 1]
        }
        blocksBelowBlock.size > 1
    }
    return safe
}

private fun calculatePart2(blocks: List<Block>): Int {
    return blocks.sumOf { block ->
        val x = part2One(blocks, block)
        println("cascade ${block.i} $x")
        x
    }
}

private fun part2One(blocks: List<Block>, block: Block): Int {
    val newBlocks = blocks.filter { it != block }.map { it.copy(q = it.q.copy(), p = it.p.copy()) }
    val newSpace = space(newBlocks)
    return drop(newSpace, blocks)
}

private fun part1(data: List<String>): Int {
    val blocks = parse(data)
    println(blocks)
    val space = space(blocks)
    println(space)
    drop(space, blocks)
    println(space)
    return safeToDisintegrate(space, blocks)
}

private fun part2(data: List<String>): Int {
    val blocks = parse(data)
    val space = space(blocks)
    drop(space, blocks)
    return calculatePart2(blocks)
}

fun main() {
    day(2023, 22) {
        part1(5, "example", ::part1)
        part1(492, "input", ::part1)
        part2(7, "example", ::part2)
        part2(7, "input", ::part2)
    }
}