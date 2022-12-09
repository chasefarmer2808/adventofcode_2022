package day09

import kotlin.math.abs

/*
Part one: Find how many unique places the tail visits as it's pulled along the path.  Movement given as list of commands:
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2

To start, head and tail are on top of each other.
Process R4:
    Head moves right once, tail does not move.
    Head moves right a second time, tail moves right by one.
    Head moves right a third time, tail moves right by one.
    Head moves right a fourth time, tail moves right by one.

if the head and tail aren't touching and aren't in the same row or column, the tail always moves one step diagonally to keep up.
If the distance between head and tail is ever greater than one, need to move tail to follow head.
How do we determine when the tail needs to move diagonally?  Don't need to.  When the tail needs to move,
it will always move to the heads last position.

Make two nodes that have position data.

function to calculate distance between head and tail:
    take abs val of difference between rows and cols.
    if either of these differences is greater than one,  tail needs to move.

Part two: basically need to make a snake game, and keep track of all the positions that the tail has been.  But now, the
rope has 9 knots. H123456789.
The logic is the same as part one, but just need to apply it for all knots.  To do this, need to make a linked list to
represent the rope.

class Knot
    id: Char
    xPos: Int
    yPos: Int
    head: Knot
    tail: Knot
 */

data class Knot(val id: String, var xPos: Int, var yPos: Int, var parent: Knot?, var child: Knot?)

class Day09 {
    private val tailPoses = HashMap<String, Int>()
    private val rope = Knot("H", 0, 0, null, null)
    private var ropeTail: Knot? = null
    private val grid = List(50) { List(50) { "." } }

    fun partOne(input: List<String>): Int {
        buildRope(2)
        storeTailPos()

        input.forEach { command ->
            val (direction, steps) = command.split(" ")
            var stepsCount = steps.toInt()

            while (stepsCount > 0) {
                moveHeadByOne(direction)
                storeTailPos()
                stepsCount--
            }
        }
        return tailPoses.keys.size
    }

    fun partTwo(input: List<String>): Int {
        buildRope(10)
        storeTailPos()

        input.forEach { command ->
            println("Moving head $command")
            val (direction, steps) = command.split(" ")
            var stepsCount = steps.toInt()

            while (stepsCount > 0) {
                moveHeadByOne(direction)
                storeTailPos()
                stepsCount--
//                printGrid()
            }

            println("Tail at x: ${ropeTail!!.xPos}, y: ${ropeTail!!.yPos}")
        }

        return tailPoses.keys.size
    }

    private fun moveHeadByOne(dir: String) {
        when (dir) {
            "L" -> rope.xPos--
            "U" -> rope.yPos++
            "R" -> rope.xPos++
            "D" -> rope.yPos--
        }

        moveTailsIfNeeded()
    }

    private fun moveTailsIfNeeded() {
        var currKnot = rope
        var currChild = currKnot.child

        while (currChild !== null) {
            val xDiff = currKnot.xPos - currChild.xPos
            val yDiff = currKnot.yPos - currChild.yPos

            if (xDiff > 1 && yDiff == 0) {
                // Need to move to the right.
                currChild.xPos++
            } else if ((xDiff > 1 && yDiff >= 1) || (yDiff > 1 && xDiff >= 1)) {
                // Need to move diagonal up and right.
                currChild.xPos++
                currChild.yPos++
            } else if (xDiff == 0 && yDiff > 1) {
                // Need to move up.
                currChild.yPos++
            } else if ((xDiff < -1 && yDiff >= 1) || (yDiff > 1 && xDiff <= -1)) {
                // Need to move diagonal up and left.
                currChild.xPos--
                currChild.yPos++
            } else if (xDiff < -1 && yDiff == 0) {
                // Need to move to the left.
                currChild.xPos--
            } else if ((xDiff < -1 && yDiff <= -1) || (yDiff < -1 && xDiff <= -1)) {
                // Need to move diagonal down and left.
                currChild.xPos--
                currChild.yPos--
            } else if (xDiff == 0 && yDiff < -1) {
                // Need to move down.
                currChild.yPos--
            } else if ((xDiff > 1 && yDiff <= -1) || (yDiff < -1 && xDiff >= 1)) {
                // Need to move diagonal down and right.
                currChild.xPos++
                currChild.yPos--
            }

            currKnot = currChild
            currChild = currKnot.child
        }
    }

    private fun storeTailPos() {
        val tailPos = "${ropeTail!!.xPos},${ropeTail!!.yPos}"
        tailPoses[tailPos] = if (tailPoses.contains(tailPos)) tailPoses[tailPos]!! + 1 else 1
    }

    private fun buildRope(size: Int) {
        var currHead = rope
        var currTail: Knot

        for (i in 1 until size) {
            currTail = Knot(i.toString(), 0, 0, currHead, null)
            currHead.child = currTail
            currHead = currTail
        }

        ropeTail = currHead
    }

    private fun printGrid() {
        grid.forEachIndexed { i, row ->
            row.forEachIndexed { j, col ->
                val knot = getTopKnotOnCurrPosOrNull(j, i)

                if (knot !== null) {
                    print(knot)
                } else {
                    print(col)
                }
            }
            println()
        }
    }

    private fun getTopKnotOnCurrPosOrNull(xPos: Int, yPos: Int): String? {
        var currKnot = rope

        while (currKnot.child !== null) {
            if (currKnot.xPos == xPos && currKnot.yPos == yPos) {
                return currKnot.id
            }

            currKnot = currKnot.child!!
        }

        return null
    }
}