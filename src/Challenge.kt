interface Challenge<Result> {
    val name: String

    fun inputName(): String = name.replace(" ", "")

    fun testInputName(): String = inputName() + "_test"

    fun testInputName2(): String = inputName() + "_test"

    fun testResult1(): Result

    fun testResult2(): Result

    fun part1(input: String): Result

    fun part2(input: String): Result
}