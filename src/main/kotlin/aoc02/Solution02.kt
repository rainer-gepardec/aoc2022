package aoc02

import java.nio.file.Paths

// X rock 1, Y paper 2, Z scissors 3
// A rock -, B paper -, C scissors -

val playMap = mapOf(
    "X" to 1, "Y" to 2, "Z" to 3
);

val expectedResultMap = mapOf(
    "X" to 0, "Y" to 3, "Z" to 6
)

val resultMap = mapOf(
"A X" to 3, "A Y" to 6, "A Z" to 0,
"B X" to 0, "B Y" to 3, "B Z" to 6,
"C X" to 6, "C Y" to 0, "C Z" to 3
)

fun main() {
    val lines = Paths.get("src/main/resources/aoc_02.txt").toFile().useLines { it.toList() }

    println(solution1(lines))
    println(solution2(lines))
}

fun solution1(plays: List<String>): Int? {
    return solve(plays)
}

fun solution2(plays: List<String>): Int? {
    val expectedPlays = plays.map { it ->
        val opponentPlay = it.take(1);
        val expectedResult = expectedResultMap[it.takeLast(1)]
        resultMap.filter { it.key.contains(opponentPlay) && it.value == expectedResult }.keys.single()
    }

    return solve(expectedPlays)
}

fun solve(lines: List<String>): Int? {
    return lines.sumOf { playMap[it.takeLast(1)]!! + resultMap[it]!! }
}