import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
class Executor(private val benchmarkRepeats: Int = 3) {
    private val challenges: MutableList<Challenge> = mutableListOf()

    init {
        challenges.add(Day03())
        challenges.add(Day04())
    }

    fun executeChallenges() {
        challenges.forEach {
            println("Challenge: ${it.name}")
            val testInput = readInput(it.testInputName())
            val input = readInput(it.inputName())

            val testResult1 = it.part1(testInput)
            println("First test result: $testResult1")
            check(testResult1 == it.testResult1())
            println("Part 1 - ${it.part1(input)}")

            val testResult2 = it.part2(testInput)
            println("Second test result: $testResult2")
            check(testResult2 == it.testResult2())
            println("Part 2 - ${it.part2(input)}")


        }
    }

    fun benchmarkChallenges() {
        challenges.forEach { challenge ->
            val input = readInput(challenge.inputName())

            println("Benchmark Challenge: ${challenge.name}")

            val firstTime = measureTime { repeat(benchmarkRepeats) { challenge.part1(input) } }.div(benchmarkRepeats)
            println("Part 1 took - $firstTime")

            val secondTime = measureTime { repeat(benchmarkRepeats) { challenge.part2(input) } }.div(benchmarkRepeats)
            println("Part 2 took - $secondTime")
        }
    }
}

fun main() {
    val executor = Executor(benchmarkRepeats = 1)

    executor.executeChallenges()
    executor.benchmarkChallenges()
}