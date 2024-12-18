package aoc2024.day17

import day
import kotlin.math.pow

fun main() {
    day(2024, 17) {
        part1("4,6,3,5,6,3,5,2,1,0", "example", ::part1)
        part1("7,0,7,3,4,1,3,0,1", "input", ::part1)
        part1("0,3,5,4,3,0", "example2a", ::part1)
        part2(117440, "example2", ::part2)
        part2(84893551, "input", ::part2)

    }
}

private enum class Instruction {
    Adv, Bxl, Bst, Jnz, Bxc, Out, Bdv, Cdv
}

private data class Computer(
    val program: List<Int>,
    var a: Long,
    var b: Long,
    var c: Long,
    var pc: Int = 0,
    val output: MutableList<Int> = mutableListOf()
) {
    private fun combo(operator: Int): Long {
        return when (operator) {
            0, 1, 2, 3 -> operator.toLong()
            4 -> a
            5 -> b
            6 -> c
            7 -> error("reserved")
            else -> error("illegal operator")
        }
    }

    fun start() {
        while (true) {
            val opcode = program.getOrNull(pc) ?: return
            val operator = program.getOrNull(pc + 1) ?: error("expecting operator after opcode")
            pc += 2
            val instruction = Instruction.entries[opcode]
            execute(instruction, operator)
        }
    }

    private fun execute(instruction: Instruction, operator: Int) {
        when (instruction) {
            Instruction.Adv -> {
                a = a / (2.0.pow(combo(operator).toDouble()).toLong())
            }

            Instruction.Bxl -> {
                b = b xor operator.toLong()
            }

            Instruction.Bst -> {
                b = combo(operator) % 8
            }

            Instruction.Jnz -> {
                if (a != 0L) {
                    pc = operator
                }
            }

            Instruction.Bxc -> {
                b = b xor c
            }

            Instruction.Out -> {
                output.add(combo(operator).toInt() % 8)
            }

            Instruction.Bdv -> {
                b = a / (2.0.pow(combo(operator).toDouble()).toLong())
            }

            Instruction.Cdv -> {
                c = a / (2.0.pow(combo(operator).toDouble()).toLong())
            }
        }
    }
}

private data class Computer2(
    val program: List<Int>,
    var a: Long,
    var b: Long,
    var c: Long,
    var pc: Int = 0,
    val output: MutableList<Int> = mutableListOf()
) {
    private fun combo(operator: Int): Long {
        return when (operator) {
            0, 1, 2, 3 -> operator.toLong()
            4 -> a
            5 -> b
            6 -> c
            7 -> error("reserved")
            else -> error("illegal operator")
        }
    }

    fun start() {
        while (true) {
            val opcode = program.getOrNull(pc) ?: return
            val operator = program.getOrNull(pc + 1) ?: error("expecting operator after opcode")
            pc += 2
            val instruction = Instruction.entries[opcode]
            if (!execute(instruction, operator)) {
                break
            }
        }
    }

    private fun execute(instruction: Instruction, operator: Int): Boolean {
        when (instruction) {
            Instruction.Adv -> {
                a = a / (2.0.pow(combo(operator).toDouble()).toLong())
            }

            Instruction.Bxl -> {
                b = b xor operator.toLong()
            }

            Instruction.Bst -> {
                b = combo(operator) % 8
            }

            Instruction.Jnz -> {
                if (a != 0L) {
                    pc = operator
                }
            }

            Instruction.Bxc -> {
                b = b xor c
            }

            Instruction.Out -> {
                val new = combo(operator).toInt() % 8
                output.add(new)
                if (program[output.size - 1] != new) {
                    return false
                }

            }

            Instruction.Bdv -> {
                b = a / (2.0.pow(combo(operator).toDouble()).toLong())
            }

            Instruction.Cdv -> {
                c = a / (2.0.pow(combo(operator).toDouble()).toLong())
            }
        }
        return true
    }
}


private fun part1(data: List<String>): String {
    val a = data[0].split(" ")[2].toLong()
    val b = data[1].split(" ")[2].toLong()
    val c = data[2].split(" ")[2].toLong()
    val program = data[4].split(" ")[1].split(",").map { it.toInt() }
    val computer = Computer(program, a, b, c)
    println(computer)
    computer.start()
    return computer.output.joinToString(",")
}

private fun part2(data: List<String>): Int {
    val a = data[0].split(" ")[2].toLong()
    val b = data[1].split(" ")[2].toLong()
    val c = data[2].split(" ")[2].toLong()
    val program = data[4].split(" ")[1].split(",").map { it.toInt() }
    println(Int.MAX_VALUE)
    for (a in 0 until Int.MAX_VALUE) {
        if (a % 10000000 == 0) {
            println(a)
        }
        val comp = Computer2(program, a.toLong(), b, c)
        comp.start()
        if (comp.output.size == program.size) {
            if (comp.output == program) {
                return a
            }
        }
    }
    return 0
}

private fun part2alt(data: List<String>): Int {
    TODO()
}