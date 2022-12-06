class Day06 : Challenge<Int> {
    override val name: String
        get() = "Day 06"

    override fun inputName(): String = "Day06"

    override fun testInputName(): String = "Day06_test"

    override fun testResult1(): Int = 7

    override fun testResult2(): Int = 19

    override fun part1(input: String): Int = input.trim().indexOfUniqueSequenceFor(4)


    override fun part2(input: String): Int = input.trim().indexOfUniqueSequenceFor(14)

    private fun String.indexOfUniqueSequenceFor(numberOfCharacters: Int): Int = (numberOfCharacters until length).first {
        substring(it - numberOfCharacters, it).toSet().size == numberOfCharacters
    }
}

