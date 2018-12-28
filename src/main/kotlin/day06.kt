import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    val input = File("res/day6.txt").readLines()
    val grid = PointGrid()
    input.mapIndexed { i, it -> it.toPoint(i) }.forEachIndexed { i, it -> grid.add(it) }

    grid.solve()
}

fun String.toPoint(id: Int): Point {
    val parts = this.split(", ")
    return Point(parts[0].toInt(), parts[1].toInt(), id)
}

data class Point(val x: Int, val y: Int, val id: Int)

class PointGrid {
    val data = mutableListOf<Point>()
    var endPoint = Point(0, 0, -1)

    val neighbors = mutableListOf<(Int, Int) -> Pair<Int, Int>>()

    init {
        for (xSummand in (-1..1)) {
            for (ySummand in (-1..1)) {
                neighbors.add { x, y ->
                    Pair(x + xSummand, y + ySummand)
                }
            }
        }
    }

    fun add(p: Point) {
        data.add(p)
        endPoint = Point(if (p.x > endPoint.x) p.x else endPoint.x, if (p.y > endPoint.y) p.y else endPoint.y, -1)
    }

    fun solve() {
        data.forEach { array[it.x][it.y] = it.id }

        array.forEach {
            it.forEach {
                if (it == -1)
                    print("-")
                else
                    print(it)
                print("\t")
            }; println()
        }

        val gridDistanced = MutableList(endPoint.x + 1) { MutableList(endPoint.y + 1) { mutableMapOf<Point, Int>() } }
        data.forEach {
            // add itself
            gridDistanced[it.x][it.y][it] = 0
        }

        array.forEachIndexed { i, it ->
            it.forEachIndexed { j, it ->
            }
        }

        gridDistanced.forEachIndexed { i, rows ->
            rows.forEachIndexed { j, map ->
                if (!map.values.contains(0)) {

                }
            }
        }
    }

    fun getManhatternDistance(p: Point, p1: Point): Int {
        return abs(p.x - p1.x) + abs(p.y - p1.y)
    }

    val array = MutableList(endPoint.x + 1) { MutableList(endPoint.y + 1) { -1 } }

    fun getNextNeighbors(i: Int, j: Int, distance: Int): List<Point> {
        val points = MutableMap<Point, Int>()

        neighbors.map { it(i, j) }.forEach { (xNex, yNex) ->
            if (isInBound(xNex, yNex)) {
                val id = array[xNex][yNex]
                if (id >= 0)
                    points.put(Point(xNex, yNex, id), distance)
            }
        }

        while (points.size == 0)
            points.addAll(neighbors.map { it(i, j) }.filter { (x, y) -> isInBound(x, y) }.map { (xNex, yNex) ->
                getNextNeighbors(xNex, yNex, distance + (abs(xNex) + abs(yNex)))
            }.flatten())
        return points
    }

    fun isInBound(x: Int, y: Int): Boolean {
        return x >= 0 && x <= endPoint.x && y >= 0 && y <= endPoint.y
    }
}