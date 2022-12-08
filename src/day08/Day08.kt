package day08

import kotlin.math.max

/*
Part 1: Find out how many trees can be seen from the outside of the grid.

A tree can be seen if there does not exist a taller one in front of it.
If a given tree is visible from left, and its right neighbor is taller, that neighbor is also visible.
If a given tree is visible from top, and its bottom neighbor is taller, that neighbor is also visible.
If a given tree is visible from right, and its left neighbor is taller, that neighbor is also visible.
If a given tree is visible from bottom, and its top neighbor is taller, that neighbor is also visible.

Part 2: Find the best scenic score.

A scenic score is the produce of the viewing distances from the left, top, right, and bottom.
The viewing distance in a given direction is the number of trees that can be seen up to the height of the current tree.
That is, don't count trees further than immediate neighbor that are taller than current tree height.

If a given tree is taller than its left neighbor, its left view distance is its left neighbor's view distance plus one.
If a given tree is taller than its top neighbor, its top view distance is its top neighbor's view distance plus one.
If a given tree is taller than its right neighbor, its right vew distance is its right neighbor's view distance plus one.
If a given tree is taller than its bottom neighbor, its bottom view distance is its bottom neighbor's view distance plus one.

If a given tree height is less than or equal to left neighbor height, its left view distance is 1.
 */

const val LEFT = 0
const val TOP = 1
const val RIGHT = 2
const val BOTTOM = 3

class Day08 {
    val treeGrid = mutableListOf<List<Int>>()
    val visibilityGrid = mutableListOf<ArrayList<String>>()
    val viewDistanceGrid = mutableListOf<ArrayList<ArrayList<Int>>>()

    fun partOne(input: List<String>): Int {
        generateTreeGrid(input)
        var visibleTrees = getEdgeTreeCount()

        for (i in 1 until treeGrid.size - 1) {
            for (j in 1 until treeGrid[0].size - 1) {
                val currHeight = treeGrid[i][j]
                val leftNeighborVisibility = visibilityGrid[i][j - 1]
                val leftNeighborHeight = treeGrid[i][j - 1]
                val topNeighborVisibility = visibilityGrid[i - 1][j]
                val topNeighborHeight = treeGrid[i - 1][j]
                val rightNeighborVisibility = visibilityGrid[i][j + 1]
                val rightNeighborHeight = treeGrid[i][j + 1]
                val bottomNeighborVisibility = visibilityGrid[i + 1][j]
                val bottomNeighborHeight = treeGrid[i + 1][j]

                var foundVisibility = false
                // Try and deduce visibility from the neighbor.
                if (leftNeighborVisibility.contains('l') && currHeight > leftNeighborHeight) {
                    visibilityGrid[i][j] += "l"
                    foundVisibility = true
                }

                if (topNeighborVisibility.contains('t') && currHeight > topNeighborHeight) {
                    visibilityGrid[i][j] += "t"
                    foundVisibility = true
                }

                if (rightNeighborVisibility.contains('r') && currHeight > rightNeighborHeight) {
                    visibilityGrid[i][j] += "r"
                    foundVisibility = true
                }

                if (bottomNeighborVisibility.contains('b') && currHeight > bottomNeighborHeight) {
                    visibilityGrid[i][j] += "b"
                    foundVisibility = true
                }

                // If still don't know, need to brute force and look at all trees in all directions.
                if (!foundVisibility) {
                    if (isVisibleFromLeft(i, j, currHeight)) {
                        visibilityGrid[i][j] += "l"
                    }

                    if (isVisibleFromTop(i, j, currHeight)) {
                        visibilityGrid[i][j] += "t"
                    }

                    if (isVisibleFromRight(i, j, currHeight)) {
                        visibilityGrid[i][j] += "r"
                    }

                    if (isVisibleFromBottom(i, j, currHeight)) {
                        visibilityGrid[i][j] += "b"
                    }
                }
            }
        }

        // Finally, count all tree positions that have at least one visibility.
        for (i in 1 until visibilityGrid.size - 1) {
            for (j in 1 until visibilityGrid[0].size - 1) {
                if (visibilityGrid[i][j].isNotEmpty()) {
                    visibleTrees++
                }
            }
        }

        return visibleTrees
    }

    fun partTwo(input: List<String>): Int {
        generateTreeGrid(input)

        for (i in 0 until treeGrid.size) {
            for (j in 0 until treeGrid.size) {
                val currHeight = treeGrid[i][j]

                // First, handle edges.
                // Top edge
                if (i == 0) {
                    viewDistanceGrid[i][j][TOP] = 0
                }

                // Left edge
                if (j == 0) {
                    viewDistanceGrid[i][j][LEFT] = 0
                }

                // Right edge
                if (j == treeGrid[0].size - 1) {
                    viewDistanceGrid[i][j][RIGHT] = 0
                }

                // Bottom edge
                if (i == treeGrid.size - 1) {
                    viewDistanceGrid[i][j][BOTTOM] = 0
                }

                // Check left neighbors if there is one.
                if (j > 0) {
                    viewDistanceGrid[i][j][LEFT] = getLeftVisibilityDistance(i, j, currHeight)
                }

                // Check top neighbors if there is one.
                if (i > 0) {
                    viewDistanceGrid[i][j][TOP] = getTopVisibilityDistance(i, j, currHeight)
                }

                // Check right neighbors if there are some.
                if (j < treeGrid[0].size - 1) {
                    viewDistanceGrid[i][j][RIGHT] = getRightVisibilityDistance(i, j, currHeight)
                }

                // Check bottom neighbors if there are some.
                if (i < treeGrid.size - 1) {
                    viewDistanceGrid[i][j][BOTTOM] = getBottomVisibilityDistance(i, j, currHeight)
                }

            }
        }

        var maxScenicScore = 0

        viewDistanceGrid.forEach { row ->
            row.forEach { col ->
                val currScenicScore = col.reduce { acc, i -> acc * i }

                if (currScenicScore > maxScenicScore) {
                    maxScenicScore = currScenicScore
                }
            }
        }

        return maxScenicScore
    }

    private fun generateTreeGrid(input: List<String>) {
        input.forEach { row ->
            val newRow = ArrayList<Int>(row.map { it.digitToInt() })
            val newVisibilityRow = ArrayList<String>(row.map { "" })
            val newViewDistanceRow = ArrayList<ArrayList<Int>>( row.map { arrayListOf(0, 0, 0, 0) }) // left, top, right, bottom
            treeGrid.add(newRow)
            visibilityGrid.add(newVisibilityRow) // Start with a grid of empty strings.
            viewDistanceGrid.add(newViewDistanceRow) // Start with all zeros.
        }
    }

    private fun getEdgeTreeCount(): Int {
        return (treeGrid.size * 2) + ((treeGrid[0].size - 2) * 2)
    }

    private fun isVisibleFromLeft(rowIndex: Int, colIndex: Int, height: Int): Boolean {
        val leftTrees = treeGrid[rowIndex].subList(0, colIndex)
        return leftTrees.none { tree -> tree >= height } // No taller trees to the left.
    }

    private fun isVisibleFromTop(rowIndex: Int, colIndex: Int, height: Int): Boolean {
        val topTrees = treeGrid.subList(0, rowIndex).map { it[colIndex] }
        return topTrees.none { tree -> tree >= height } // No taller trees from the top.
    }

    private fun isVisibleFromRight(rowIndex: Int, colIndex: Int, height: Int): Boolean {
        val rightTrees = treeGrid[rowIndex].subList(colIndex + 1, treeGrid[rowIndex].size)
        return rightTrees.none { tree -> tree >= height } // No taller trees to the right.
    }

    private fun isVisibleFromBottom(rowIndex: Int, colIndex: Int, height: Int): Boolean {
        val bottomTrees = treeGrid.subList(rowIndex + 1, treeGrid.size).map { it[colIndex] }
        return bottomTrees.none { tree -> tree >= height } // No taller trees from the bottom.
    }

    private fun getLeftVisibilityDistance(rowIndex: Int, colIndex: Int, height: Int): Int {
        var distance = 0
        val leftTrees = treeGrid[rowIndex].subList(0, colIndex).reversed()

        for (tree in leftTrees) {
            distance++

            if (tree >= height) {
                break
            }
        }

        return distance
    }

    private fun getTopVisibilityDistance(rowIndex: Int, colIndex: Int, height: Int): Int {
        var distance = 0
        val topTrees = treeGrid.subList(0, rowIndex).map { it[colIndex] }.reversed()

        for (tree in topTrees) {
            distance++

            if (tree >= height) {
                break
            }
        }

        return distance
    }

    private fun getRightVisibilityDistance(rowIndex: Int, colIndex: Int, height: Int): Int {
        var distance = 0
        val rightTrees = treeGrid[rowIndex].subList(colIndex + 1, treeGrid[rowIndex].size)

        for (tree in rightTrees) {
            distance++

            if (tree >= height) {
                break
            }
        }

        return distance
    }

    private fun getBottomVisibilityDistance(rowIndex: Int, colIndex: Int, height: Int): Int {
        var distance = 0
        val bottomTrees = treeGrid.subList(rowIndex + 1, treeGrid.size).map { it[colIndex] }

        for (tree in bottomTrees) {
            distance++

            if (tree >= height) {
                break
            }
        }

        return distance
    }
}