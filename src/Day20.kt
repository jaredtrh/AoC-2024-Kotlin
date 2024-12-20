fun main() {
    fun calc(input: List<String>, duration: Int, save: Int): Int {
        val sq = ArrayDeque<Pair<Int, Int>>()
        val eq = ArrayDeque<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[0].indices) {
                when (input[i][j]) {
                    'S' -> sq.add(Pair(i, j))
                    'E' -> eq.add(Pair(i, j))
                }
            }
        }
        val dist = Array(input.size) { IntArray(input[0].length) { -1 } }
        dist[sq.first().first][sq.first().second] = 0
        while (sq.isNotEmpty()) {
            val (i, j) = sq.removeLast()
            for ((ni, nj) in arrayOf(Pair(i - 1, j), Pair(i + 1, j), Pair(i, j - 1), Pair(i, j + 1))) {
                if (
                    input.indices.contains(ni) && input[0].indices.contains(nj) &&
                    input[ni][nj] != '#' && dist[ni][nj] == -1
                ) {
                    dist[ni][nj] = dist[i][j] + 1
                    sq.add(Pair(ni, nj))
                }
            }
        }
        val fastest = dist[eq.first().first][eq.first().second]
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        vis[eq.first().first][eq.first().second] = true
        var ans = 0
        var edist = 0
        while (eq.isNotEmpty()) {
            repeat(eq.size) {
                val (i, j) = eq.removeLast()
                for (cdist in 1 .. duration) {
                    for (k in 0 ..< cdist) {
                        for ((ni, nj) in arrayOf(
                            Pair(i - cdist + k, j + k), Pair(i + k, j + cdist - k),
                            Pair(i + cdist - k, j - k), Pair(i - k, j - cdist + k)
                        )) {
                            if (input.indices.contains(ni) && input[0].indices.contains(nj) && dist[ni][nj] != -1) {
                                val time = dist[ni][nj] + cdist + edist
                                if (fastest - time >= save)
                                    ans += 1
                            }
                        }
                    }
                }
                for ((ni, nj) in arrayOf(Pair(i - 1, j), Pair(i + 1, j), Pair(i, j - 1), Pair(i, j + 1))) {
                    if (
                        input.indices.contains(ni) && input[0].indices.contains(nj) &&
                        input[ni][nj] != '#' && !vis[ni][nj]
                    ) {
                        vis[ni][nj] = true
                        eq.add(Pair(ni, nj))
                    }
                }
            }
            edist += 1
        }
        return ans
    }

    fun part1(input: List<String>, save: Int): Int {
        return calc(input, 2, save)
    }

    fun part2(input: List<String>, save: Int): Int {
        return calc(input, 20, save)
    }

    val testInput = readInput("Day20_test")
    check(part1(testInput, 2) == 44)
    check(part2(testInput, 50) == 285)

    val input = readInput("Day20")
    part1(input, 100).println()
    part2(input, 100).println()
}
