import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

fun main(args: Array<String>) {
    val guardScheduleUnordered = File("res/day4.txt").readLines()

    val guardSchedules = mutableMapOf<Int, MutableList<Event>>()
    var currentGuard by Delegates.notNull<Int>()
    guardScheduleUnordered.map {
        val parts = it.split("] ")
        val dateString = parts[0].substring(1)
        val date = dateTimeStrToLocalDateTime(dateString)
        parts[1].toEvent(date)
    }.sortedBy { it.date }.forEach {
        if (it is GuardChange) {
            currentGuard = it.guardId
        }
        if (guardSchedules.containsKey(currentGuard))
            guardSchedules[currentGuard]?.add(it)
        else
            guardSchedules[currentGuard] = mutableListOf(it)
    }
    val activeMinutes = mutableMapOf<Int, MutableMap<Int, Int>>()
    guardSchedules.map { (guardId, events) ->
        Pair(guardId,
                events.filterNot { it is GuardChange }.chunked(2).map { (sleep, wake) -> Pair(sleep.date.minute, wake.date.minute) })
    }.forEach { (guardId, minutes) ->
        minutes.forEach {
            for (i in (it.first until it.second)) {
                if (activeMinutes.containsKey(guardId)) {
                    activeMinutes[guardId]?.set(i, activeMinutes[guardId]?.get(i)?.plus(1) ?: 1)
                } else {
                    activeMinutes[guardId] = mutableMapOf()
                    activeMinutes[guardId]?.set(i, 1)
                }
            }
        }
    }
    val result = activeMinutes.map { (guardId, mins) ->
        Pair(guardId, mins.count())
    }.maxBy { it.second }
    val result2 = activeMinutes.map { (guardId, mins) ->
        Pair(guardId, mins.maxBy { (min, count) ->
            count
        })
    }.map { (guardId, minuteCountEntry) ->
        guardId to minuteCountEntry?.value
    }.toMap()
    if (result != null) {
        println("guard ${result.first} is asleep the most at minute ${result2[result.first]}")
        println("result: ${result.first * result2[result.first]!!}")
    }
}

open class Event(open val date: LocalDateTime)
class Wakeup(override val date: LocalDateTime) : Event(date)
class Sleep(override val date: LocalDateTime) : Event(date)
class GuardChange(override val date: LocalDateTime, val guardId: Int) : Event(date) {
    override fun toString(): String {
        return "GuardChange ($guardId)"
    }
}

fun String.toEvent(date: LocalDateTime): Event {
    if ("wakes up" == this)
        return Wakeup(date)
    if ("falls asleep" == this)
        return Sleep(date)
    val parts = this.split(" ")
    val guardId = parts[1].substring(1).toInt()
    return GuardChange(date, guardId)
}

val dateTimeStrToLocalDateTime: (String) -> LocalDateTime = {
    LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}