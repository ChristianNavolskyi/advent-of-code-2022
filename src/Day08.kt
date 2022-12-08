class Day08 : Challenge<Int> {
    override val name: String
        get() = "Day 08"

    override fun inputName(): String = "Day08"

    override fun testInputName(): String = "Day08_test"

    override fun testResult1(): Int = 21

    override fun testResult2(): Int = 8

    override fun part1(input: String): Int {
        val forest = input.parseForest()

        val visibilityMap = forest.lookup(::visibility)

//        println("Visible Trees")
//        visibilityMap.print()

        return visibilityMap.sum()
    }

    override fun part2(input: String): Int {
        val forest = input.parseForest()

        val sight = forest.lookup(::sight)

//        println("Sight Map")
//        sight.print()

        return sight.max()

    }

    private fun String.parseForest(): List<List<Int>> = trim().split("\n").map { it.map { char -> char.digitToInt() }.toList() }.toList()

    private fun List<List<Int>>.transpose(): List<List<Int>> = List(first().size) { column ->
        List(size) { row ->
            get(row)[column]
        }
    }

    private fun List<List<Int>>.lookup(determinant: (rowIndex: Int, columnIndex: Int, row: List<Int>, column: List<Int>) -> Int): List<List<Int>> {
        val transposed = transpose()

//        println("Forest")
//        print()
//        println("Transposed Forest")
//        transposed.print()

        return mapIndexed { rowIndex, row ->
            List(row.size) { columnIndex ->
                determinant(rowIndex, columnIndex, row, transposed[columnIndex])
            }
        }.toList()
    }

    private fun visibility(rowIndex: Int, columnIndex: Int, row: List<Int>, column: List<Int>): Int {
        val width = row.size
        val height = column.size
        val treeHeight = row[columnIndex]

        return if (rowIndex == 0 || columnIndex == 0 || rowIndex == width - 1 || columnIndex == height - 1) {
            1
        } else {
            val maxLeft = row.subList(0, columnIndex).maxOrNull() ?: 0
            val maxRight = row.subList(columnIndex + 1, width).maxOrNull() ?: 0

            val maxTop = column.subList(0, rowIndex).maxOrNull() ?: 0
            val maxBottom = column.subList(rowIndex + 1, height).maxOrNull() ?: 0

            if (treeHeight > maxLeft || treeHeight > maxRight || treeHeight > maxTop || treeHeight > maxBottom) {
                1
            } else {
                0
            }
        }
    }

    private fun sight(rowIndex: Int, columnIndex: Int, row: List<Int>, column: List<Int>): Int {
        val width = row.size
        val height = column.size
        val treeHeight = row[columnIndex]

        return if (rowIndex == 0 || columnIndex == 0 || rowIndex == width - 1 || columnIndex == height - 1) {
            0
        } else {
            val sightLeft = row.subList(0, columnIndex).reversed().indexOfFirstOrNull { it >= treeHeight }?.inc() ?: columnIndex
            val sightRight = row.subList(columnIndex + 1, width).indexOfFirstOrNull { it >= treeHeight }?.inc() ?: (width - columnIndex - 1)

            val sightTop = column.subList(0, rowIndex).reversed().indexOfFirstOrNull { it >= treeHeight }?.inc() ?: rowIndex
            val sightBottom = column.subList(rowIndex + 1, height).indexOfFirstOrNull { it >= treeHeight }?.inc() ?: (height - rowIndex - 1)

            return sightLeft * sightRight * sightTop * sightBottom
        }
    }


    private fun <T> List<T>.indexOfFirstOrNull(predicate: (it: T) -> Boolean): Int? {
        val index = indexOfFirst(predicate)

        return if (index == -1) null else index
    }


    private fun List<List<Int>>.print() {
        forEach {
            it.forEach { visible -> print("$visible\t") }
            println()
        }
    }

    private fun List<List<Int>>.sum(): Int = sumOf { it.sum() }

    private fun List<List<Int>>.max(): Int = maxOf { it.max() }
}