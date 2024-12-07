fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val (testStr, equationStr) = line.split(": ")
            val test = testStr.toLong()
            val equation = equationStr.split(' ').map(String::toInt)
            fun rec(i: Int, acc: Long): Boolean =
                if (i < equation.size) {
                    if (acc > test) {
                        false
                    } else {
                        rec(i + 1, acc * equation[i]) || rec(i + 1, acc + equation[i])
                    }
                } else {
                    acc == test
                }
            if (rec(1, equation.first().toLong())) test else 0
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val (testStr, equationStr) = line.split(": ")
            val test = testStr.toLong()
            val equation = equationStr.split(' ').map(String::toInt)
            fun rec(i: Int, acc: Long): Boolean =
                if (i < equation.size) {
                    if (acc > test) {
                        false
                    } else {
                        rec(i + 1, (acc.toString() + equation[i].toString()).toLong()) ||
                                rec(i + 1, acc * equation[i]) ||
                                rec(i + 1, acc + equation[i])
                    }
                } else {
                    acc == test
                }
            if (rec(1, equation.first().toLong())) test else 0
        }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
