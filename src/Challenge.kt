interface Challenge {
    val name: String

    fun inputName(): String

    fun testInputName(): String

    fun testResult1(): Int

    fun testResult2(): Int

    fun part1(input: List<String>): Int

    fun part2(input: List<String>): Int
}