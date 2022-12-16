package test

import day14.Day14
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day14Test {
    @Test
    fun partOneTest() {
        val day14 = Day14()
        val input = File("src/day14/input.txt").readLines()
        assertEquals(24, day14.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day14 = Day14()
        val input = File("src/day14/input.txt").readLines()
        assertEquals(93, day14.partTwo(input))
    }
}