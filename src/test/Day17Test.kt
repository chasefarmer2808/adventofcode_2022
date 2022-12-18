package test

import day17.Day17
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day17Test {
    @Test
    fun partOneTest() {
        val day17 = Day17()
        val input = File("src/day17/input.txt").readLines()
        assertEquals(3068, day17.partOne(input.first()))
    }
}