fun main() {
    fun getGroups(input: List<String>): Collection<List<Pair<Int, Int>>> =
        input.indices.flatMap { i ->
            input[i].indices.map { j ->
                Pair(i, j)
            }
        }.groupBy { (i, j) -> input[i][j] }.filter { it.key != '.' }.values

    fun part1(input: List<String>): Int {
        val groups = getGroups(input)
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        for (group in groups) {
            for (i in group.indices) {
                val (ai, aj) = group[i]
                for (j in i + 1 ..< group.size) {
                    val (bi, bj) = group[j]
                    val di = bi - ai
                    val dj = bj - aj
                    val xi = ai - di
                    val xj = aj - dj
                    if (input.indices.contains(xi) && input[0].indices.contains(xj))
                        vis[xi][xj] = true
                    val yi = bi + di
                    val yj = bj + dj
                    if (input.indices.contains(yi) && input[0].indices.contains(yj))
                        vis[yi][yj] = true
                }
            }
        }
        return vis.sumOf { row -> row.count { it } }
    }

    fun part2(input: List<String>): Int {
        val groups = getGroups(input)
        val vis = Array(input.size) { BooleanArray(input[0].length) }
        for (group in groups) {
            for (i in group.indices) {
                val (ai, aj) = group[i]
                for (j in i + 1 ..< group.size) {
                    val (bi, bj) = group[j]
                    val di = bi - ai
                    val dj = bj - aj
                    var xi = ai
                    var xj = aj
                    while (input.indices.contains(xi) && input[0].indices.contains(xj)) {
                        vis[xi][xj] = true
                        xi -= di
                        xj -= dj
                    }
                    var yi = bi
                    var yj = bj
                    while (input.indices.contains(yi) && input[0].indices.contains(yj)) {
                        vis[yi][yj] = true
                        yi += di
                        yj += dj
                    }
                }
            }
        }
        return vis.sumOf { row -> row.count { it } }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
