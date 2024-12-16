import java.util.PriorityQueue

fun main() {
    fun pathFinding(input: List<String>, countTiles: Boolean): Int {
        val pq = PriorityQueue<Pair<Int, Triple<Int, Int, Int>>> { (lhs, _), (rhs, _) ->
            lhs.compareTo(rhs)
        }
        val dirs = arrayOf(Pair(0, 1), Pair(-1, 0), Pair(0, -1), Pair(1, 0))
        val dist = Array(input.size) { Array(input[0].length) { IntArray(dirs.size) { Int.MAX_VALUE / 2 } } }
        var ei = 0
        var ej = 0
        for (i in input.indices) {
            for (j in input[0].indices) {
                when (input[i][j]) {
                    'S' -> pq.add(Pair(0, Triple(i, j, 0)))
                    'E' -> {
                        ei = i
                        ej = j
                    }
                }
            }
        }
        val adj = Array(input.size) { Array(input[0].length) { Array(dirs.size) { mutableListOf<Triple<Int, Int, Int>>() } } }
        while (pq.isNotEmpty()) {
            val (d, node) = pq.poll()!!
            val (i, j, dir) = node
            if (d > dist[i][j][dir]) continue
            val ni = i + dirs[dir].first
            val nj = j + dirs[dir].second
            if (input[ni][nj] != '#') {
                val nd = d + 1
                if (nd <= dist[ni][nj][dir]) {
                    if (nd < dist[ni][nj][dir]) {
                        pq.add(Pair(nd, Triple(ni, nj, dir)))
                        dist[ni][nj][dir] = nd
                        adj[ni][nj][dir].clear()
                    }
                    adj[ni][nj][dir].add(node)
                }
            }
            val nd = d + 1000
            for (ndir in arrayOf((dir + 1) % dirs.size, (dir + dirs.size - 1) % dirs.size)) {
                if (nd <= dist[i][j][ndir]) {
                    if (nd < dist[i][j][ndir]) {
                        pq.add(Pair(nd, Triple(i, j, ndir)))
                        dist[i][j][ndir] = nd
                        adj[i][j][ndir].clear()
                    }
                    adj[i][j][ndir].add(node)
                }
            }
        }
        val shortest = dist[ei][ej].min()
        return if (countTiles) {
            val vis = Array(input.size) { Array(input[0].length) { BooleanArray(dirs.size) } }
            fun dfs(i: Int, j: Int, dir: Int) {
                if (vis[i][j][dir]) return
                vis[i][j][dir] = true
                for ((ni, nj, ndir) in adj[i][j][dir])
                    dfs(ni, nj, ndir)
            }
            for (dir in dirs.indices)
                if (dist[ei][ej][dir] == shortest)
                    dfs(ei, ej, dir)
            vis.sumOf { row -> row.count { dirs -> dirs.any { it } } }
        } else {
            shortest
        }
    }

    fun part1(input: List<String>): Int {
        return pathFinding(input, false)
    }

    fun part2(input: List<String>): Int {
        return pathFinding(input, true)
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 7036)
    check(part2(testInput) == 45)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
