interface Challenge<Result> {
    val name: String

    fun inputName(): String

    fun testInputName(): String

    fun testResult1(): Result

    fun testResult2(): Result

    fun part1(input: List<String>): Result

    fun part2(input: List<String>): Result
}