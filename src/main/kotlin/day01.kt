import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val inst = Instrument()
    for (i in 1..1000) {
        File("res/day01input.txt").forEachLine { inst.eval(it) }
        println(inst.sum) // 582
    }
}

class Instrument {
    var sum = 0
    val previouslyHitFrequencies = mutableSetOf<Int>()

    fun eval(line: String) {
        sum += line.toInt()
        if (previouslyHitFrequencies.contains(sum)) {
            println("first hit is $sum")
            exitProcess(0)
        } else {
            previouslyHitFrequencies.add(sum)
        }
    }
}