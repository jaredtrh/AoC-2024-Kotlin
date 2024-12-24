fun main() {
    fun getAdj(input: List<String>): Map<String, List<String>> {
        val adj: MutableMap<String, MutableList<String>> = mutableMapOf()
        for (line in input) {
            val (a, b) = line.split('-')
            adj.getOrPut(a, ::mutableListOf).add(b)
            adj.getOrPut(b, ::mutableListOf).add(a)
        }
        return adj
    }

    fun part1(input: List<String>): Int {
        val adj = getAdj(input)
        val split = input.map { it.split('-') }
        val edge = (split.map { (a, b) -> Pair(a, b) } + split.map { (a, b) -> Pair(b, a) }).toSet()
        val ans: MutableList<Triple<String, String, String>> = mutableListOf()
        for ((com, nbs) in adj) {
            for (i in nbs.indices) {
                for (j in i + 1 ..< nbs.size) {
                    if (edge.contains(Pair(nbs[i], nbs[j])) && (com[0] == 't' || nbs[i][0] == 't' || nbs[j][0] == 't')) {
                        val (a, b, c) = arrayOf(com, nbs[i], nbs[j]).sorted()
                        ans.add(Triple(a, b, c))
                    }
                }
            }
        }
        return ans.distinct().size
    }

    fun part2(input: List<String>): String {
        val adj = getAdj(input)
        for ((com, nbs) in adj) {
            val vis = nbs.toMutableSet()
            vis.add(com)
            val cnts: MutableList<Int> = nbs.map { nb ->
                adj[nb]!!.count { vis.contains(it) }
            }.toMutableList()
            val total = nbs.size + cnts.sum()
            for (i in cnts.indices) {
                if (total - cnts[i] * 2 == nbs.size * (nbs.size - 1)) {
                    val ans = mutableListOf(com)
                    ans.addAll(nbs.take(i))
                    ans.addAll(nbs.drop(i + 1))
                    ans.sort()
                    return ans.joinToString(",")
                }
            }
        }
        return "" // unreachable
    }

    val testInput = readInput("Day23_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == "co,de,ka,ta")

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
