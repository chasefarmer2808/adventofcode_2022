package day02

/*
A -> rock
B -> paper
C -> scissors

X -> rock
Y -> paper
Z -> scissors

Make a map, where the key is what option was chosen, and the value is the option that it beats.
Ex: map["X"] would return "C".

Also need a score map that returns the points for the option I chose.
Ex: scoreMap["Y"] would return 2.

Get desired play given what elf played and desired outcome:

 */

class Day02 {
    private val gameMap = mapOf('X' to 'C', 'Y' to 'A', 'Z' to 'B')
    private val scoreMap = mapOf('X' to 1, 'Y' to 2, 'Z' to 3)
    private val elfMap = mapOf('X' to 'A', 'Y' to 'B', 'Z' to 'C')
    private val stratMap = mapOf('X' to 0, 'Y' to 3, 'Z' to 6)
    private val winMap = mapOf('A' to 'Y', 'B' to 'Z', 'C' to 'X')
    private val loseMap = mapOf('A' to 'Z', 'B' to 'X', 'C' to 'Y')
    private val drawMap = mapOf('A' to 'X', 'B' to 'Y', 'C' to 'Z')

    fun partOne(input: List<String>): Int {
        var score = 0

        input.forEach { round ->
            val (elfPlay, myPlay) = round.split(' ').map{ it[0] }

            // Add my play to the score no matter what.
            score += scoreMap[myPlay]!!

            // Find out if I won the round.
            if (gameMap[myPlay] == elfPlay) {
                // I won
                score += 6
            } else if (elfMap[myPlay] == elfPlay) {
                // Draw
                score += 3
            }
        }
        println(score)
        return score
    }

    fun partTwo(input: List<String>): Int {
        var score = 0

        input.forEach { round ->
            val (elfPlay, strat) = round.split(' ').map{ it[0] }

            // Add the desired outcome to the score.
            score += stratMap[strat]!!

            // Now, find out which play I should have.
            score += when (strat) {
                'X' -> scoreMap[loseMap[elfPlay]]
                'Y' -> scoreMap[drawMap[elfPlay]]
                'Z' -> scoreMap[winMap[elfPlay]]
                else -> 0
            }!!

        }
        println(score)
        return score
    }
}