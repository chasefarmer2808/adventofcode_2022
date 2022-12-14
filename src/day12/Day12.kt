package day12

import java.util.PriorityQueue
import kotlin.math.abs
import kotlin.math.min

/*
Part one: Find path from S to E with least amount of steps.
Can only move with a difference of one letter.  So cost must be one.
 */

const val START_CHAR = 'S'
const val END_CHAR = 'E'

data class Node(var id: Char, val elevation: Int, val row: Int, val col: Int) {
    val neighbors = arrayListOf<Node>()
    var distance = Int.MAX_VALUE
    var visited = false
    var steps = 0
}

class Day12 {
    val topology = arrayListOf<ArrayList<Node>>()
    var startNode: Node? = null
    var endNode: Node? = null

    val compareByDistance: Comparator<Node> = compareBy { it.distance }
    val pathQueue = PriorityQueue(compareByDistance)

    fun partOne(input: List<String>): Int {
        buildTopology(input)

        try {
            pathQueue.add(startNode)
            return dijkstras()
        } catch (e: StackOverflowError) {
            printLocation()
        }

        return 0
    }

    fun partTwo(input: List<String>): Int {
        buildTopology(input)

        try {
            endNode!!.distance = 0
            pathQueue.add(endNode)
            return dijkstrasReverse()
        } catch (e: StackOverflowError) {
            printLocation()
        }

        return 0
    }

    fun dijkstras(): Int {
        while (true) {
            val currNode = pathQueue.remove()

            if (currNode.visited) continue

            currNode.visited = true

            if (currNode.id == 'E') {
                printLocation()
                return currNode.steps
            }

            currNode.neighbors.filter { node -> node.elevation <= currNode.elevation + 1 }.forEach { neighbor ->
                neighbor.steps = currNode.steps + 1
                neighbor.distance = currNode.distance + 1
                pathQueue.add(neighbor)
            }
        }
    }

    fun dijkstrasReverse(): Int {
        while (true) {
            val currNode = pathQueue.remove()

            if (currNode.visited) continue

            currNode.visited = true

            if (currNode.id == 'a') {
                printLocation()
                return currNode.steps
            }

            currNode.neighbors.filter { node -> node.elevation >= currNode.elevation - 1 }.forEach { neighbor ->
                neighbor.steps = currNode.steps + 1
                neighbor.distance = currNode.distance + 1
                pathQueue.add(neighbor)
            }
        }
    }

    private fun buildTopology(input: List<String>) {
        input.forEachIndexed { row, line ->
            val charRow = arrayListOf<Node>()
            line.forEachIndexed { col, c ->
                val elevation = when (c) {
                    'S' -> 0
                    'E' -> 25
                    else -> c.toInt() - 97
                }
                val newNode = Node(c, elevation, row, col)

                // Check for start or end node.
                if (c == START_CHAR) {
                    newNode.distance = 0
                    startNode = newNode
                }

                if (c == END_CHAR) {
                    endNode = newNode
                }

                charRow.add(newNode)
            }
            topology.add(charRow)
        }

        initNodeNeighbors()
    }

    private fun initNodeNeighbors() {
        topology.forEachIndexed { row, nodes ->
            nodes.forEachIndexed { col, node ->
                if (row > 0) {
                    // Get top neighbor.
                    node.neighbors.add(topology[row - 1][col])
                }

                if (col < topology[0].size - 1) {
                    // Get right neighbor.
                    node.neighbors.add(topology[row][col + 1])
                }

                if (row < topology.size - 1) {
                    // Get bottom neighbor.
                    node.neighbors.add(topology[row + 1][col])
                }

                if (col > 0) {
                    // Get left neighbor.
                    node.neighbors.add(topology[row][col - 1])
                }
            }
        }
    }

    private fun printLocation() {
        topology.forEachIndexed { row, nodes ->
            nodes.forEachIndexed { col, currNode ->
                if (currNode.visited) {
                    print("*")
                } else {
                    print(currNode.id)
                }
            }
            println()
        }
        println()
    }
}