package aoc03

import java.nio.file.Paths

val valueMap = calculateValueMap();

fun calculateValueMap(): Map<Char, Int> {
    val tmpMap = mutableMapOf<Char, Int>()
    var c = 'a';
    while (c <= 'z') {
        tmpMap[c] = c.code - 96;
        c++
    }

    c = 'A'
    while (c <= 'Z') {
        tmpMap[c] = c.code - 38;
        c++
    }
    return tmpMap;
}

fun main() {
    val lines = Paths.get("src/main/resources/aoc_03.txt").toFile().useLines { it.toList() }

    println(solution1(lines))
    println(solution2(lines))
}

fun solution1(lines: List<String>): Int {
    return lines.map {
        Pair(it.substring(0, it.length / 2), it.substring(it.length / 2))
    }.map { pair ->
        pair.first.toCharArray().first {
            pair.second.contains(it)
        }
    }.sumOf {
        valueMap[it]!!
    }
}

fun solution2(lines: List<String>): Int {
    return lines
        .windowed(3, 3)
        .map { group ->
            val charMap = mutableMapOf<Char, Int>()
            group.forEach { line ->
                line.toCharArray()
                    .distinct()
                    .forEach {
                        charMap.computeIfPresent(it) { _, v -> v + 1 }
                        charMap.putIfAbsent(it, 1)
                    }
            }
            charMap.maxBy { it.value }.key
        }.sumOf {
            valueMap[it]!!
        }
}