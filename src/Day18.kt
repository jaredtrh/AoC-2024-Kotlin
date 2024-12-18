import kotlin.math.min

fun main() {
    fun part1(input: List<String>, n: Int, first: Int): Int {
        val grid = Array(n) { BooleanArray(n) }
        for ((x, y) in input.take(first).map { it.split(',').map(String::toInt) })
            grid[y][x] = true
        val q: ArrayDeque<Pair<Int, Int>> = ArrayDeque(listOf(Pair(0, 0)))
        var ans = 0
        while (q.isNotEmpty()) {
            repeat(q.size) {
                val (x, y) = q.first()
                q.removeFirst()
                if (!((0 ..< n).contains(x) && (0 ..< n).contains(y)) || grid[y][x]) return@repeat
                if (x == n - 1 && y == n - 1) return ans
                grid[y][x] = true
                q.add(Pair(x - 1, y))
                q.add(Pair(x + 1, y))
                q.add(Pair(x, y - 1))
                q.add(Pair(x, y + 1))
            }
            ans += 1
        }
        return 0 // unreachable
    }

    fun part2(input: List<String>, n: Int): String {
        val cost = Array(n) { IntArray(n) { input.size } }
        for ((i, byte) in input.map { it.split(',').map(String::toInt) }.withIndex()) {
            val (x, y) = byte
            cost[y][x] = i
        }
        val neighbours = Array(input.size + 1) { mutableListOf<Pair<Int, Int>>() }
        neighbours[input.size].add(Pair(0, 0))
        for (i in input.size downTo 0) {
            while (neighbours[i].isNotEmpty()) {
                val (x, y) = neighbours[i].last()
                neighbours[i].removeLast()
                if (x == n - 1 && y == n - 1) return input[i]
                cost[y][x] = -1
                for ((nx, ny) in arrayOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1)))
                    if ((0 ..< n).contains(nx) && (0 ..< n).contains(ny) && cost[ny][nx] != -1)
                        neighbours[min(i, cost[ny][nx])].add(Pair(nx, ny))
            }
        }
        return "" // unreachable
    }

    val testInput = readInput("Day18_test")
    check(part1(testInput, 7, 12) == 22)
    check(part2(testInput, 7) == "6,1")

    val input = readInput("Day18")
    part1(input, 71, 1024).println()
    part2(input, 71).println()
}
