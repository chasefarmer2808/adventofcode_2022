package day11

var modFactor = 1L

data class Throw(val dest: Int, val worryLevel: Long)

class Monkey(val items: ArrayList<Long>, val operation: String, val operand: Long?, val testValue: Long, val trueIndex: Int, val falseIndex: Int) {
    var inspections = 0L

    fun getNextThrowIndex(divisor: Long = 3): Throw? {
        if (items.isEmpty()) {
            return null
        }

        val currItem = items.first()

        var newWorryLevel = when (operation) {
            "+" -> currItem + (operand ?: currItem)
            "*" -> currItem * (operand ?: currItem)
            else -> throw UnsupportedOperationException("Unsupported operation: $operation")
        }

        newWorryLevel /= divisor

        return if (newWorryLevel % testValue == 0L) Throw(trueIndex, newWorryLevel) else Throw(falseIndex, newWorryLevel)
    }

    fun getNextThrowIndexSafe(): Throw? {
        if (items.isEmpty()) {
            return null
        }

        val currItem = items.first()

        var newWorryLevel = when (operation) {
            "+" -> currItem + (operand ?: currItem)
            "*" -> currItem * (operand ?: currItem)
            else -> throw UnsupportedOperationException("Unsupported operation: $operation")
        }

        newWorryLevel %= modFactor

        return if (newWorryLevel % testValue == 0L) Throw(trueIndex, newWorryLevel) else Throw(falseIndex, newWorryLevel)
    }
}

class Day11 {
    var rounds = 20
    val testMonkeys = listOf(
        Monkey(arrayListOf(79, 98), "*", 19, 23, 2, 3),
        Monkey(arrayListOf(54, 65, 75, 74), "+", 6, 19, 2, 0),
        Monkey(arrayListOf(79, 60, 97), "*", null, 13, 1, 3),
        Monkey(arrayListOf(74), "+", 3, 17, 0, 1)
    )
    val realMonkees = listOf(
        Monkey(arrayListOf(56, 52, 58, 96, 70, 75, 72), "*", 17, 11, 2, 3),
        Monkey(arrayListOf(75, 58, 86, 80, 55, 81), "+", 7, 3, 6, 5),
        Monkey(arrayListOf(73, 68, 73, 90), "*", null, 5, 1, 7),
        Monkey(arrayListOf(72, 89, 55, 51, 59), "+", 1, 7, 2, 7),
        Monkey(arrayListOf(76, 76, 91), "*", 3, 19, 0, 3),
        Monkey(arrayListOf(88), "+", 4, 2, 6, 4),
        Monkey(arrayListOf(64, 63, 56, 50, 77, 55, 55, 86), "+", 8, 13, 4, 0),
        Monkey(arrayListOf(79, 58), "+", 6, 17, 1, 5)

    )

    fun partOne(): Long {
        val monkeys = testMonkeys

        while (rounds > 0) {
            // Execute a round of throwing.
            monkeys.forEach { monkey ->
                monkey.inspections += monkey.items.size

                while (monkey.items.isNotEmpty()) {
                    monkey.getNextThrowIndex(1)?.let { newThrow ->
                        monkey.items.removeFirst()
                        monkeys[newThrow.dest].items.add(newThrow.worryLevel)
                    }
                }
            }

            rounds--
        }

        return monkeys.map { monkey -> monkey.inspections }.sorted().takeLast(2).reduce { acc, i -> acc * i }
    }

    fun partTwo(): Long {
        rounds = 10000
        var roundCount = 0

        val monkeys = realMonkees
        modFactor = monkeys.map { it.testValue }.reduce { acc, l -> acc * l }

        while (rounds > 0) {
            // Execute a round of throwing.
            monkeys.forEach { monkey ->
                monkey.inspections += monkey.items.size

                while (monkey.items.isNotEmpty()) {
                    monkey.getNextThrowIndexSafe()?.let { newThrow ->
                        monkey.items.removeFirst()
                        monkeys[newThrow.dest].items.add(newThrow.worryLevel)
                    }
                }
            }

            roundCount++
            rounds--

            if (roundCount == 20) {
                monkeys.forEach { monkey ->
                    println(monkey.inspections)
                }
            }
        }

        return monkeys.map { monkey -> monkey.inspections }.sorted().takeLast(2).reduce { acc, i -> acc * i }
    }
}