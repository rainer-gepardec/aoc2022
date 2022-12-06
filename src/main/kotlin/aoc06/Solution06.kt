package aoc06

import java.nio.file.Paths

fun main() {
    val line = Paths.get("src/main/resources/aoc_06.txt").toFile().useLines { it.toList() }.single()

    println(solution1(line))
    println(solution2(line))
}

fun solution1(line: String): Int {
    return solve(line, 4)
}

fun solution2(line: String): Int {
    return solve(line, 14)
}

fun solve(line: String, markerSize: Int): Int {
    return line.windowed(markerSize)
        .indexOfFirst { it.toCharArray().distinct().size == markerSize } + markerSize;
}