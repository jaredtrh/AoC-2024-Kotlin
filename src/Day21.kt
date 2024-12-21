import kotlin.math.min

fun main() {
    fun calc(code: String, directionalCount: Int): Long {
        val numeric = arrayOf(
            "789",
            "456",
            "123",
            " 0A"
        )
        val directional = arrayOf(
            " ^A",
            "<v>"
        )
        fun getPos(keypad: Array<String>, c: Char): Pair<Int, Int> {
            for (i in keypad.indices) {
                val j = keypad[i].indexOf(c)
                if (j != -1)
                    return Pair(i, j)
            }
            return Pair(0, 0) // unreachable
        }
        fun typeNumeric(a: Char, b: Char, flip: Boolean): String {
            val (ai, aj) = getPos(numeric, a)
            val (bi, bj) = getPos(numeric, b)
            val ver = if (bi >= ai) "v".repeat(bi - ai) else "^".repeat(ai - bi)
            val hor = if (bj >= aj) ">".repeat(bj - aj) else "<".repeat(aj - bj)
            if (aj == 0 && bi == numeric.lastIndex)
                return hor + ver + 'A'
            if (ai == numeric.lastIndex && bj == 0)
                return ver + hor + 'A'
            return (if (flip) hor + ver else ver + hor) + 'A'
        }
        fun typeDirectional(a: Char, b: Char, flip: Boolean): String {
            val (ai, aj) = getPos(directional, a)
            val (bi, bj) = getPos(directional, b)
            val ver = if (bi >= ai) "v".repeat(bi - ai) else "^".repeat(ai - bi)
            val hor = if (bj >= aj) ">".repeat(bj - aj) else "<".repeat(aj - bj)
            if (aj == 0 && bi == 0)
                return hor + ver + 'A'
            if (ai == 0 && bj == 0)
                return ver + hor + 'A'
            return (if (flip) hor + ver else ver + hor) + 'A'
        }
        val memo: MutableMap<Triple<Char, Char, Int>, Long> = mutableMapOf()
        fun rec(a: Char, b: Char, directionalCount: Int): Long {
            if (directionalCount == 0) return 1
            val key = Triple(a, b, directionalCount)
            memo[key]?.let { return it }
            var noFlip = 0L
            for ((c, d) in ("A" + typeDirectional(a, b, false)).zipWithNext())
                noFlip += rec(c, d, directionalCount - 1)
            var flip = 0L
            for ((c, d) in ("A" + typeDirectional(a, b, true)).zipWithNext())
                flip += rec(c, d, directionalCount - 1)
            return min(noFlip, flip).also { memo[key] = it }
        }
        var ans = 0L
        for ((a, b) in "A$code".zipWithNext()) {
            var noFlip = 0L
            for ((c, d) in ("A" + typeNumeric(a, b, false)).zipWithNext())
                noFlip += rec(c, d, directionalCount)
            var flip = 0L
            for ((c, d) in ("A" + typeNumeric(a, b, true)).zipWithNext())
                flip += rec(c, d, directionalCount)
            ans += min(noFlip, flip)
        }
        return ans
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { calc(it, 2) * it.dropLast(1).toInt() }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { calc(it, 25) * it.dropLast(1).toInt() }
    }

    val testInput = readInput("Day21_test")
    check(part1(testInput) == 126384L)

    val input = readInput("Day21")
    part1(input).println()
    part2(input).println()
}
