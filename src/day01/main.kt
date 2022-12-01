package day01

import java.io.File

// Input: number of calories each elf is carying.
// Problem: Find the total calories that the elf with the most calories is carrying.

fun main() {
    var currCals = 0
    val totalCals = mutableListOf<Int>()

    val inputFile = File("src/day01/input.txt")

    inputFile.forEachLine {
        if (it.isEmpty()) {
            // Reached new line.
            totalCals.add(currCals)
            currCals = 0
        } else {
            currCals += it.toInt()
        }
    }
    totalCals.sort()

    val sum = totalCals[totalCals.size - 1] + totalCals[totalCals.size - 2] + totalCals[totalCals.size - 3]
    println(sum)
}



