package aoc01

import java.nio.file.Paths

fun main() {
    val lines = Paths.get("src/main/resources/aoc_01.txt").toFile().useLines { it.toMutableList() }
    val linesAsInt = lines.map { it.toIntOrNull() }.toMutableList();

    linesAsInt.add(null)

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
    var lastIndex = 0;

    return lines.asSequence().withIndex()
        .filter { it.value == null }
        .map { it.index }
        .map { it ->
            val currentIndex = lastIndex;
            lastIndex = it + 1;
            lines.subList(currentIndex, it).sumOf { it!! }
        }
        .sortedDescending()
        .take(size)
        .sum()
}