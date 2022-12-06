import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
fun main() {
    fun part1(input: List<String>): Int {
        return input.toMutableList().apply { add("") }.fold(listOf(-1, 0)) { acc, calories ->
            if (calories.isEmpty()) {
                listOf(max(acc[0], acc[1]), 0)
            } else {
                listOf(acc[0], acc[1] + (calories.toIntOrNull() ?: 0))
            }
        }[0]
    }

    fun part2(input: List<String>): Int {
        val output = input.toMutableList().apply { add("") }.fold(listOf(-1, -1, -1, 0)) { acc, calories ->
            if (calories.isEmpty()) {
                val sum = acc[3]

                if (sum > acc[0]) {
                    listOf(sum, acc[0], acc[1], 0)
                } else if (sum > acc[1]) {
                    listOf(acc[0], sum, acc[1], 0)
                } else if (sum > acc[2]) {
                    listOf(acc[0], acc[1], sum, 0)
                } else {
                    listOf(acc[0], acc[1], acc[2], 0)
                }
            } else {
                listOf(acc[0], acc[1], acc[2], acc[3] + (calories.toIntOrNull() ?: 0))
            }
        }

        return output[0] + output[1] + output[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readLines("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readLines("Day01")

    val first: Int
    val second: Int
    val taskTime = measureTime {
        first = part1(input)
        second = part2(input)
    }

    println("Task took: $taskTime to compute part 1 ($first) and part 2 ($second)")

    val times = 1
    val firstTime = measureTime { repeat(times) { part1(input) } }.div(times)
    val secondTime = measureTime { repeat(times) { part2(input) } }.div(times)

    println("Times")
    println("First part took: $firstTime")
    println("Second part took: $secondTime")
}
