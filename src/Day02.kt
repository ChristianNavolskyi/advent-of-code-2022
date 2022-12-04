import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
fun main() {
    val pointMap: Map<String, Int> = mapOf(
        Pair("A X", 4),
        Pair("A Y", 8),
        Pair("A Z", 3),
        Pair("B X", 1),
        Pair("B Y", 5),
        Pair("B Z", 9),
        Pair("C X", 7),
        Pair("C Y", 2),
        Pair("C Z", 6),
    )

    val strategyMap: Map<String, Int> = mapOf(
        Pair("A X", 3),
        Pair("A Y", 4),
        Pair("A Z", 8),
        Pair("B X", 1),
        Pair("B Y", 5),
        Pair("B Z", 9),
        Pair("C X", 2),
        Pair("C Y", 6),
        Pair("C Z", 7),
    )

    fun part1(input: List<String>): Int = input.mapNotNull { pointMap[it] }.sum()

    fun part2(input: List<String>): Int = input.mapNotNull { strategyMap[it] }.sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")

    println(part1(input))
    println(part2(input))

    val times = 1
    val firstTime = measureTime { repeat(times) { part1(input) } }.div(times)
    val secondTime = measureTime { repeat(times) { part2(input) } }.div(times)

    println("Times")
    println("First part took: $firstTime")
    println("Second part took: $secondTime")
}
