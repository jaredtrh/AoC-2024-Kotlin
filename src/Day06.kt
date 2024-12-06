fun main() {
    val dirs = arrayOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

    fun findGuard(input: List<String>): Triple<Int, Int, Int> {
        for ((i, row) in input.withIndex())
            for ((j, c) in row.withIndex())
                if (c != '.' && c != '#')
                    return Triple(i, j, "^>v<".indexOf(input[i][j]))
        return Triple(0, 0, 0) // unreachable
    }

    fun part1(input: List<String>): Int {
        var (i, j, d) = findGuard(input)
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        while (input.indices.contains(i) && input[0].indices.contains(j)) {
            if (input[i][j] == '#') {
                i -= dirs[d].first
                j -= dirs[d].second
                d = (d + 1) % dirs.size
                continue
            }
            vis[i][j] = true
            i += dirs[d].first
            j += dirs[d].second
        }
        return vis.sumOf { row -> row.count { it } }
    }

    fun part2(input: List<String>): Int {
        var (gi, gj, gd) = findGuard(input)
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        val been = Array(input.size) { Array(input[0].length) { BooleanArray(dirs.size) } }
        var ans = 0
        while (true) {
            if (input[gi][gj] == '#') {
                gi -= dirs[gd].first
                gj -= dirs[gd].second
                gd = (gd + 1) % dirs.size
                continue
            }
            gi += dirs[gd].first
            gj += dirs[gd].second
            if (!(input.indices.contains(gi) && input[0].indices.contains(gj))) break
            if (!vis[gi][gj]) {
                vis[gi][gj] = true
                if (
                    DeepRecursiveFunction<Triple<Int, Int, Int>, Boolean> { (i, j, d) ->
                        if (!(input.indices.contains(i) && input[0].indices.contains(j)))
                            return@DeepRecursiveFunction false
                        if (been[i][j][d]) return@DeepRecursiveFunction true
                        been[i][j][d] = true
                        val ni = i + dirs[d].first
                        val nj = j + dirs[d].second
                        val res = if (
                            input.indices.contains(ni) && input[0].indices.contains(nj) &&
                            input[ni][nj] == '#' || (ni == gi && nj == gj)
                        )
                            callRecursive(Triple(i, j, (d + 1) % dirs.size))
                        else
                            callRecursive(Triple(ni, nj, d))
                        been[i][j][d] = false
                        return@DeepRecursiveFunction res
                    }(Triple(gi - dirs[gd].first, gj - dirs[gd].second, (gd + 1) % dirs.size))
                ) ans += 1
            }
        }
        return ans
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
