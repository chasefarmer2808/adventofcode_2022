package test

import day04.Day04
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day04Test {
    @Test
    fun partOneTest() {
        val day04 = Day04()
        val input = File("src/day04/input.txt").readLines()
        assertEquals(2, day04.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day04 = Day04()
        val input = File("src/day04/input.txt").readLines()
        assertEquals(4, day04.partTwo(input))
    }
}