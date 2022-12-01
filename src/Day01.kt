import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        return input.toMutableList().apply { add("\n") }.map { it.toIntOrNull() ?: -1 }.fold(listOf(-1)) { acc, calories ->
            if (calories == -1) {
                val sum = acc.subList(1, acc.size).sum()

                listOf(max(sum, acc.first()))
            } else {
                listOf(*acc.toTypedArray(), calories)
            }
        }.first()
    }

    fun part2(input: List<String>): Int {
        val output = input.toMutableList().apply { add("\n") }.map { it.toIntOrNull() ?: -1 }.fold(listOf(-1, -1, -1)) { acc, calories ->
            if (calories == -1) {
                val sum = acc.subList(3, acc.size).sum()

                if (sum > acc[0]) {
                    listOf(sum, acc[0], acc[1])
                } else if (sum > acc[1]) {
                    listOf(acc[0], sum, acc[1])
                } else if (sum > acc[2]) {
                    listOf(acc[0], acc[1], sum)
                } else {
                    listOf(acc[0], acc[1], acc[2])
                }
            } else {
                val list = listOf(*acc.toTypedArray(), calories)
                list
            }
        }

        return output.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
