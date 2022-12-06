class Day05 : Challenge<String> {
    override val name: String
        get() = "Day 05"

    override fun inputName(): String = "Day05"

    override fun testInputName(): String = "Day05_test"

    override fun testResult1(): String = "CMZ"

    override fun testResult2(): String = "MCD"

    override fun part1(input: String): String {
        val challengeSetup = input.split("\n\n")

        val stacks = challengeSetup.first().split("\n").parseStacks()

        challengeSetup[1]
            .split("\n")
            .filter { it.isNotBlank() }
            .map { it.moves() }
            .forEach { move -> stacks.applyMove9000(move) }

        return stacks.evaluateChallenge()
    }

    override fun part2(input: String): String {
        val challengeSetup = input.split("\n\n")

        val stacks = challengeSetup.first().split("\n").parseStacks()

        challengeSetup[1]
            .split("\n")
            .filter { it.isNotBlank() }
            .map { it.moves() }
            .forEach { move -> stacks.applyMove9001(move) }

        return stacks.evaluateChallenge()
    }

    private fun Map<Int, MutableList<Char>>.applyMove9001(move: Triple<Int, Int, Int>) {
        val (amount, from, to) = move

        this[to]?.addAll(
            this[from]?.takeLast(amount)
                ?: throw IndexOutOfBoundsException("Cannot move $amount crates from $from to $to of $this")
        )
        repeat((1..amount).count()) { this[from]?.removeLastOrNull() }
    }
    private fun Map<Int, MutableList<Char>>.applyMove9000(move: Triple<Int, Int, Int>) {
        val (amount, from, to) = move

        this[to]?.addAll(
            this[from]?.takeLast(amount)?.reversed()
                ?: throw IndexOutOfBoundsException("Cannot move $amount crates from $from to $to of $this")
        )
        repeat((1..amount).count()) { this[from]?.removeLastOrNull() }
    }

    private fun Map<Int, List<Char>>.evaluateChallenge(): String =
        toSortedMap().mapNotNull { it.value.lastOrNull { c -> c.isLetter() } }.joinToString("")


    private fun String.moves(): Triple<Int, Int, Int> {
        val parts = split(" ")

        return Triple(parts[1].toInt(), parts[3].toInt(), parts[5].toInt())
    }

    private fun List<String>.parseStacks(): Map<Int, MutableList<Char>> {
        val numberOfStacks = last().split(regex = Regex("\\s")).last { it.isNotBlank() }.toInt()
        val result = (1..numberOfStacks).associateWith { mutableListOf<Char>() }

        reversed().drop(1).forEach {
            it.crates(numberOfStacks).forEachIndexed { index, c ->
                if (c.isLetter()) {
                    result[index + 1]?.add(c)
                        ?: throw IndexOutOfBoundsException("Failed to insert $c at index $index into $result")

                }
            }
        }

        return result
    }

    private fun String.crates(numberOfStacks: Int): List<Char> {
        return substring(1).mapIndexedNotNull { index, c ->
            if (index % 4 == 0) {
                c
            } else {
                null
            }
        }
    }
}

