import java.util.*

fun main() {
    fun part1(input: List<String>): Long {
        val ids: MutableList<Int> = mutableListOf()
        for ((i, c) in input[0].withIndex()) {
            val id = if (i % 2 == 0) i / 2 else -1
            ids.addAll((1 .. c.digitToInt()).map { id })
        }
        var j = ids.lastIndex
        for (i in ids.indices) {
            if (ids[i] == -1) {
                while (i < j && ids[j] == -1) j -= 1
                if (i == j) break
                ids[i] = ids[j]
                j -= 1
            } else {
                if (i == j) break
            }
        }
        return ids.take(j + 1).withIndex().sumOf { (i, id) -> i * id.toLong() }
    }

    fun part2(input: List<String>): Long {
        val disk = input[0].map(Char::digitToInt)
        val before = IntArray(input[0].length / 2)
        var sum = 0
        val free: Array<SortedSet<Int>> = Array(10) { sortedSetOf() }
        for (i in (1 ..< disk.size).step(2)) {
            sum += disk[i - 1]
            before[i / 2] = sum
            sum += disk[i]
            free[disk[i]].add(i)
        }
        if (disk.size % 2 == 0)
            sum -= disk[disk.size - 3] + disk[disk.size - 2]
        val used = IntArray(before.size)
        var ans = 0L
        for (i in (2 ..< disk.size).step(2).reversed()) {
            if (i < disk.lastIndex)
                free[disk[i + 1] - used[i / 2]].remove(i + 1)
            val min = (disk[i] ..< free.size).minByOrNull { free[it].firstOrNull() ?: disk.size }!!
            free[min].firstOrNull()?.let {
                ans += i / 2L * (
                        (before[it / 2] + disk[i]).toLong() * (before[it / 2] + disk[i] - 1) / 2 -
                                before[it / 2].toLong() * (before[it / 2] - 1) / 2)
                before[it / 2] += disk[i]
                free[min].remove(it)
                used[it / 2] += disk[i]
                free[disk[it] - used[it / 2]].add(it)
            } ?: run {
                ans += i / 2L * ((sum + disk[i]).toLong() * (sum + disk[i] - 1) / 2 - sum.toLong() * (sum - 1) / 2)
            }
            sum -= disk[i - 2] + disk[i - 1]
        }
        return ans
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
