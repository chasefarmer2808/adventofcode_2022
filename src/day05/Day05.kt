package day05

import java.util.Stack

class Day05 {
    val stacks = mutableListOf<Stack<Char>>()
    val queues = mutableListOf<ArrayDeque<Char>>()

    fun partOne(input: List<String>): String {
        initStacks(input)
        processMoves(input)
        return getTopOfStacks()
    }

    fun partTwo(input: List<String>): String {
        initQueues(input)
        processMovesForQueues(input)
        return getTopOfQueues()
    }

    private fun initStacks(input: List<String>) {
        stacks.clear()

        input.forEach { line ->
            line.forEachIndexed { index, c ->
                if (c == '[') {
                    val stackIndex = index / 4 // opening brackets are placed every multiple of 4 index.

                    // Add char in next index to desired stack.
                    while (stacks.size == 0 || stacks.size <= stackIndex) {
                        stacks.add(Stack<Char>())
                    }
                    stacks[stackIndex].push(line[index + 1])
                }
            }
        }

        // Finally, reverse each stack because we parsed the file from top to bottom.
        stacks.forEach { stack -> stack.reverse() }
    }

    private fun initQueues(input: List<String>) {
        queues.clear()

        input.forEach { line ->
            line.forEachIndexed { index, c ->
                if (c == '[') {
                    val stackIndex = index / 4 // opening brackets are placed every multiple of 4 index.

                    // Add char in next index to desired stack.
                    while (queues.size == 0 || queues.size <= stackIndex) {
                        queues.add(ArrayDeque<Char>())
                    }
                    queues[stackIndex].add(line[index + 1])
                }
            }
        }

        // Finally, reverse each stack because we parsed the file from top to bottom.
        queues.forEach { queue -> queue.reverse() }
    }

    private fun processMoves(input: List<String>) {
        val commands = input.filter { line -> line.contains("move") }

        commands.forEach { command ->
            val nums = command.split(" ").filter { token ->
                try {
                    Integer.parseInt(token)
                    true
                } catch (e: java.lang.NumberFormatException) {
                    false
                }
            }.map { numStr -> numStr.toInt() }

            val count = nums[0]
            val firstStack = nums[1] - 1
            val secondStack = nums[2] - 1

            for (i in 0 until count) {
                val currMoving = stacks[firstStack].pop()
                stacks[secondStack].push(currMoving)
            }
        }
    }

    private fun processMovesForQueues(input: List<String>) {
        val commands = input.filter { line -> line.contains("move") }

        commands.forEach { command ->
            val nums = command.split(" ").filter { token ->
                try {
                    Integer.parseInt(token)
                    true
                } catch (e: java.lang.NumberFormatException) {
                    false
                }
            }.map { numStr -> numStr.toInt() }

            val count = nums[0]
            val firstStack = nums[1] - 1
            val secondStack = nums[2] - 1

            val charsToRemove = queues[firstStack].takeLast(count)
            queues[secondStack].addAll(charsToRemove)

            for (i in 0 until count) {
                queues[firstStack].removeLast()

//                val currMoving = if (count > 1) {
//                    queues[firstStack].removeFirst()
//                } else {
//                    queues[firstStack].removeLast()
//                }
//                queues[secondStack].add(currMoving)
            }
        }
    }

    private fun getTopOfStacks(): String {
        var topPattern = ""

        stacks.forEachIndexed { index, stack ->
            topPattern += stack.pop()
        }

        return topPattern
    }

    private fun getTopOfQueues(): String {
        var topPattern = ""

        queues.forEach { stack ->
            topPattern += stack.removeLast()
        }

        return topPattern
    }
}