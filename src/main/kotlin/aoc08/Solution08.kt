package aoc08

import java.nio.file.Paths

inline fun <T> Iterable<T>.takeWhileInclusive(
    predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

fun main() {
    val lines = Paths.get("src/main/resources/aoc_08.txt").toFile().useLines { it.toList() }

    val map = lines
        .map { line -> line.toCharArray().map { it.digitToInt() } }
        .map { it.toIntArray() }
        .toTypedArray()

    val width = map.first().size
    val height = map.size

    println(solution1(map, width, height))
    println(solution2(map, width, height))
}

fun solution1(map: Array<IntArray>, width: Int, height: Int): Int {
    val treeMap = solve(map, width, height)
    return treeMap.asSequence().filter { tree ->
        tree.value.any { edge -> edge.all { it < map[tree.key.second][tree.key.first] } }
    }.count() + (width * 2) + (height * 2) - 4
}

fun solution2(map: Array<IntArray>, width: Int, height: Int): Int {
    val treeMap = solve(map, width, height)
    return treeMap.asSequence().map { tree ->
        tree.value.map { edge ->
            edge.takeWhileInclusive { map[tree.key.second][tree.key.first] > it }.toList()
        }.map { it.size }
    }.maxOf {
        it.reduce { result, value -> result.times(value) }
    }
}

fun solve(map: Array<IntArray>, width: Int, height: Int): Map<Pair<Int, Int>, List<List<Int>>> {
    val treeMap = mutableMapOf<Pair<Int, Int>, List<List<Int>>>()

    for (y in 1 until height - 1) {
        for (x in 1 until width - 1) {
            val leftEdgeValues = mutableListOf<Int>()
            val rightEdgeValues = mutableListOf<Int>()
            val topEdgeValues = mutableListOf<Int>()
            val bottomEdgeValues = mutableListOf<Int>()

            for (l in 0 until x) leftEdgeValues.add(map[y][l])
            for (r in x + 1 until width) rightEdgeValues.add(map[y][r])
            for (t in 0 until y) topEdgeValues.add(map[t][x])
            for (b in y + 1 until height) bottomEdgeValues.add(map[b][x])

            treeMap[Pair(x, y)] =
                listOf(leftEdgeValues.reversed(), rightEdgeValues, topEdgeValues.reversed(), bottomEdgeValues)
        }
    }
    return treeMap
}