fun main() {
    fun part1(input: List<String>): Long {
        val (init, gates) = input.partition { it.getOrNull(3) == ':' }
        val adj: MutableMap<String, Triple<String, String, Int>> = mutableMapOf()
        for (gate in gates.drop(1)) {
            val (lhs, op, rhs, res) = Regex("([a-z0-9]+) (AND|OR|XOR) ([a-z0-9]+) -> ([a-z0-9]+)")
                .matchEntire(gate)!!.destructured
            adj[res] = Triple(lhs, rhs, when (op) {
                "AND" -> 0
                "OR" -> 1
                else -> 2
            })
        }
        val memo = init.associate {
            val (wire, value) = it.split(": ")
            Pair(wire, value == "1")
        }.toMutableMap()
        fun calc(wire: String): Boolean {
            memo[wire]?.let { return it }
            val (lhs, rhs, op) = adj[wire]!!
            return when (op) {
                0 -> calc(lhs) && calc(rhs)
                1 -> calc(lhs) || calc(rhs)
                else -> calc(lhs) xor calc(rhs)
            }.also { memo[wire] = it }
        }
        return adj.keys.filter { it[0] == 'z' }.sumOf {
            (if (calc(it)) 1L else 0L) shl it.substring(1).toInt()
        }
    }

    fun part2(input: List<String>): String {
        data class Logic(val lhs: String, val op: Int, val rhs: String, val res: String)
        val (_, gates) = input.partition { it.getOrNull(3) == ':' }
        val logic: MutableList<Logic> = mutableListOf()
        val map: MutableMap<String, MutableList<Int>> = mutableMapOf()
        for (gate in gates.drop(1)) {
            val (lhs, op, rhs, res) = Regex("([a-z0-9]+) (AND|OR|XOR) ([a-z0-9]+) -> ([a-z0-9]+)")
                .matchEntire(gate)!!.destructured
            val opcode = when (op) { "AND" -> 0 "OR" -> 1 else -> 2 }
            logic.add(Logic(lhs, opcode, rhs, res))
            map.getOrPut(lhs, ::mutableListOf).add(opcode)
            map.getOrPut(rhs, ::mutableListOf).add(opcode)
        }
        val ans: MutableList<String> = mutableListOf()
        for ((lhs, opcode, _, res) in logic) {
            if (res[0] == 'z') {
                when (opcode) {
                    0 -> ans.add(res)
                    1 -> if (res != "z45") ans.add(res)
                    2 -> if (lhs[0] in "xy" && lhs !in arrayOf("x00", "y00")) ans.add(res)
                }
                continue
            }
            when (opcode) {
                0 -> if (map[res]!![0] != 1 && lhs !in arrayOf("x00", "y00")) ans.add(res)
                1 -> if (map[res]!!.size != 2) ans.add(res)
                else -> if (if (lhs[0] in "xy") map[res]!!.size != 2 else res[0] != 'z') ans.add(res)
            }
        }
        return ans.distinct().sorted().joinToString(",")
    }

    val testInput = readInput("Day24_test")
    check(part1(testInput) == 2024L)

    val input = readInput("Day24")
    part1(input).println()
    part2(input).println()
}
