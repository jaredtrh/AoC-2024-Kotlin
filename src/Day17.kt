fun main() {
    fun getProgram(input: List<String>): Pair<Int, List<Int>> =
        Pair(
            Regex("Register A: (\\d+)").matchEntire(input[0])!!.groupValues[1].toInt(),
            Regex("Program: (.+)").matchEntire(input[4])!!.groupValues[1].split(',').map(String::toInt)
        )

    fun execute(initA: Int, program: List<Int>): String {
        var a = initA
        var b = 0
        var c = 0
        fun combo(operand: Int): Int =
            when (operand) {
                in 0..3 -> operand
                4 -> a
                5 -> b
                6 -> c
                else -> 0 // unreachable
            }
        val output: MutableList<Int> = mutableListOf()
        var ip = 0
        while (ip < program.size) {
            val operand = program[ip + 1]
            when (program[ip]) {
                0 -> a = a shr combo(operand)
                1 -> b = b xor operand
                2 -> b = combo(operand) and 7
                3 -> if (a != 0) ip = operand - 2
                4 -> b = b xor c
                5 -> output.add(combo(operand) and 7)
                6 -> b = a shr combo(operand)
                else -> c = a shr combo(operand)
            }
            ip += 2
        }
        return output.joinToString(",")
    }

    fun calc(a: Long, x: Int, y: Int): Int =
        ((a xor (a shr ((a.toInt() and 7) xor x))).toInt() xor x xor y) and 7

    fun part1(input: List<String>, test: Boolean): String {
        var (a, program) = getProgram(input)
        return if (test) {
            execute(a, program)
        } else {
            val x = program[3]
            val y = program[program.drop(6).indexOf(1) + 1]
            val output: MutableList<Int> = mutableListOf()
            while (a != 0) {
                output.add(calc(a.toLong(), x, y))
                a = a shr 3
            }
            output.joinToString(",")
        }
    }

    fun part2(input: List<String>, test: Boolean): Long {
        val (_, program) = getProgram(input)
        return if (test) {
            val programStr = program.joinToString(",")
            var ans = 0
            while (execute(ans, program) != programStr) ans += 1
            ans.toLong()
        } else {
            val x = program[3]
            val y = program[program.drop(6).indexOf(1) + 1]
            fun dfs(i: Int, a: Long): Long? {
                if (i == -1) return a
                for (b in (a shl 3) .. (a shl 3) + 7)
                    if (calc(b, x, y) == program[i])
                        dfs(i - 1, b)?.let { return it }
                return null
            }
            dfs(program.lastIndex, 0)!!
        }
    }

    val testInput1 = readInput("Day17_test1")
    check(part1(testInput1, true) == "4,6,3,5,6,3,5,2,1,0")
    val testInput2 = readInput("Day17_test2")
    check(part2(testInput2, true) == 117440L)

    val input = readInput("Day17")
    part1(input, false).println()
    part2(input, false).println()
}
