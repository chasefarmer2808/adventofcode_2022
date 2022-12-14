package day13

import java.util.Stack

/*
Part one: Find packets that are in the right order.
Packets are in the right order when:
    If both values are integers, and the left integer is lower in value.
    If both values are lists, and the left list runs out first (aka smaller in size).
    If the types are mixed, convert the integer to a list of size one, and follow the rules above.

Packets will always be given as two lists.

Need to recursively go through each item in the list.
    if both items are ints, compare their values.
        if they do not equal, we are done.
        if one list becomes empty, we are done. (make sure to remove items as they are compared)
    if both items are lists
        recurse and pass in both lists
    if one is
 */

class Day13 {
    val pairs = arrayListOf<Pair<ArrayList<Any>, ArrayList<Any>>>()

    fun partOne(input: List<String>): Int {
        buildPairs(input)
        return getOrderedPairs()
    }

    private fun buildPairs(input: List<String>) {
        input.filter { line -> line.isNotEmpty() }.chunked(2).forEach { pair ->
            val leftArr = parseArrayStr(pair[0])
            val rightArr = parseArrayStr(pair[1])
            val newPair = Pair(leftArr, rightArr)
            pairs.add(newPair)
        }
    }

    private fun parseArrayStr(str: String): ArrayList<Any> {
        val res = arrayListOf<Any>()

        // Remove first bracket.
        val strippedStr = str.substring(1, str.length)
        var i = 0

        while (i < strippedStr.length) {
            val char = strippedStr[i]
            if (char.toString().toIntOrNull() !== null) {
                res.add(char.toString().toInt())
            } else if (char == '[') {
                res.add(parseArrayStr(strippedStr.substring(i, strippedStr.length)))

                while (strippedStr[i] !== ']') {
                    i++
                }
            } else if (char == ']') {
                return res
            }
            i++
        }

        throw Exception("Input string array not balanced.")
    }

    private fun getOrderedPairs(): Int {
        var sum = 0

        pairs.forEachIndexed { i, pair ->
            val isOrdered = compare(pair.first, pair.second)
            if (isOrdered == true) {
                sum += (i + 1)
            }
        }

        return sum
    }

    private fun compare(left: Any, right: Any): Boolean? {
        var leftItem = left
        var rightItem = right

        if (leftItem is List<*> && rightItem is Int) {
            rightItem = arrayListOf(rightItem)
        }

        if (leftItem is Int && rightItem is List<*>) {
            leftItem = arrayListOf(leftItem)
        }

        if (leftItem is Int && rightItem is Int) {
            if (leftItem < rightItem) {
                return true
            }

            if (leftItem == rightItem) {
                return null
            }

            return false
        }

        if (leftItem is List<*> && rightItem is List<*>) {
            var i = 0

            while (i < leftItem.size && i < rightItem.size) {
                val res = compare(leftItem[i]!!, rightItem[i]!!)

                if (res != null) {
                    return res
                }

                i++
            }

            if (i == leftItem.size) {
                if (leftItem.size == rightItem.size) {
                    return null
                }
                return true // left ended first
            }

            if (i == rightItem.size) {
                return false // right ended first
            }
        }

        return null
    }
}