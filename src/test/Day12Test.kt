package test

import day12.Day12
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day12Test {
    @Test
    fun partOneTest() {
        val day12 = Day12()
        val input = File("src/day12/input.txt").readLines()
        assertEquals(31, day12.partOne(input))
    }
}