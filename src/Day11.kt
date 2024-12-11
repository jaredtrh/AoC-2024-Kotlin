fun main() {
    val memo: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()
    fun calc(stone: Long, blinks: Int): Long {
        if (blinks == 0) return 1
        val key = Pair(stone, blinks)
        memo[key]?.let { return it }
        return if (stone == 0L) {
            calc(1, blinks - 1)
        } else {
            val s = stone.toString()
            if (s.length % 2 == 0) {
                val left = s.substring(0 ..< s.length / 2).toLong()
                val right = s.substring(s.length / 2 ..< s.length).toLong()
                calc(left, blinks - 1) + calc(right, blinks - 1)
            } else {
                calc(stone * 2024, blinks - 1)
            }
        }.also {
            memo[key] = it
        }
    }

    fun part1(input: List<String>): Long {
        return input[0].split(' ').map(String::toLong).sumOf { calc(it, 25) }
    }

    fun part2(input: List<String>): Long {
        return input[0].split(' ').map(String::toLong).sumOf { calc(it, 75) }
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
