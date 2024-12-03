fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)").findAll(line).sumOf {
                val (lhs, rhs) = it.destructured
                lhs.toInt() * rhs.toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        var enabled = true
        for (line in input) {
            for (match in Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)").findAll(line)) {
                when (match.value) {
                    "do()" -> enabled = true
                    "don't()" -> enabled = false
                    else -> if (enabled) {
                        val (lhs, rhs) = match.destructured
                        sum += lhs.toInt() * rhs.toInt()
                    }
                }
            }
        }
        return sum
    }

    val testInput1 = readInput("Day03_test1")
    check(part1(testInput1) == 161)
    val testInput2 = readInput("Day03_test2")
    check(part2(testInput2) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
