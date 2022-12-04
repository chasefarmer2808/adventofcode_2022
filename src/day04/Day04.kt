package day04

/*
Part 1: Find how many assignments, where one pair fully contains the other pair.

Part 2: How many pairs overlap at all?
Get the min and max of a pair.  Of the min or the max is within the range of the other
pair, then there is overlap.
 */

class Day04 {
    fun partOne(input: List<String>): Int {
        var containsCount = 0

        input.forEach { assignment ->
            val pairs = extractPairs(assignment)

            if (pairs[0][0] <= pairs[1][0] && pairs[0][1] >= pairs[1][1] ||
                pairs[1][0] <= pairs[0][0] && pairs[1][1] >= pairs[0][1]) {
                containsCount++
            }
        }

        return containsCount
    }

    fun partTwo(input: List<String>): Int {
        var overlapsCount = 0

        input.forEach { assignment ->
            val pairs = extractPairs(assignment)

            if (pairs[0][0] in pairs[1][0]..pairs[1][1] ||
                pairs[0][1] in pairs[1][0]..pairs[1][1] ||
                pairs[1][0] in pairs[0][0]..pairs[0][1] ||
                pairs[1][1] in pairs[0][0]..pairs[0][1]) {
                overlapsCount++
            }
        }

        return overlapsCount
    }

    private fun extractPairs(assignment: String): List<List<Int>> {
        return assignment
            .split(',')
            .map { pair -> pair.split('-')
                .map { it.toInt() } }
    }
}