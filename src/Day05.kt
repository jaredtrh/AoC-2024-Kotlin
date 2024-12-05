fun main() {
    fun calc(input: List<String>, right: (ls: List<Int>) -> Int, wrong: (ls: MutableList<Int>, set: Set<Pair<Int, Int>>) -> Int): Int {
        val (ordering, updated) = input.partition { !it.contains(',') }
        val set = ordering.take(ordering.size - 1).map {
            val (lhs, rhs) = it.split('|').map(String::toInt)
            Pair(lhs, rhs)
        }.toSet()
        return updated.sumOf {
            val ls = it.split(',').map(String::toInt)
            if (
                (0 ..< ls.lastIndex).all { i ->
                    (i + 1 ..< ls.size).all { j ->
                        set.contains(Pair(ls[i], ls[j]))
                    }
                }
            ) right(ls) else wrong(ls.toMutableList(), set)
        }
    }

    fun part1(input: List<String>): Int {
        return calc(input, { it[it.size / 2] }, { _, _ -> 0 })
    }

    fun part2(input: List<String>): Int {
        return calc(input, { 0 }, { ls, set ->
            ls.sortWith { lhs, rhs -> if (set.contains(Pair(lhs, rhs))) -1 else 1 }
            ls[ls.size / 2]
        })
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
