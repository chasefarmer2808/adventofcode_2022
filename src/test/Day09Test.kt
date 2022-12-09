package test

import day09.Day09
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day09Test {
    @Test
    fun partOneTest() {
        val day09 = Day09()
        val input = File("src/day09/input.txt").readLines()
        assertEquals(13, day09.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day09 = Day09()
        val input = File("src/day09/input.txt").readLines()
        assertEquals(36, day09.partTwo(input))
    }
}