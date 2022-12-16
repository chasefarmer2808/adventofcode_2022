package day15

import kotlin.math.abs
import kotlin.math.min

/*
Part one: Find all positions in a given row where a beacon cannot be present.
For each sensor,
    Recursively search around it until its target beacon is found.
    All locations it encounters are places where a beacon is not.
 */

val xRegex = Regex("x=-?\\d+")
val yRegex = Regex("y=-?\\d+")

class Day15 {
    val sensors = arrayListOf<Pair<Int, Int>>()
    val beacons = arrayListOf<Pair<Int, Int>>()
    val manhattanDists = arrayListOf<Int>()
    val targetRowIntervals = arrayListOf<Pair<Int, Int>>()
    val beaconsOnTargetRow = arrayListOf<Int>()
    val targetRow = 2000000

    fun partOne(input: List<String>): Int {
        parseInput(input)

        sensors.forEachIndexed { index, sensor ->
            manhattanDists.add(dist(sensor, beacons[index]))
        }

        // Get all intervals on the target row.
        sensors.forEachIndexed { i, sensor ->
            val distX = manhattanDists[i] - abs(sensor.second - targetRow)

            // See if the sensor exclusion zone intersects the target row.
            if (distX > 0) {
                val targetPoint = Pair(sensor.first - distX, sensor.first + distX)
                targetRowIntervals.add(targetPoint)
            }
        }

        // Find all beacons on target row.
        beacons.forEach { beacon ->
            if (beacon.second == targetRow) {
                beaconsOnTargetRow.add(beacon.first)
            }
        }

        // Find min and max target row intervals.
        val minX = targetRowIntervals.minOfOrNull { it.first }
        val maxX = targetRowIntervals.maxOfOrNull { it.second }

        var res = 0

        for (x in minX!!..maxX!!) {
            if (beaconsOnTargetRow.contains(x)) {
                continue
            }

            for (interval in targetRowIntervals) {
                if (x in interval.first..interval.second) {
                    res++
                    break
                }
            }
        }

        return res
    }

    fun partTwo(input: List<String>): Long {
        partOne(input)

        // Need to get all positive and negative lines of the exclustion zones.
        val posLines = arrayListOf<Int>()
        val negLines = arrayListOf<Int>()

        sensors.forEachIndexed { i, sensor ->
            val d = manhattanDists[i]
            posLines.addAll(arrayListOf(sensor.first - sensor.second - d, sensor.first - sensor.second + d))
            negLines.addAll(arrayListOf(sensor.first + sensor.second - d, sensor.first + sensor.second + d))
        }

        val N = sensors.size
        var pos = 0
        var neg = 0

        for (i in 0 until 2 * N) {
            for (j in i + 1 until 2 * N) {
                var a =  posLines[i]
                var b = posLines[j]

                if (abs(a - b) == 2) {
                    pos = min(a, b) + 1
                }

                a = negLines[i]
                b = negLines[j]

                if (abs(a - b) == 2) {
                    neg = min(a, b) + 1
                }
            }
        }

        val x = (pos + neg) / 2
        val y = (neg - pos) / 2

        return (x.toLong() * 4000000L) + y.toLong()
    }

    private fun parseInput(input: List<String>) {
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

            sensors.add(Pair(xVals[0], yVals[0]))
            beacons.add(Pair(xVals[1], yVals[1]))
        }

        println(sensors)
        println(beacons)
    }

    private fun dist(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
        return abs(p1.first - p2.first) + abs(p1.second - p2.second)
    }
}