import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.sign

class Day09 : Challenge<Int> {
    override val name: String
        get() = "Day 09"

    override fun inputName(): String = "Day09"

    override fun testInputName(): String = "Day09_test"

    override fun testInputName2(): String = "Day09_test2"

    override fun testResult1(): Int = 13

    override fun testResult2(): Int = 36

    override fun part1(input: String): Int {
        val moves = input.trim().split("\n").map { Move.fromInput(it) }

        val traceMap = mutableSetOf<Pair<Int, Int>>()
        val rope = Rope(listOf(RopeSegment("H", null), RopeSegment("T", traceMap)))

        moves.forEach { rope.apply(it) }

        return traceMap.count()
    }

    override fun part2(input: String): Int {
        val moves = input.trim().split("\n").map { Move.fromInput(it) }

        val traceMap = mutableSetOf<Pair<Int, Int>>()
        val rope = Rope(listOf(RopeSegment("H", null)) + List(8) { RopeSegment((it + 1).toString(), null) } + listOf(RopeSegment("T", traceMap)))

        moves.forEach { rope.apply(it) }

        return traceMap.count()
    }

}

enum class Direction(private val shortHand: Char) {
    UP('U'), LEFT('L'), RIGHT('R'), DOWN('D');

    companion object {
        fun getByShortHand(shortHand: Char) = Direction.values().first { it.shortHand == shortHand }
    }
}

abstract class Move(val steps: Int) {
    abstract override fun toString(): String

    abstract fun step(position: Pair<Int, Int>): Pair<Int, Int>

    companion object {
        fun fromInput(input: String): Move {
            val inputPart = input.split(" ")
            val direction = Direction.getByShortHand(inputPart[0].first())
            val steps = inputPart[1].toInt()

            return when (direction) {
                Direction.UP -> MoveUp(steps)
                Direction.LEFT -> MoveLeft(steps)
                Direction.RIGHT -> MoveRight(steps)
                Direction.DOWN -> MoveDown(steps)
            }
        }
    }
}

class MoveUp(steps: Int) : Move(steps) {
    override fun toString(): String = "Up $steps"

    override fun step(position: Pair<Int, Int>): Pair<Int, Int> = Pair(position.first, position.second + 1)
}

class MoveDown(steps: Int) : Move(steps) {
    override fun toString(): String = "Down $steps"

    override fun step(position: Pair<Int, Int>): Pair<Int, Int> = Pair(position.first, position.second - 1)
}

class MoveLeft(steps: Int) : Move(steps) {
    override fun toString(): String = "Left $steps"

    override fun step(position: Pair<Int, Int>): Pair<Int, Int> = Pair(position.first - 1, position.second)
}

class MoveRight(steps: Int) : Move(steps) {
    override fun toString(): String = "Right $steps"

    override fun step(position: Pair<Int, Int>): Pair<Int, Int> = Pair(position.first + 1, position.second)
}


class Rope(private val segments: List<RopeSegment>) {
    fun apply(move: Move) {
        repeat((0 until move.steps).count()) {
            val first = segments.first()
            first.position = move.step(first.position)

            segments.subList(1, segments.size).fold(first.position) { previousPosition, ropeSegment ->
                ropeSegment.updatedLeading(previousPosition)
                ropeSegment.position
            }
        }
    }

}

class RopeSegment(private val segmentName: String, private val trace: MutableSet<Pair<Int, Int>>?) {
    var position: Pair<Int, Int> = Pair(0, 0)
        set(value) {
//            println("moving segment $segmentName: ${field.toStringPrint()} -> ${value.toStringPrint()}")

            field = value
            trace?.add(value)
        }

    init {
        trace?.add(position)
    }

    fun updatedLeading(newLeading: Pair<Int, Int>) {
        val totalDistance = newLeading - position

        if (shouldMoveTail(totalDistance)) {

            val differenceVector = totalDistance / 2
            val newTail = position + differenceVector

            position = newTail
        }
    }

    private fun shouldMoveTail(totalDistance: Pair<Int, Int>): Boolean = totalDistance.abs().max() > 1


    private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> = Pair(first - other.first, second - other.second)

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> = Pair(first + other.first, second + other.second)

    private operator fun Pair<Int, Int>.div(divider: Int): Pair<Int, Int> =
        Pair(first.sign * ceil(first.absoluteValue / divider.toDouble()).toInt(), second.sign * ceil(second.absoluteValue / divider.toDouble()).toInt())

    private fun Pair<Int, Int>.abs(): Pair<Int, Int> = Pair(first.absoluteValue, second.absoluteValue)

    private fun Pair<Int, Int>.max(): Int = kotlin.math.max(first, second)

    private fun Pair<Int, Int>.toStringPrint() = "($first,$second)"
}