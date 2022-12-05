package aoc04

import java.nio.file.Paths
import kotlin.time.Duration.Companion.seconds

fun main() {
    val lines = Paths.get("src/main/resources/aoc_04.txt").toFile().useLines { it.toList() }

    println(solution1(lines))
    println(solution2(lines))
}

fun solution1(lines: List<String>): Int {
    return lines
        .map { it.split(",") }
        .map {
            Pair(fill(it[0]), fill(it[1]))
        }
        .count { items ->
            items.first.all { it in items.second } || items.second.all { it in items.first }
        }
}

fun solution2(lines: List<String>): Int {
    return lines
        .map { it.split(",") }
        .map {
            Pair(fill(it[0]), fill(it[1]))
        }
        .count { items ->
            items.first.any { it in items.second }
        }
}

fun fill(worktime: String): List<Int> {
    val split = worktime.split("-")
    return IntRange(split[0].toInt(), split[1].toInt()).toList()
}