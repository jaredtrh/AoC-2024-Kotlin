fun main() {
    fun part1(input: List<String>): Int {
        return input.count {
            val adj = it.split(' ').map(String::toInt).zipWithNext()
            adj.all { (a, b) -> (1 .. 3).contains(b - a) } ||
                    adj.all { (a, b) -> (1 .. 3).contains(a - b) }
        }
    }

    fun part2(input: List<String>): Int {
        fun safe(report: List<Int>, range: IntRange): Boolean {
            val adj = report.zipWithNext()
            val total = adj.count { (a, b) -> !range.contains(b - a) }
            run {
                var violations = total
                if (!range.contains(adj.first().second - adj.first().first)) violations -= 1
                if (violations == 0) return true
            }
            for ((lhs, rhs) in adj.zipWithNext()) {
                val (a, b) = lhs
                val (c, d) = rhs
                var violations = total
                if (!range.contains(b - a)) violations -= 1
                if (!range.contains(d - c)) violations -= 1
                if (!range.contains(d - a)) violations += 1
                if (violations == 0) return true
            }
            run {
                var violations = total
                if (!range.contains(adj.last().second - adj.last().first)) violations -= 1
                if (violations == 0) return true
            }
            return false
        }
        return input.count {
            val report = it.split(' ').map(String::toInt)
            safe(report, 1 .. 3) || safe(report, -3 .. -1)
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
