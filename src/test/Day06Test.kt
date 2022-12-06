package test

import day06.Day06
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day06Test {
    @Test
    fun partOneTest() {
        val day06 = Day06()
        val input = File("src/day06/input.txt").readLines()
        assertEquals(7, day06.partOne(input[0]))
    }

    @Test
    fun partTwoTest() {
        val day06 = Day06()
        val input = File("src/day06/input.txt").readLines()
        assertEquals(19, day06.partTwo(input[0]))
    }
}