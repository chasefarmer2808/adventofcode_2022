package day10

val CYCLE_CHECKPOINTS = arrayOf(20, 60, 100, 140, 180, 220)
const val SCREEN_WIDTH = 40
const val SCREEN_HEIGHT = 6
const val TOTAL_SCREEN_SIZE = SCREEN_HEIGHT * SCREEN_WIDTH

class Day10 {
    var clock = 1
    var reg = 1
    var regSum = 0
    val spritePos = arrayListOf(0, 1, 2)
    val screen = (0..SCREEN_HEIGHT).toMutableList().map{(0 until SCREEN_WIDTH).toList().map { "." }.toTypedArray()}

    fun partOne(input: List<String>): Int {
        input.forEach { instruction ->
            val tokens = instruction.split(" ")

            if (tokens[0] == "addx") {
                for (i in 0..1) {
                    if (i == 1) {
                        // Second cycle of add instruction.
                        reg += tokens[1].toInt()
                    } else {
                        clock++
                        updateRegSum()
                    }
                }
            }

            clock++
            updateRegSum()
        }
        return regSum
    }

    fun partTwo(input: List<String>) {
        input.forEach { instruction ->
            val tokens = instruction.split(" ")
            updateScreen()

            if (tokens[0] == "addx") {
                for (i in 0..1) {
                    if (i == 1) {
                        // Second cycle of add instruction.
                        reg += tokens[1].toInt()
                        updateSpritePos()
                    } else {
                        clock++
                        updateScreen()
                    }
                }
            }

            clock++
        }
        printScreen()
    }

    private fun updateRegSum()  {
        if (CYCLE_CHECKPOINTS.contains(clock)) {
            regSum += clock * reg
        }
    }

    private fun updateSpritePos() {
        val currPos = spritePos[1] // Get middle pixel.

        if (reg != currPos) {
            spritePos[0] = reg - 1
            spritePos[1] = reg
            spritePos[2] = reg + 1
        }
    }

    private fun updateScreen() {
        val screenIndex = (clock - 1) % SCREEN_WIDTH
        val screenRow = when (clock) {
            in 1..40 -> 0
            in 41..80 -> 1
            in 81..120 -> 2
            in 121..160 -> 3
            in 161..200 -> 4
            in 201..240 -> 5
            else -> 0
        }

        if (spritePos.contains(screenIndex)) {
            screen[screenRow][screenIndex] = "#"
        }
    }

    private fun printScreen() {
        screen.forEach { row ->
            row.forEach { col ->
                print(col)
            }
            println()
        }
    }
}