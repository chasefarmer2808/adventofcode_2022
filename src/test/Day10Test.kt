package test

import day10.Day10
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day10Test {
    @Test
    fun partOneTest() {
        val day10 = Day10()
        val input = File("src/day10/input.txt").readLines()
        assertEquals(13140, day10.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day10 = Day10()
        val input = File("src/day10/input.txt").readLines()
        day10.partTwo(input)
    }
}