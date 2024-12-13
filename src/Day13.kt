fun main() {
    fun calc(ax: Long, ay: Long, bx: Long, by: Long, px: Long, py: Long): Long {
        // [[ax bx]
        //  [ay by]]
        val det = ax * by - bx * ay
        val cx = by * px - bx * py
        val cy = ax * py - ay * px
        if (cx % det != 0L || cy % det != 0L) return 0
        val x = cx / det
        val y = cy / det
        if (x < 0 || y < 0) return 0
        return x * 3 + y
    }

    fun part1(input: List<String>): Long {
        return input.chunked(4).sumOf { (a, b, prize) ->
            val (ax, ay) = Regex("Button A: X\\+(\\d+), Y\\+(\\d+)").matchEntire(a)!!.groups
                .drop(1).map { it!!.value.toLong() }
            val (bx, by) = Regex("Button B: X\\+(\\d+), Y\\+(\\d+)").matchEntire(b)!!.groups
                .drop(1).map { it!!.value.toLong() }
            val (px, py) = Regex("Prize: X=(\\d+), Y=(\\d+)").matchEntire(prize)!!.groups
                .drop(1).map { it!!.value.toLong() }
            calc(ax, ay, bx, by, px, py)
        }
    }

    fun part2(input: List<String>): Long {
        return input.chunked(4).sumOf { (a, b, prize) ->
            val (ax, ay) = Regex("Button A: X\\+(\\d+), Y\\+(\\d+)").matchEntire(a)!!.groups
                .drop(1).map { it!!.value.toLong() }
            val (bx, by) = Regex("Button B: X\\+(\\d+), Y\\+(\\d+)").matchEntire(b)!!.groups
                .drop(1).map { it!!.value.toLong() }
            val (px, py) = Regex("Prize: X=(\\d+), Y=(\\d+)").matchEntire(prize)!!.groups
                .drop(1).map { it!!.value.toLong() }
            calc(ax, ay, bx, by, px + 10000000000000, py + 10000000000000)
        }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480L)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
