package test

import day13.Day13
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day13Test {
    @Test
    fun partOneTest() {
        val day13 = Day13()
        val input = File("src/day13/input.txt").readLines()
        assertEquals(13, day13.partOne(input))
    }
}