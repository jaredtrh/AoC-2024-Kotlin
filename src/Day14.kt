fun main() {
    fun part1(input: List<String>, n: Int, m: Int): Int {
        val quad = IntArray(4)
        for (line in input) {
            val (px, py, vx, vy) = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)").matchEntire(line)!!.groups
                .drop(1).map { it!!.value.toInt() }
            val x = (px + (vx + n) * 100) % n
            val y = (py + (vy + m) * 100) % m
            if (x != n / 2 && y != m / 2)
                quad[(if (x > n / 2) 1 else 0) + (if (y > m / 2) 2 else 0)] += 1
        }
        return quad.reduce(Int::times)
    }

    fun part2(input: List<String>, n: Int, m: Int): Int {
        val robots = input.map { line ->
            Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)").matchEntire(line)!!.groups
                .drop(1).map { it!!.value.toInt() }.toMutableList()
        }
        var ans = 0
        while (true) {
            for ((i, robot) in robots.withIndex()) {
                val (px, py, vx, vy) = robot
                robots[i][0] = (px + vx + n) % n
                robots[i][1] = (py + vy + m) % m
            }
            ans += 1
            if (robots.distinctBy { Pair(it[0], it[1]) }.size == robots.size)
                return ans
        }
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput, 11, 7) == 12)

    val input = readInput("Day14")
    part1(input, 101, 103).println()
    part2(input, 101, 103).println()
}
