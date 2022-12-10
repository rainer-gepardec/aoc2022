package aoc10

import java.nio.file.Paths

fun main() {
    val lines = Paths.get("src/main/resources/aoc_10.txt").toFile().useLines { it.toList() }

    println(solution1(lines))
    println(solution2(lines))
}

fun solution1(lines: List<String>): Int {
    return solve(lines)
        .filter { it.first == 20 || ((it.first - 20) % 40) == 0 }
        .sumOf { it.first * it.second }
}

fun solution2(lines: List<String>): Int {

    val crt = Array(6) { CharArray(40) }

    for (row in 0 until 6) {
        for (column in 0 until 40) {
            crt[row][column] = '.'
        }
    }

    solve(lines)
        .forEach {
            val row = (it.first - 1) / 40
            val column = (it.first - 1) % 40

            if (IntRange(it.second - 1, it.second + 1).contains(column)) {
                crt[row][column] = '#'
            }
        }

    crt.forEach { println(it) }
    return 0
}

fun solve(lines: List<String>): List<Pair<Int, Int>> {
    val cycles = mutableListOf<Pair<Int, Int>>()
    lines
        .map { if (it.startsWith("addx")) listOf("noop", it) else listOf(it) }
        .flatten()
        .map { it.split(" ").getOrElse(1) { "0" }.toInt() }
        .foldIndexed(1) { index, result, add ->
            cycles.add(Pair(index + 1, result));
            result.plus(add)
        }
    return cycles
}