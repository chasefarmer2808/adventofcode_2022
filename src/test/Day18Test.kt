package test

import day18.Day18
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day18Test {
    @Test
    fun partOneTest() {
        val day18 = Day18()
        val input = File("src/day18/input.txt").readLines()
        assertEquals(64, day18.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day18 = Day18()
        val input = File("src/day18/input.txt").readLines()
        assertEquals(58, day18.partTwo(input))
    }
}