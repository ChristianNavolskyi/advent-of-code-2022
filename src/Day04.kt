class Day04 : Challenge<Int> {
    override val name: String
        get() = "Day 04"

    override fun inputName(): String = "Day04"

    override fun testInputName(): String = "Day04_test"

    override fun testResult1(): Int = 2

    override fun testResult2(): Int = 4

    override fun part1(input: List<String>): Int = input.map { ElfPair(it) }.count { it.contained }

    override fun part2(input: List<String>): Int = input.map { ElfPair(it) }.count { it.overlaps }
}

class ElfPair(private val input: String) {
    val overlaps: Boolean
        get() {
            val bounds = input.bounds()

            return bounds.first.overlaps(bounds.second) || bounds.second.contains(bounds.first)
        }

    val contained: Boolean
        get() {
            val bounds = input.bounds()

            return bounds.first.contains(bounds.second) || bounds.second.contains(bounds.first)
        }

    private fun String.bounds(): Pair<List<Int>, List<Int>> {
        val ranges = split(",")
        val firstBounds = ranges[0].split("-").map { it.toInt() }
        val secondBounds = ranges[1].split("-").map { it.toInt() }

        return Pair(firstBounds, secondBounds)
    }

    private fun List<Int>.contains(other: List<Int>) = first() <= other.first() && last() >= other.last()

    private fun List<Int>.overlaps(other: List<Int>) =
        first() <= other.first() && other.first() <= last() || first() <= other.last() && other.last() <= last()
}