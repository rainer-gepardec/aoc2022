package aoc07

import java.nio.file.Paths
import kotlin.math.abs


class Node(val parent: Node?, val name: String, val type: String, val size: Int = 0) {
    val children = mutableListOf<Node>()

    override fun toString(): String {
        return "$name ($type)"
    }
}
fun main() {
    val lines = Paths.get("src/main/resources/aoc_07.txt").toFile().useLines { it.toList() }

    println(solution1(lines))
    println(solution2(lines))
}

fun solution1(lines: List<String>): Int {
    return solve(lines).filter { it <= 100000 }.sum()
}

fun solution2(lines: List<String>): Int {
    val fileList = solve(lines)

    val maxSpace = 70000000
    val installSpace = 30000000
    val usedSpace = fileList.max()
    val freeSpace = maxSpace - usedSpace
    val requiredSpace = installSpace - freeSpace

    return fileList.filter { requiredSpace - it < 0 }.maxBy { requiredSpace - it }
}
fun solve(lines: List<String>): List<Int> {
    val shellLines = lines
        .filterNot { it.contains("$ ls") }
        .map { it.split(" ") }
        .map { if (it.size == 2)  Pair(it[0], it[1]) else Pair(it[1], it[2]) }

    val rootNode = Node(null, "/", "dir")
    var currentNode: Node? = null

    for (shellLine in shellLines) {
        if (shellLine.first == "cd") {
            currentNode = if (shellLine.second == "..") {
                currentNode?.parent
            } else {
                if (currentNode == null) {
                    rootNode
                } else {
                    currentNode.children.add(Node(currentNode, currentNode.name + "/" + shellLine.second, "dir"))
                    currentNode.children.last()
                }
            }
        } else if (shellLine.first.toIntOrNull() !== null) {
            currentNode?.children?.add(Node(currentNode, shellLine.second, "file", shellLine.first.toInt()))
        }
    }

    val sizeMap = mutableMapOf<String, Int>()
    calculate(rootNode, sizeMap)
    return sizeMap.toList().map { it.second }.sortedDescending()
}

fun calculate(node: Node, sizeMap: MutableMap<String, Int>): Int {
    return if (node.type == "dir") {
        val size =  node.children.filter { it.type == "dir" }.sumOf { calculate(it, sizeMap) } + calcFileSize(node)
        sizeMap[node.name] = size;
        size
    } else {
        calcFileSize(node)
    }
}

fun calcFileSize(node: Node): Int {
    return node.children.filter { it.type == "file" }.sumOf { it.size }
}