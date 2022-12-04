@OptIn(ExperimentalUnsignedTypes::class)
class Day03(override val name: String = "Day 03") : Challenge {
    override fun inputName(): String = "Day03"

    override fun testInputName(): String = "Day03_test"

    override fun testResult1(): Int = 157

    override fun testResult2(): Int = 70

    override fun part1(input: List<String>): Int {
        return input.map {
            Pair(it.substring(0, it.length / 2), it.substring(it.length / 2, it.length))
        }.sumOf {
            val first = it.first.encode()
            val second = it.second.encode()

            first.zip(second).mapIndexed { index, pair ->
                (pair.first and pair.second).toPriority(index)
            }.sum()
        }
    }

    override fun part2(input: List<String>): Int {
        return input.mapIndexed { index, s ->
            Pair(index / 3, s)
        }.groupBy { it.first }
            .map { mapEntry ->
                mapEntry.value.flatMap { listOf(it.second) }
            }.map { Triple(it[0], it[1], it[2]) }
            .sumOf {
                val first = it.first.encode()
                val second = it.second.encode()
                val third = it.third.encode()

                first.mapIndexed { index, uByte ->
                    (uByte and second[index] and third[index]).toPriority(index)
                }.sum()
            }
    }

    private fun UByte.toPriority(index: Int): Int {
        val comparator: UByte = 1u
        return when (this) {
            0.toUByte() -> {
                0
            }

            comparator -> {
                index * 8 + 1
            }

            this and 2u -> {
                index * 8 + 2
            }

            this and 4u -> {
                index * 8 + 3
            }

            this and 8u -> {
                index * 8 + 4
            }

            this and 16u -> {
                index * 8 + 5
            }

            this and 32u -> {
                index * 8 + 6
            }

            this and 64u -> {
                index * 8 + 7
            }

            this and 128u -> {
                index * 8 + 8
            }

            else -> 0
        }
    }

    private fun String.encode(): UByteArray {
        return fold(UByteArray(8)) { acc, char ->
            val priority = char.priority() - 1

            val byteNumber = priority / 8
            val bytePosition = priority % 8

            acc[byteNumber] = acc[byteNumber] or bytePosition.encode()

            acc
        }
    }

    private fun UByteArray.print() {
        forEach { print(it.toString(2)) }
    }

    private fun Int.encode(): UByte = (0x1 shl this).toUByte()

    private fun Char.priority(): Int = if (code > 90) {
        code - 96
    } else {
        code - 38
    }
}