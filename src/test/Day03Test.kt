package test

import day03.Day03
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day03Test {
    @Test
    fun partOneTest() {
        val day03 = Day03()
        val input = File("src/day03/input.txt").readLines()
        assertEquals(157, day03.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day03 = Day03()
        val input = File("src/day03/input.txt").readLines()
        assertEquals(70, day03.partTwo(input))
    }
}