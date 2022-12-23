package day18

import kotlin.math.abs
import kotlin.math.sqrt

/*
Part one: get the surface area of cubes that are not touching other cubes.
Input: x, y, z of cubes.
num sides = 6 - num neighbors

Find distance between two cubes:
    sum coords and take difference

Part two: get exterior surface area only.  Don't include surface area for sides touching an air pocket.
Need to find all air pockets.
If a cube is touching an air pocket, subtract one from the exterior sides.
 */

class Cube(val x: Int, val y: Int, val z: Int) {
    val neighbors = arrayListOf<Cube>()
}

class Day18 {
    val cubes = arrayListOf<Cube>()
    fun partOne(input: List<String>): Int {
        parseCoords(input)
        findNeighbors()
        return countSides()
    }

    fun partTwo(input: List<String>): Int {
        parseCoords(input)
        return 0
    }

    fun parseCoords(input: List<String>) {
        input.forEach{ line ->
            val (x, y, z) = line.split(",").map { it.toInt() }
            val newCube = Cube(x, y, z)
            cubes.add(newCube)
        }
    }

    fun findNeighbors() {
        cubes.forEachIndexed { i, cube ->
            val subset = cubes.filterIndexed { index, _ -> index != i }

            subset.forEach { subCube ->
                if (distance(cube, subCube) == 1.0) {
                    cube.neighbors.add(subCube)
                }
            }
        }
    }

    fun distance(cubeA: Cube, cubeB: Cube): Double {
        val xDiff = (cubeB.x - cubeA.x).toDouble()
        val yDiff = (cubeB.y - cubeA.y).toDouble()
        val zDiff = (cubeB.z - cubeA.z).toDouble()

        return sqrt((xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff))
    }

    fun countSides(): Int {
        var sides = 0

        cubes.forEach { cube ->
            sides += (6 - cube.neighbors.size)
        }

        return sides
    }
}