package day17

import kotlin.math.min

/*
Grid will be 8 units wide and 5000 units deep.
 */

const val WIDTH = 9L
const val HEIGHT = 100000L
const val NUM_ROCKS = 1000000000000L
const val NUM_ROCK_TYPES = 5

abstract class Rock {
    abstract val nodes: ArrayList<ArrayList<Long>>
    var isAtRest = false

    abstract fun getTopHeight(): Long

    fun moveRight() {
        nodes.forEach { node -> node[0]++ }
    }

    fun moveLeft() {
        nodes.forEach { node -> node[0]-- }
    }

    fun moveDown() {
        nodes.forEach { node -> node[1]++ }
    }

    fun getNextPos(dir: Char?): List<List<Long>> {
        val pos = arrayListOf<ArrayList<Long>>()

        when (dir) {
            '>' -> {
                nodes.forEach { node ->
                    val newNode = arrayListOf(node[0] + 1, node[1])
                    pos.add(newNode)
                }
            }
            '<' -> {
                nodes.forEach { node ->
                    val newNode = arrayListOf(node[0] - 1, node[1])
                    pos.add(newNode)
                }
            }
            else -> {
                // Down
                nodes.forEach { node ->
                    val newNode = arrayListOf(node[0], node[1] + 1)
                    pos.add(newNode)
                }
            }
        }

        return pos
    }

    fun asSet(): Set<Pair<Long, Long>> {
        val set = mutableSetOf<Pair<Long, Long>>()
        nodes.forEach { node -> set.add(Pair(node[0], node[1])) }
        return set
    }
}

class FlatRock(towerHeight: Long): Rock() {
    override val nodes = arrayListOf(
        arrayListOf(3, towerHeight - 4),
        arrayListOf(4, towerHeight - 4),
        arrayListOf(5, towerHeight - 4),
        arrayListOf(6, towerHeight - 4)
    )

    override fun getTopHeight(): Long {
        return nodes[0][1]
    }
}

class PlusRock(towerHeight: Long): Rock() {
    override val nodes = arrayListOf(
        arrayListOf(4, towerHeight - 5), // center
        arrayListOf(3, towerHeight - 5), // left
        arrayListOf(4, towerHeight - 6), // top
        arrayListOf(5, towerHeight - 5), // right
        arrayListOf(4, towerHeight - 4)  // bottom
    )

    override fun getTopHeight(): Long {
        return nodes[2][1]
    }
}

class LRock(towerHeight: Long): Rock() {
    override val nodes = arrayListOf(
        arrayListOf(5, towerHeight - 6), // top
        arrayListOf(5, towerHeight - 5),
        arrayListOf(5, towerHeight - 4), // corner
        arrayListOf(4, towerHeight - 4),
        arrayListOf(3, towerHeight - 4)  // left
    )

    override fun getTopHeight(): Long {
        return nodes[0][1]
    }
}

class StraightRock(towerHeight: Long): Rock() {
    override val nodes = arrayListOf(
        arrayListOf(3, towerHeight - 7), // top
        arrayListOf(3, towerHeight - 6),
        arrayListOf(3, towerHeight - 5),
        arrayListOf(3, towerHeight - 4)  // bottom
    )

    override fun getTopHeight(): Long {
        return nodes[0][1]
    }
}

class SquareRock(towerHeight: Long): Rock() {
    override val nodes = arrayListOf(
        arrayListOf(3, towerHeight - 5), // top left
        arrayListOf(4, towerHeight - 5), // top right
        arrayListOf(3, towerHeight - 4), // bottom right
        arrayListOf(4, towerHeight - 4)  // bottom left
    )

    override fun getTopHeight(): Long {
        return nodes[0][1]
    }
}

class Day17 {
    val atRestNodes = mutableSetOf<Pair<Long, Long>>()

    fun partOne(input: String): Long {
        buildWallsAndFloor()

        var towerHeight = Long.MAX_VALUE
        var rockCount = 1
        var rockNum = 0
        var dirIndex = 0
        var currRock: Rock = FlatRock(HEIGHT - 1)
        var currDir: Char? = null

        while (rockCount <= NUM_ROCKS) {
//            printGrid(currRock)
            // First, check if we are at rest.
            if (currRock.isAtRest) {
                // Add currRock nodes to at rest nodes.
                atRestNodes.addAll(currRock.nodes.map { node -> Pair(node[0], node[1]) })

                // Update tower height if needed.
                towerHeight = min(towerHeight, currRock.getTopHeight())

                // Get the next rock
                rockNum = (rockNum + 1) % NUM_ROCK_TYPES
                currRock = getNextRock(rockNum, towerHeight)

                rockCount++
                continue
            }

            currDir = getNextDir(input, dirIndex, currDir)
            val nextPos = currRock.getNextPos(currDir)

            when (currDir) {
                '>' -> {
                    if (canMove(nextPos)) {
                        currRock.moveRight()
                    }
                    dirIndex++
                }
                '<' -> {
                    if (canMove(nextPos)) {
                        currRock.moveLeft()
                    }
                    dirIndex++
                }
                else -> {
                    if (canMove(nextPos)) {
                        currRock.moveDown()
                    } else {
                        // Rock is now at rest.
                        currRock.isAtRest = true
                    }
                }
            }

            // Reset dir index if gone through all dirs.
            if (dirIndex % input.length == 0) {
                dirIndex = 0
            }
        }
        printGrid(currRock)
        return HEIGHT - (towerHeight + 1)
    }

    private fun buildWallsAndFloor() {
        // Build walls.
        for (row in 0 until HEIGHT) {
            atRestNodes.add(Pair(0, row)) // left wall.
            atRestNodes.add(Pair(WIDTH - 1, row)) // right wall
        }

        // Build floor.
        for (col in 0  until WIDTH) {
            atRestNodes.add(Pair(col, HEIGHT - 1))
        }
    }

    private fun getNextDir(input: String, dirIndex: Int, lastDir: Char?): Char? {
        if (lastDir == null) {
            // Last dir was down.
            return input[dirIndex]
        }

        // Else, dir was not down.
        return null
    }

    private fun getNextRock(rockTypeNum: Int, towerHeight: Long): Rock {
        return when (rockTypeNum) {
            0 -> FlatRock(towerHeight)
            1 -> PlusRock(towerHeight)
            2 -> LRock(towerHeight)
            3 -> StraightRock(towerHeight)
            4 -> SquareRock(towerHeight)
            else -> throw Exception("Invalid rock type")
        }
    }

    private fun canMove(nextPos: List<List<Long>>): Boolean {
        val pairs = nextPos.map { pos -> Pair(pos[0], pos[1]) }

        for (pair in pairs) {
            if (atRestNodes.contains(pair)) {
                return false
            }
        }

        return true
    }

    private fun printGrid(currRock: Rock) {
        for (row in 0 until HEIGHT) {
            for (col in 0 until WIDTH) {
                val point = Pair(col, row)

                if (atRestNodes.contains(point)) {
                    print("#")
                } else if (currRock.asSet().contains(point)) {
                    print('@')
                } else {
                    print(".")
                }
            }
            println()
        }
        println()
    }
}