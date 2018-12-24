import java.io.File

fun main(args: Array<String>) {
    var input = File("res/day5.txt").readText().trim()
    var current = input
    for (i in 0 until 99999) {
        //println(input)
        val temp = current.react()
        if (temp == current) {
            println("final")
            break
        }
        current = temp
        //println("-----------------------------------------------------------------------------------------------------")
    }
    println("final: $current")
    println("solution remove nothing for shortest length of ${current.trim().length}")

    for (letter in 'a' until 'z') {
        current = input.filterNot { it == letter || it == letter.toUpperCase() }
        for (i in 0 until 99999) {
            val temp = current.react()
            if (temp == current) {
                println("final")
                break
            }
            current = temp
        }
        println("final: $current")
        println("solution remove $letter for shortest length of ${current.trim().length}")
    }
}

fun String.react(): String {
    this.filterNot { it == '_' }.zipWithNext().forEachIndexed { i, it ->
        if (it.first.swapCase() == it.second) {
            //println("${it.first}${it.second} reacted")
            //println(this.substring(0, i) + "__" + this.substring(i + 2, this.length))
            return this.substring(0, i) + this.substring(i + 2, this.length)
        }
    }
    return this
}

fun Char.swapCase(): Char {
    return if (this.isUpperCase())
        this.toLowerCase()
    else
        this.toUpperCase()
}