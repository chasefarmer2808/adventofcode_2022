package day12

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
    var parentNode: Node? = null
}

class Day12 {
    val topology = arrayListOf<ArrayList<Node>>()
    var startNode: Node? = null
    var endNode: Node? = null
    var lastNode = startNode

    fun partOne(input: List<String>): Int {
        buildTopology(input)

        try {
            return findShortestDistance(startNode!!, endNode!!)
        } catch (e: StackOverflowError) {
            printLocation()
        }

        return 0
    }

    fun findShortestDistance(startNode: Node, endNode: Node): Int {
        lastNode = startNode
        startNode.visited = true
        startNode.id = '*'
        val potentialNodes = startNode.neighbors.filter { node -> !node.visited && abs(node.elevation - startNode.elevation) <= 1 }

        if (potentialNodes.isEmpty()) {
            if (startNode.parentNode == null) {
                return countParents(endNode)
            }
            return findShortestDistance(startNode.parentNode!!, endNode)
        }

        var currMinDistanceNode = potentialNodes.first()

        potentialNodes.forEach { node ->
            node.distance = min(node.distance, abs(node.elevation - startNode.elevation))

            // Keep track of smallest distance node.
            if (node.distance < currMinDistanceNode.distance) {
                currMinDistanceNode = node
            }
        }

        currMinDistanceNode.parentNode = startNode
        return findShortestDistance(currMinDistanceNode, endNode)
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


    private fun countParents(node: Node): Int {
        var count = 0
        var currNode = node

        while (currNode.parentNode !== null) {
            count++
            currNode = currNode.parentNode!!
        }

        return count
    }

    private fun printLocation() {
        topology.forEachIndexed { row, nodes ->
            nodes.forEachIndexed { col, currNode ->
                print(currNode.id)
            }
            println()
        }
        println()
    }
}