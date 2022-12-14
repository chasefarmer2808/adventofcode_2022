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
            val isOrdered = isOrderedNew(pair.first, pair.second)
            if (isOrdered == true) {
                sum += (i + 1)
            }
        }

        return sum
    }

    private fun isOrderedNew(left: List<Any?>, right: List<Any?>): Boolean? {
        val currLeft = left.toMutableList()
        val currRight = right.toMutableList()

        while (currLeft.size < currRight.size) {
            currLeft.add(null)
        }

        while (currRight.size < currLeft.size) {
            currRight.add(null)
        }

        var res: Boolean? = null

        if (currRight.isEmpty() && currLeft.isNotEmpty()) {
            res = false
        }

        if (currLeft.isEmpty() && currRight.isNotEmpty()) {
            res = true
        }

        if (res !== null) {
            return res
        }

        if (currLeft.isEmpty() && currRight.isEmpty()) {
            return null
        }

        for ((leftItem, rightItem) in currLeft zip currRight) {
            if (leftItem == null) {
                return true
            }

            if (rightItem == null) {
                return false
            }

            if (leftItem is Int && rightItem is Int) {
                if (leftItem == rightItem) {
                    continue
                }

                return leftItem < rightItem
            }

            if (leftItem is List<*> && rightItem is Int) {
                res = isOrderedNew(leftItem as List<Any>, arrayListOf(rightItem))
            }

            if (leftItem is Int && rightItem is List<*>) {
                res = isOrderedNew(arrayListOf(leftItem), rightItem as List<Any>)
            }

            if (leftItem is List<*> && rightItem is List<*>) {
                res = isOrderedNew(leftItem as List<Any>, rightItem as List<Any>)
            }

            if (res !== null) {
                return res
            }
        }

        return null
    }

    private fun isOrdered(left: List<Any>, right: List<Any>): Boolean? {
        var res: Boolean? = null

        if (right.isEmpty() && left.isNotEmpty()) {
            res = false
        }

        if (left.isEmpty() && right.isNotEmpty()) {
            res = true
        }

        if (res !== null) {
            return res
        }

        if (left.isEmpty() && right.isEmpty()) {
            return null
        }

        val leftItem = left.first()
        val rightItem = right.first()

        if (leftItem is Int && rightItem is Int) {
            if (leftItem < rightItem) {
                res = true
            }

            if (leftItem > rightItem) {
                res = false
            }
        }

        if (leftItem is List<*> && rightItem is Int) {
            res = isOrdered(leftItem as List<Any>, arrayListOf(rightItem))
        }

        if (leftItem is Int && rightItem is List<*>) {
            res = isOrdered(arrayListOf(leftItem), rightItem as List<Any>)
        }

        if (leftItem is List<*> && rightItem is List<*>) {
            res = isOrdered(leftItem as List<Any>, rightItem as List<Any>)
        }

        if (res !== null) {
            return res
        } else {
            return isOrdered(
                left.takeLast(left.size - 1),
                right.takeLast(right.size - 1)
            )
        }
    }
}