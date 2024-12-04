fun main() {
    fun part1(input: List<String>): Int {
        val xmas = "XMAS"
        val withIndex = xmas.withIndex()
        var ans = 0
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (j + xmas.length <= input.size && withIndex.all { (k, c) -> input[i][j + k] == c }) ans += 1
                if (j >= xmas.lastIndex && withIndex.all { (k, c) -> input[i][j - k] == c }) ans += 1
                if (i + xmas.length <= input.size && withIndex.all { (k, c) -> input[i + k][j] == c }) ans += 1
                if (i >= xmas.lastIndex && withIndex.all { (k, c) -> input[i - k][j] == c }) ans += 1
                if (i + xmas.length <= input.size && j + xmas.length <= input.size &&
                    withIndex.all { (k, c) -> input[i + k][j + k] == c }) ans += 1
                if (i >= xmas.lastIndex && j + xmas.length <= input.size &&
                    withIndex.all { (k, c) -> input[i - k][j + k] == c }) ans += 1
                if (i + xmas.length <= input.size && j >= xmas.lastIndex &&
                    withIndex.all { (k, c) -> input[i + k][j - k] == c }) ans += 1
                if (i >= xmas.lastIndex && j >= xmas.lastIndex &&
                    withIndex.all { (k, c) -> input[i - k][j - k] == c }) ans += 1
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        return (1 ..< input.lastIndex).sumOf { i ->
            (1 ..< input[0].lastIndex).count { j ->
                input[i][j] == 'A' && "MMSSMMS".contains(
                    "${input[i - 1][j - 1]}${input[i - 1][j + 1]}${input[i + 1][j + 1]}${input[i + 1][j - 1]}"
                )
            }
        }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
