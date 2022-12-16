package day14

import kotlin.math.max
import kotlin.math.min

/*
Part one: How many units of sand come to rest before falling into the abyss?
Sand falls one unit at a time, and moves in the following priority:
down, down and left, down and right.
A new unit of sand does not enter until the current one becomes at rest, or falls into the abyss.
Sand falls from point 500, 0

Start by building the cave given the input.
I don't like how large the X values are.  Let's go through the input and find the smallest X.  Then go back through the
input again and subtract that from all Xs.

Keep track of the current sand unit's location.  Every tick, move in the way described.  If it comes to rest, increment a
counter.  If it falls into the abyss, we are done.

Part two:
Need to increase the number of rows by 1, and make it like  1000 columns wide so we don't go out of bounds.
 */

class Day14 {
    val cave = arrayListOf<ArrayList<Char>>()
    var sandX = 0
    val sandY = 0
    var currSandX = 0
    var currSandY = 1
    var numRows = 0
    var numCols = 1000
    val restingSands = arrayListOf<Pair<Int, Int>>()

    fun partOne(input: List<String>): Int {
        val min = findMinX(input) - 1 // So there is an extra column at index 0.
        sandX = 500 - min
        currSandX = sandX
        buildCave(input, min)
        printCave()
        moveSand()
        return restingSands.size
    }

    fun partTwo(input: List<String>): Int {
        sandX = 500
        currSandX = sandX
        buildCave(input, 0)
        printCave()
        moveSand()
        return restingSands.size
    }

    private fun findMinX(input: List<String>): Int {
        var min = 1000

        input.forEach { line ->
            val points = line.split(" -> ")
            points.forEach { point ->
                val coords = point.split(",").map { it.toInt() }

                if (coords[0] < min) {
                    min = coords[0]
                }
            }
        }

        return min
    }

    private fun buildCave(input: List<String>, min: Int) {
        input.forEach { line ->
            val points = line.split(" -> ")
            val coords = points.map { point ->
                point.split(",").map { it.toInt() }
            }

            var i = 0
            var j = 1

            while (j < coords.size) {
                val firstPoint = coords[i]
                val secondPoint = coords[j]

                drawLine(firstPoint[0], firstPoint[1], secondPoint[0], secondPoint[1])
                i++
                j++
            }
        }

        // Draw floor.
        cave[cave.size - 1] = cave[cave.size - 1].map { '#' } as ArrayList<Char>
    }

    private fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        val maxX = max(x1, x2)
        val maxY = max(y1, y2)

        // First, increase cave size if needed.

        if (maxX > numCols) {
            numCols = maxX
        }

        if (maxY > numRows) {
            numRows = maxY
        }

        while (cave.size <= numRows + 2) { // Adding one so there is an extra row at the bottom.
            cave.add(arrayListOf())
        }

        cave.forEach { row ->
            while (row.size <= numCols + 1) { // Adding one so there is an extra column at the right-most side.
                row.add('.')
            }
        }

        // Next, draw the line.
        if (x1 == x2) {
            // Vertical line.
            cave.forEachIndexed { row, chars ->
                if (row in min(y1, y2)..max(y1, y2)) {
                    chars[x1] = '#'
                }
            }
        } else if (y1 == y2) {
            // Horizontal line.
            cave.forEachIndexed { row, chars ->
                if (row == y1) {
                    chars.forEachIndexed { col, c ->
                        if (col in min(x1, x2)..max(x1, x2)) {
                            cave[row][col] = '#'
                        }
                    }
                }
            }
        }
    }

    private fun printCave() {
        cave.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, char ->
                if (row == sandY && col == sandX) {
                    print('+')
                } else if (row == currSandY && col == currSandX) {
                    print('O')
                } else {
                    print(char)
                }
            }
            println()
        }
        println()
    }

    private fun moveSand() {
        while (true) {
//            printCave()
            // See if we reached abyss.
            if (currSandY == cave.size - 1) {
                break
            }

            // See if we can move down.
            val bottomNeighbor = cave[currSandY + 1][currSandX]
            if (bottomNeighbor == '.') {
                currSandY++
                continue
            }

            // See if we can move down and left.
            if (currSandX > 0) {
                val bottomLeftNeighbor = cave[currSandY + 1][currSandX - 1]
                if (bottomLeftNeighbor == '.') {
                    currSandY++
                    currSandX--
                    continue
                }
            }

            // See if we can move down and right.
            if (currSandX < cave[0].size - 1) {
                val bottomRightNeighbor = cave[currSandY + 1][currSandX + 1]
                if (bottomRightNeighbor == '.') {
                    currSandY++
                    currSandX++
                    continue
                }
            }

            // No where else to move.  We are now at rest.
            cave[currSandY][currSandX] = 'O'
            restingSands.add(Pair(currSandX, currSandY))

            // See if we reached the sand source.
            if (currSandX == sandX && currSandY == sandY) {
                break
            }

            currSandX = sandX
            currSandY = sandY
        }
    }
}