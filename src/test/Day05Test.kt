package test

import day05.Day05
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day05Test {
    @Test
    fun partOneTest() {
        val day05 = Day05()
        val input = File("src/day05/input.txt").readLines()
        assertEquals("CMZ", day05.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day05 = Day05()
        val input = File("src/day05/input.txt").readLines()
        assertEquals("MCD", day05.partTwo(input))
    }
}