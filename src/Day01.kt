import kotlin.math.abs

fun main() {
    fun getLists(input: List<String>): Pair<List<Int>, List<Int>> =
        input.map {
            val (x, y) = it.split("   ").map(String::toInt)
            Pair(x, y)
        }.unzip()

    fun part1(input: List<String>): Int {
        var (a, b) = getLists(input)
        a = a.toMutableList()
        b = b.toMutableList()
        a.sort()
        b.sort()
        return a.zip(b).sumOf { (x, y) -> abs(x - y) }
    }

    fun part2(input: List<String>): Int {
        val (a, b) = getLists(input)
        val freq = b.fold(mutableMapOf<Int, Int>()) { freq, x ->
            freq.merge(x, 1, Int::plus)
            freq
        }
        return a.sumOf { it * freq.getOrDefault(it, 0) }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
