package test

import day15.Day15
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day15Test {
    @Test
    fun partOneTest() {
        val day15 = Day15()
        val input = File("src/day15/input.txt").readLines()
        assertEquals(26, day15.partOne(input))
    }
}