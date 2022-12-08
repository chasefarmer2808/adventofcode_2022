package day07

import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.abs
import kotlin.math.min

class Day07 {
    val rootFileSystem = hashMapOf<String, Any>("/" to HashMap<String, Any>())
    val dirSizes = hashMapOf("/" to 0)
    var totalSum = 0

    fun partOne(input: List<String>): Int {
        buildFileSystem(input.takeLast(input.size - 1), rootFileSystem)
        getDirSize(rootFileSystem, "/")
        return totalSum
    }

    fun partTwo(): Int {
        val totalDiskSpace = 70000000
        val spaceNeeded = 30000000

        val unusedSpace = totalDiskSpace - getDirSize(rootFileSystem, "/")
        val targetSpace = spaceNeeded - unusedSpace

        var minDiff = totalDiskSpace

        dirSizes.values.forEach{ size ->
            if (size in targetSpace until minDiff) {
                minDiff = size
            }
        }

        return minDiff
    }

    fun buildFileSystem(input: List<String>, root: HashMap<String, Any>) {
        var cwd = root["/"] as HashMap<String, Any>
        val path = arrayListOf("/")

        input.forEach { line ->
            val tokens = line.split(" ")
            val isFile = tokens[0].toIntOrNull() != null

            when (true) {
                (tokens[0]  == "$") -> {
                    if (tokens[1] == "cd") {
                        cwd = if (tokens[2] == "..") {
                            path.removeLast()
                            getCwd(root, path)
                        } else {
                            path.add(tokens[2])
                            getCwd(root, path)
                        }
                    }
                }
                (tokens[0] == "dir") -> {
                    cwd[tokens[1]] = HashMap<String, Any>()
                }
                (isFile) -> {
                    cwd[tokens[1]] = tokens[0].toInt()
                }
                else -> {} // Do nothing
            }
        }
    }

    private fun getCwd(root: HashMap<String, Any>, filePath: List<String>): HashMap<String, Any> {
        var newCwd = root
        val tempPath = ArrayList(filePath)

        while (tempPath.size > 0 && newCwd.contains(tempPath[0])) {
            newCwd = newCwd[tempPath[0]] as HashMap<String, Any>
            tempPath.removeFirst()
        }

        return newCwd
    }

    private fun getDirSize(dir: HashMap<String, Any>, dirName: String): Int {
        var size = 0

        dir.forEach { entry ->
            if (entry.value.toString().toIntOrNull() !== null) {
                size += entry.value.toString().toInt()
            } else {
                size += getDirSize(entry.value as HashMap<String, Any>, entry.key)
            }
        }

        dirSizes[dirName] = size

        // For part one.
        if (size <= 100000) {
            totalSum += size
        }

        return size
    }
}