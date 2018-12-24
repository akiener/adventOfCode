import java.io.File

fun main(args: Array<String>) {
    val input = File("res/day6.txt").readLines()
    val grid = PointGrid()
    input.map { it.toPoint() }.forEachIndexed { i, it -> grid.add(it) }

    grid.convertDataToArray()
    grid.calculateManhattenDistance()
    grid.print()
}

fun String.toPoint(): Point {
    val parts = this.split(", ")
    return Point(parts[0].toInt(), parts[1].toInt())
}

data class Point(val x: Int, val y: Int)

class PointGrid {
    val data = mutableListOf<Point>()
    var endPoint = Point(0, 0)
    val array = Array(endPoint.x + 1) { Array(endPoint.y + 1) { 0 } }

    fun add(p: Point) {
        data.add(p)
        endPoint = Point(if (p.x > endPoint.x) p.x else endPoint.x, if (p.y > endPoint.y) p.y else endPoint.y)
    }

    fun convertDataToArray() {
        data.forEachIndexed { i, it -> array[it.x][it.y] = i }
    }

    fun print() {
        array.forEach {
            it.forEach {
                print("$it\t")
            }; println()
        }
    }

    fun calculateManhattenDistance() {

    }

}