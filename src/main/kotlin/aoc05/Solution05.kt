package aoc05

import java.nio.file.Paths

/*

    [D]
[N] [C]
[Z] [M] [P]
 1   2   3


            [J] [Z] [G]
            [Z] [T] [S] [P] [R]
[R]         [Q] [V] [B] [G] [J]
[W] [W]     [N] [L] [V] [W] [C]
[F] [Q]     [T] [G] [C] [T] [T] [W]
[H] [D] [W] [W] [H] [T] [R] [M] [B]
[T] [G] [T] [R] [B] [P] [B] [G] [G]
[S] [S] [B] [D] [F] [L] [Z] [N] [L]
 1   2   3   4   5   6   7   8   9
 */

fun createStorage(): Map<Int, ArrayDeque<String>> {
    val storage = mutableMapOf<Int, ArrayDeque<String>>()
    storage[1] = ArrayDeque(listOf("S", "T","H","F","W","R"))
    storage[2] = ArrayDeque(listOf("S", "G", "D","Q","W"))
    storage[3] = ArrayDeque(listOf("B","T","W"))
    storage[4] = ArrayDeque(listOf("D","R","W","T","N","Q","Z","J"))
    storage[5] = ArrayDeque(listOf("F","B","H","G","L","V","T","Z"))
    storage[6] = ArrayDeque(listOf("L","P","T","C","V","B","S","G"))
    storage[7] = ArrayDeque(listOf("Z","B","R","T","W","G","P"))
    storage[8] = ArrayDeque(listOf("N","G","M","T","C","J","R"))
    storage[9] = ArrayDeque(listOf("L","G","B","W"))
    return storage
}


fun main() {
    val lines = Paths.get("src/main/resources/aoc_05.txt").toFile().useLines { it.toList() }

    val operations = lines.map { it.split(" ") }
        .map { tokens ->
            tokens.map { token -> token.toIntOrNull() }.mapNotNull { it }
        }
        .map {
            Triple(it[0], it[1], it[2])
        }

    println(solution1(createStorage(), operations))
    println(solution2(createStorage(), operations))
}

fun solution1(storage: Map<Int, ArrayDeque<String>>, operations: List<Triple<Int, Int, Int>>): String {
    operations.forEach {
        for (i in 0 until it.first) storage[it.third]!!.addLast(storage[it.second]!!.removeLast())
    }

    return storage.map {it.value.removeLast() }.joinToString(separator = "")
}

fun solution2(storage: Map<Int, ArrayDeque<String>>, operations: List<Triple<Int, Int, Int>>): String {
    operations.forEach { movement ->
        val tmpDeque = ArrayDeque<String>();

        for (i in 0 until movement.first) {
            tmpDeque.addFirst(storage[movement.second]!!.removeLast());
        }

        tmpDeque.forEach {
            storage[movement.third]!!.addLast(it)
        }
    }

    return storage.map {it.value.removeLast() }.joinToString(separator = "")
}