package test

import day02.Day02
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day02Test {
    @Test
    fun partOneTest() {
        val day02 = Day02()
        val input = File("src/day02/input.txt").readLines()
        assertEquals(15, day02.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day02 = Day02()
        val input = File("src/day02/input.txt").readLines()
        assertEquals(12, day02.partTwo(input))
    }
}