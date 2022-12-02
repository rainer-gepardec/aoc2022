package aoc01

import java.nio.file.Paths

fun main() {
    val lines = Paths.get("src/main/resources/aoc_01.txt").toFile().useLines { it.toMutableList() }
    val linesAsInt = lines.map { it.toIntOrNull() };

    println(solution1(linesAsInt))
    println(solution2(linesAsInt))
}

fun solution1(calories: List<Int?>): Int {
    return solve(calories, 1);
}

fun solution2(calories: List<Int?>): Int {
    return solve(calories, 3);
}

fun solve(lines: List<Int?>, size: Int): Int {
    return lines.fold(mutableListOf(mutableListOf<Int>())) { acc, e ->
        if (e != null) acc.last().add(e); else acc.add(mutableListOf()); acc
    }
        .map { it.sum() }
        .sortedDescending()
        .take(size)
        .sum();
}