package day06

/*
Part one: Find how many characters have passed in the sequence until we get a subsequence of four characters
that are all different.

Could use a sliding window method that checks four chars at a time.  For each subset, I can convert it to a char set.
If the char set size is the same as the original subset array, I'm done.  If it's not, increment the count and move each
end of the window.
 */

class Day06 {
    fun partOne(input: String): Int {
        return findFirstUniqueSubset(input, 4)
    }

    fun partTwo(input: String): Int {
        return findFirstUniqueSubset(input, 14)
    }

    private fun findFirstUniqueSubset(input: String, size: Int): Int {
        var startIndex = 0
        var endIndex = size - 1

        while (endIndex < input.length) {
            val subChars = input.subSequence(startIndex, endIndex + 1).toSet()
            println(subChars)
            if (subChars.size == size) {
                break
            }

            startIndex++
            endIndex++
        }

        return endIndex + 1
    }
}