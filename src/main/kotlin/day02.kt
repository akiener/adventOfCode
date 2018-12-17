import java.io.File

fun main(args: Array<String>) {
    var twos = 0
    var threes = 0

    val boxIDs = File("res/day02input.txt").readLines()

    boxIDs.forEach {
        val letters = mutableMapOf<Char, Int>()
        it.toCharArray().forEach { c: Char ->
            if (letters.contains(c)) {
                letters[c] = letters[c]!! + 1
            } else {
                letters[c] = 1
            }
        }
        if (letters.values.filter { count -> count == 2 }.sum() > 0)
            twos++
        if (letters.values.filter { count -> count == 3 }.sum() > 0)
            threes++
    }

    println("$twos twos and $threes threes")
    val checksum = twos * threes
    println("Checksum: $checksum")

    boxIDs.forEach {first ->
        boxIDs.filter { second -> second != first }.forEach{second ->
            if (hammingDistance(first, second) == 1) {
                println("$first and")
                println("$second are similar")
            }
        }
    }
}

fun hammingDistance(first: String, second: String): Int {
    require(first.length == second.length) { "first and second String must be of equal length." }
    return first.zip(second).count{it.first != it.second}
}