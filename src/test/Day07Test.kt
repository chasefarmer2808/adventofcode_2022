package test

import day07.Day07
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

/*
Part one: Find all directories with a total size of at most 100000.  Then calculate the sum of their total sizes.
Ex:
- / (dir)
  - a (dir)
    - e (dir)
      - i (file, size=584)
    - f (file, size=29116)
    - g (file, size=2557)
    - h.lst (file, size=62596)
  - b.txt (file, size=14848514)
  - c.dat (file, size=8504156)
  - d (dir)
    - j (file, size=4060174)
    - d.log (file, size=8033020)
    - d.ext (file, size=5626152)
    - k (file, size=7214296)

In this example, dirs a and e meet the criteria.  e is of size 584.  a is of size (f + g + h) + size of e.  So ultimatly,
we would return 94853 + 584 = 95437

Input given as
$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k

- / (dir)
  - a (dir)
    - e (dir)
      - i (file, size=584)
    - f (file, size=29116)
    - g (file, size=2557)
    - h.lst (file, size=62596)
  - b.txt (file, size=14848514)
  - c.dat (file, size=8504156)
  - d (dir)
    - j (file, size=4060174)
    - d.log (file, size=8033020)
    - d.ext (file, size=5626152)
    - k (file, size=7214296)

Line the starts with '$' means a command to execute.

init an empty map to represent the root fs.
$ cd <dir> means make dir called / and set that as cwd.
$ ls can be skipped
dir <filename> means there is a directory called a.
    put a new item in the cwd with a key of the dirname and value of a new hashmap.
<int> <filename> means there is a file with size <int>
    put a new item in the cwd with a key of filename and value of int
$ cd .. means to set cwd to parent wd
    remove last item from path and set cwd to new last item in path.

if current line is a command
    if command is cd
        create a new obj in map with key of dir name
        for each file in dir
            if line starts with "dir"
                recurse
            else if line starts with number
                create new obj in map with key of file name and value of file size

Let's start with the root of the file system.  This contains a list of files, where some of those files could by directories
themselves.

class File
    int size
    bool isDir
    File[] files

Once the file system has been parsed, need to recurse down it to find sizes of each dir.

 */

class Day07Test {
    @Test
    fun partOneTest() {
        val day07 = Day07()
        val input = File("src/day07/input.txt").readLines()
        assertEquals(95437, day07.partOne(input))
    }

    @Test
    fun partTwoTest() {
        val day07 = Day07()
        val input = File("src/day07/input.txt").readLines()
        day07.partOne(input)
        assertEquals(24933642, day07.partTwo())
    }
}