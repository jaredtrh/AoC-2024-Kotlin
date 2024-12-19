fun main() {
    data class TrieNode(var children: MutableMap<Char, Int>, var term: Boolean)

    fun buildTrie(patterns: List<String>): List<TrieNode> {
        val trie = mutableListOf(TrieNode(mutableMapOf(), false))
        for (pattern in patterns) {
            var id = 0
            for (c in pattern) {
                trie[id].children[c]?.let {
                    id = it
                } ?: run {
                    trie[id].children[c] = trie.size
                    id = trie.size
                    trie.add(TrieNode(mutableMapOf(), false))
                }
            }
            trie[id].term = true
        }
        return trie
    }

    fun part1(input: List<String>): Int {
        val trie = buildTrie(input[0].split(", "))
        return input.drop(2).count { design ->
            val dp = BooleanArray(design.length + 1)
            dp[design.length] = true
            for (i in design.length downTo 0) {
                var id = 0
                for (j in i ..< design.length) {
                    id = trie[id].children[design[j]] ?: break
                    if (trie[id].term && dp[j + 1]) {
                        dp[i] = true
                        break
                    }
                }
            }
            dp[0]
        }
    }

    fun part2(input: List<String>): Long {
        val trie = buildTrie(input[0].split(", "))
        return input.drop(2).sumOf { design ->
            val dp = LongArray(design.length + 1)
            dp[design.length] = 1
            for (i in design.length downTo 0) {
                var id = 0
                for (j in i ..< design.length) {
                    id = trie[id].children[design[j]] ?: break
                    if (trie[id].term)
                        dp[i] += dp[j + 1]
                }
            }
            dp[0]
        }
    }

    val testInput = readInput("Day19_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
