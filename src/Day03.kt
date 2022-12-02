import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
fun main() {
    fun part1(input: List<String>): Int = input.size

    fun part2(input: List<String>): Int = input.size

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 15)

    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))

    val times = 3
    val firstTime = measureTime { repeat(times) { part1(input) } }.div(times)
    val secondTime = measureTime { repeat(times) { part2(input) } }.div(times)

    println("Times")
    println("First part took: $firstTime")
    println("Second part took: $secondTime")
}
