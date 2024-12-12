fun main() {
    fun part1(input: List<String>): Int {
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        return input.indices.sumOf { i ->
            input[0].indices.sumOf { j ->
                var area = 0
                var perimeter = 0
                val c = input[i][j]
                fun dfs(i: Int, j: Int) {
                    if (!(input.indices.contains(i) && input[0].indices.contains(j) && input[i][j] == c)) {
                        perimeter += 1
                        return
                    }
                    if (vis[i][j]) return
                    vis[i][j] = true
                    area += 1
                    dfs(i - 1, j)
                    dfs(i + 1, j)
                    dfs(i, j - 1)
                    dfs(i, j + 1)
                }
                dfs(i, j)
                area * perimeter
            }
        }
    }

    fun part2(input: List<String>): Int {
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        return input.indices.sumOf { i ->
            input[0].indices.sumOf { j ->
                var area = 0
                var sides = 0
                val c = input[i][j]
                fun dfs(i: Int, j: Int) {
                    if (!(input.indices.contains(i) && input[0].indices.contains(j) && input[i][j] == c) || vis[i][j])
                        return
                    vis[i][j] = true
                    area += 1
                    for ((di, dj) in arrayOf(Pair(-1, -1), Pair(1, -1), Pair(-1, 1), Pair(1, 1))) {
                        val ni = i + di
                        val nj = j + dj
                        if (
                            ((!input.indices.contains(ni) || input[ni][j] != c) &&
                                    (!input[0].indices.contains(nj) || input[i][nj] != c)) ||
                            (input.indices.contains(ni) && input[0].indices.contains(nj) &&
                                    input[ni][j] == c && input[i][nj] == c && input[ni][nj] != c)
                        ) sides += 1
                    }
                    dfs(i - 1, j)
                    dfs(i + 1, j)
                    dfs(i, j - 1)
                    dfs(i, j + 1)
                }
                dfs(i, j)
                area * sides
            }
        }
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
