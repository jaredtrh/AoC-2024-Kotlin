fun main() {
    fun part1(input: List<String>): Int {
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        return input.indices.sumOf { i ->
            input[0].indices.sumOf inner@ { j ->
                if (input[i][j] != '0') return@inner 0
                var cnt = 0
                val undo: MutableList<Pair<Int, Int>> = mutableListOf()
                fun dfs(i: Int, j: Int) {
                    if (vis[i][j]) return
                    vis[i][j] = true
                    undo.add(Pair(i, j))
                    if (input[i][j] == '9') {
                        cnt += 1
                        return
                    }
                    val next = (input[i][j].digitToInt() + 1).digitToChar()
                    if (i > 0 && input[i - 1][j] == next)
                        dfs(i - 1, j)
                    if (i < input.lastIndex && input[i + 1][j] == next)
                        dfs(i + 1, j)
                    if (j > 0 && input[i][j - 1] == next)
                        dfs(i, j - 1)
                    if (j < input[0].lastIndex && input[i][j + 1] == next)
                        dfs(i, j + 1)
                }
                dfs(i, j)
                for ((ui, uj) in undo)
                    vis[ui][uj] = false
                cnt
            }
        }
    }

    fun part2(input: List<String>): Int {
        val rating = Array(input.size) { IntArray(input[0].length) }
        return input.indices.sumOf { i ->
            input[0].indices.sumOf inner@ { j ->
                if (input[i][j] != '0') return@inner 0
                fun dfs(i: Int, j: Int): Int {
                    if (rating[i][j] != 0) return rating[i][j]
                    if (input[i][j] == '9') {
                        rating[i][j] = 1
                        return 1
                    }
                    val next = (input[i][j].digitToInt() + 1).digitToChar()
                    if (i > 0 && input[i - 1][j] == next)
                        rating[i][j] += dfs(i - 1, j)
                    if (i < input.lastIndex && input[i + 1][j] == next)
                        rating[i][j] += dfs(i + 1, j)
                    if (j > 0 && input[i][j - 1] == next)
                        rating[i][j] += dfs(i, j - 1)
                    if (j < input[0].lastIndex && input[i][j + 1] == next)
                        rating[i][j] += dfs(i, j + 1)
                    return rating[i][j]
                }
                dfs(i, j)
            }
        }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
