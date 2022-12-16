package day15

/*
Part one: Find all positions in a given row where a beacon cannot be present.
For each sensor,
    Recursively search around it until its target beacon is found.
    All locations it encounters are places where a beacon is not.
 */

val xRegex = Regex("x=-?\\d+")
val yRegex = Regex("y=-?\\d+")

data class Node(val id: String, val row: Int, val col: Int) {
    val neighbors = arrayListOf<Node>()
    var visited = false
}

class Day15 {
    val grid = arrayListOf<ArrayList<Node>>()
    val sensors = arrayListOf<Node>()

    fun partOne(input: List<String>): Int {
        buildGrid(input)
        return 0
    }

    private fun buildGrid(input: List<String>) {
        input.forEach { line ->
            val xRes = xRegex.findAll(line)
            val yRes = yRegex.findAll(line)
            val xVals = arrayListOf<Int>()
            val yVals = arrayListOf<Int>()

            xRes.forEach { xStr ->
                xVals.add(xStr.value.split("=").last().toInt())
            }

            yRes.forEach { yStr ->
                yVals.add(yStr.value.split("=").last().toInt())
            }

            println(xVals)
            println(yVals)
        }
    }
}