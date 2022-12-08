package test

import day08.Day08
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day08Test {
    @Test
    fun partOneTest() {
        val day08 = Day08()
        val input = File("src/day08/input.txt").readLines()
        assertEquals(21, day08.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day08 = Day08()
        val input = File("src/day08/input.txt").readLines()
        assertEquals(8, day08.partTwo(input))
    }
}