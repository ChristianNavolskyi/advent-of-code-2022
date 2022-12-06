import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

enum class Weapon(val opponent: Char, val me: Char, val value: Int) {
    ROCK('A', 'X', 1), PAPER('B', 'Y', 2), SCISSORS('C', 'Z', 3);

    companion object {
        private val opponentMap: Map<Char, Weapon> = mapOf(Pair('A', ROCK), Pair('B', PAPER), Pair('C', SCISSORS))
        private val meMap: Map<Char, Weapon> = mapOf(Pair('X', ROCK), Pair('Y', PAPER), Pair('Z', SCISSORS))

        fun byOpponent(opponent: Char): Weapon = opponentMap[opponent]!!
        fun byMe(me: Char): Weapon = meMap[me]!!
    }
}

class Fight(
    private val opponent: Weapon,
    private val me: Weapon
) {

    fun evaluate(): Int = me.value + when (me) {
        Weapon.ROCK -> pointsRock[opponent]!!
        Weapon.PAPER -> pointsPaper[opponent]!!
        Weapon.SCISSORS -> pointsScissors[opponent]!!
    }

    companion object {
        val pointsRock: Map<Weapon, Int> = mapOf(Pair(Weapon.ROCK, 3), Pair(Weapon.PAPER, 0), Pair(Weapon.SCISSORS, 6))
        val pointsPaper: Map<Weapon, Int> = mapOf(Pair(Weapon.ROCK, 6), Pair(Weapon.PAPER, 3), Pair(Weapon.SCISSORS, 0))
        val pointsScissors: Map<Weapon, Int> = mapOf(Pair(Weapon.ROCK, 0), Pair(Weapon.PAPER, 6), Pair(Weapon.SCISSORS, 3))
    }
}

@ExperimentalTime
fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val parts = it.split(" ")
            Fight(Weapon.byOpponent(parts[0].first()), Weapon.byMe(parts[1].first()))
        }.sumOf { it.evaluate() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readLines("Day02_test")
    println(part1(testInput))

    check(part1(testInput) == 15)
//    check(part2(testInput) == 45000)

    val input = readLines("Day02")

    val firstTime = measureTime {
        part1(input)
        part1(input)
        part1(input)
        part1(input)
    }.div(4)

    println("Times")
    println("First part took: $firstTime")


    println(part1(input))
    println(part2(input))
}
