package test

import day11.Day11
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun partOneTest() {
        val day11 = Day11()
        assertEquals(10605, day11.partOne())
    }

    @Test
    fun partTwoTest() {
        val day11 = Day11()
        assertEquals(2713310158, day11.partTwo())
    }
}