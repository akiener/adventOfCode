import java.io.File

fun main(args: Array<String>) {
    val elfClaims = File("res/day3.txt").readLines()

    val grid = Grid()
    elfClaims.forEach {
        val claimParts = it.split(" ")
        val id = claimParts[0].substring(1).toInt()
        val coordParts = claimParts[2].substring(0, claimParts[2].length - 1).split(",")
        val x = coordParts[0].toInt()
        val y = coordParts[1].toInt()
        val sizeParts = claimParts[3].split("x")
        val xSize = sizeParts[0].toInt()
        val ySize = sizeParts[1].toInt()

        grid.addClaim(Claim(id, x, y, xSize, ySize))
    }

    val countOverlappingClaims = grid.data.flatten().count{ it.size >= 2 }
    println("countOverlappingClaims: $countOverlappingClaims")

    val overlappingClaims = grid.data.flatten().filter { it.size >= 2 }.flatten().distinct()
    val possibleFreeClaims = grid.data.flatten().filter { it.size == 1 }.map { it.first() }
    val freeClaims = possibleFreeClaims.subtract(overlappingClaims)
    println(freeClaims)
}

class Grid {
    val data = Array(1000) {Array(1000) {mutableListOf<Int>()} }

    fun get(x: Int, y: Int): List<Int> {
        return data[x][y]
    }

    fun addClaim(claim: Claim) {
        for ((x, y) in claim.toListOfCoordinates()) {
            data[x][y].add(claim.id)
        }
    }
}

data class Claim(val id: Int, val x: Int, val y: Int, val xSize: Int, val ySize: Int) {
    fun toListOfCoordinates(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (xStep in (x..x + xSize - 1)) {
            for (yStep in (y..y + ySize - 1)) {
                result.add(Pair(xStep, yStep))
            }
        }
        return result
    }
}