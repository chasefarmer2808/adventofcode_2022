package day03

/*
Part One
We don't care how many times an item type occurs in both compartments, just that it does or now.
Turn each compartment into a set of chars.  This will eliminate dups and let us take intersection
of each compartment.
Save dup item types to a list, and sum them up by adding their ascii values.
While summing, if the char is between 97 and 122, subtract 96 from it before adding.
If the char is between 65 and 90, subtract 38 from it before adding.
 */

class Day03 {
    fun partOne(input: List<String>): Int {
        var priority = 0

        input.forEach { rucksack ->
            val (firstHalfSet, secondHalfSet) = splitRucksack(rucksack).map { half -> half.toCharArray().toSet() }
            val dups = firstHalfSet intersect secondHalfSet
            priority += calculatePriority(dups)
        }

        return priority
    }

    fun partTwo(input: List<String>): Int {
        var priority = 0
        val groups = mutableListOf<Set<Char>>()

        input.forEach { rucksack ->
            groups.add(rucksack.toCharArray().toSet())

            if (groups.size == 3) {
                // Ready to get priority of the group.
                val commonTypes = groups.reduce { acc, set -> acc intersect set }
                priority += calculatePriority(commonTypes)
                groups.clear()
            }
        }

        return priority
    }

    private fun splitRucksack(items: String): List<String> {
        val halfWay = items.length / 2
        val firstHalf = items.take(halfWay)
        val secondHalf = items.takeLast(halfWay )
        return listOf(firstHalf, secondHalf)
    }

    private fun calculatePriority(dups: Set<Char>): Int {
        var priority = 0

        dups.forEach { dup ->
            priority += if (dup.isLowerCase()) {
                dup.code - 96
            } else {
                dup.code - 38
            }
        }

        return priority
    }
}