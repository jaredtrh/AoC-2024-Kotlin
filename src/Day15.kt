fun main() {
    fun findRobot(grid: List<List<Char>>): Pair<Int, Int> {
        for (i in grid.indices)
            for (j in grid[0].indices)
                if (grid[i][j] == '@')
                    return Pair(i, j)
        return Pair(0, 0) // unreachable
    }

    fun calc(grid: List<List<Char>>, c: Char): Int =
        grid.indices.sumOf { ii ->
            grid[0].indices.sumOf { jj ->
                if (grid[ii][jj] == c)
                    ii * 100 + jj
                else
                    0
            }
        }

    fun part1(input: List<String>): Int {
        val (gridStr, moveLns) = input.partition { it.firstOrNull() == '#' }
        val grid = gridStr.map(String::toMutableList)
        val moves = moveLns.joinToString("")
        var (i, j) = findRobot(grid)
        for (move in moves) {
            when (move) {
                '^' -> {
                    var x = i - 1
                    while (grid[x][j] == 'O') x -= 1
                    if (grid[x][j] == '.') {
                        grid[x][j] = 'O'
                        grid[i][j] = '.'
                        i -= 1
                        grid[i][j] = '@'
                    }
                }
                'v' -> {
                    var x = i + 1
                    while (grid[x][j] == 'O') x += 1
                    if (grid[x][j] == '.') {
                        grid[x][j] = 'O'
                        grid[i][j] = '.'
                        i += 1
                        grid[i][j] = '@'
                    }
                }
                '<' -> {
                    var x = j - 1
                    while (grid[i][x] == 'O') x -= 1
                    if (grid[i][x] == '.') {
                        grid[i][x] = 'O'
                        grid[i][j] = '.'
                        j -= 1
                        grid[i][j] = '@'
                    }
                }
                else -> {
                    var x = j + 1
                    while (grid[i][x] == 'O') x += 1
                    if (grid[i][x] == '.') {
                        grid[i][x] = 'O'
                        grid[i][j] = '.'
                        j += 1
                        grid[i][j] = '@'
                    }
                }
            }
        }
        return calc(grid, 'O')
    }

    fun part2(input: List<String>): Int {
        val (gridStr, moveLns) = input.partition { it.firstOrNull() == '#' }
        val grid = gridStr.map { row ->
            row.flatMap {
                when (it) {
                    '#' -> "##"
                    'O' -> "[]"
                    '.' -> ".."
                    else -> "@."
                }.toList()
            }.toMutableList()
        }
        val moves = moveLns.joinToString("")
        var (i, j) = findRobot(grid)
        for (move in moves) {
            when (move) {
                '^' -> {
                    val push: MutableList<Pair<Int, Int>> = mutableListOf()
                    fun up(i: Int, j: Int): Boolean {
                        if (grid[i][j] != '[') return true
                        grid[i][j] = 'X'
                        val res = grid[i - 1][j] != '#' && grid[i - 1][j + 1] != '#' &&
                                up(i - 1, j - 1) && up(i - 1, j) && up(i - 1, j + 1)
                        push.add(Pair(i, j))
                        return res
                    }
                    if (grid[i - 1][j] != '#' && up(i - 1, j - 1) && up(i - 1, j)) {
                        for ((ii, jj) in push) {
                            grid[ii - 1][jj] = '['
                            grid[ii - 1][jj + 1] = ']'
                            grid[ii][jj] = '.'
                            grid[ii][jj + 1] = '.'
                        }
                        grid[i][j] = '.'
                        i -= 1
                        grid[i][j] = '@'
                    } else {
                        for ((ii, jj) in push)
                            grid[ii][jj] = '['
                    }
                }
                'v' -> {
                    val push: MutableList<Pair<Int, Int>> = mutableListOf()
                    fun down(i: Int, j: Int): Boolean {
                        if (grid[i][j] != '[') return true
                        grid[i][j] = 'X'
                        val res = grid[i + 1][j] != '#' && grid[i + 1][j + 1] != '#' &&
                                down(i + 1, j - 1) && down(i + 1, j) && down(i + 1, j + 1)
                        push.add(Pair(i, j))
                        return res
                    }
                    if (grid[i + 1][j] != '#' && down(i + 1, j - 1) && down(i + 1, j)) {
                        for ((ii, jj) in push) {
                            grid[ii + 1][jj] = '['
                            grid[ii + 1][jj + 1] = ']'
                            grid[ii][jj] = '.'
                            grid[ii][jj + 1] = '.'
                        }
                        grid[i][j] = '.'
                        i += 1
                        grid[i][j] = '@'
                    } else {
                        for ((ii, jj) in push)
                            grid[ii][jj] = '['
                    }
                }
                '<' -> {
                    val push: MutableList<Pair<Int, Int>> = mutableListOf()
                    fun left(i: Int, j: Int): Boolean {
                        if (grid[i][j] != '[') return true
                        grid[i][j] = 'X'
                        val res = grid[i][j - 1] != '#' && left(i, j - 2)
                        push.add(Pair(i, j))
                        return res
                    }
                    if (grid[i][j - 1] != '#' && left(i, j - 2)) {
                        for ((ii, jj) in push) {
                            grid[ii][jj - 1] = '['
                            grid[ii][jj] = ']'
                            grid[ii][jj + 1] = '.'
                        }
                        grid[i][j] = '.'
                        j -= 1
                        grid[i][j] = '@'
                    } else {
                        for ((ii, jj) in push)
                            grid[ii][jj] = '['
                    }
                }
                else -> {
                    val push: MutableList<Pair<Int, Int>> = mutableListOf()
                    fun right(i: Int, j: Int): Boolean {
                        if (grid[i][j] != '[') return true
                        grid[i][j] = 'X'
                        val res = grid[i][j + 2] != '#' && right(i, j + 2)
                        push.add(Pair(i, j))
                        return res
                    }
                    if (grid[i][j + 1] != '#' && right(i, j + 1)) {
                        for ((ii, jj) in push) {
                            grid[ii][jj] = '.'
                            grid[ii][jj + 1] = '['
                            grid[ii][jj + 2] = ']'
                        }
                        grid[i][j] = '.'
                        j += 1
                        grid[i][j] = '@'
                    } else {
                        for ((ii, jj) in push)
                            grid[ii][jj] = '['
                    }
                }
            }
        }
        return calc(grid, '[')
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 10092)
    check(part2(testInput) == 9021)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
