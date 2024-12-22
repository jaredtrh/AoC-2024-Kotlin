fun main() {
    fun next(x: Int): Int {
        var res = x
        res = (res xor (res shl 6)) and 16777215
        res = (res xor (res shr 5)) and 16777215
        res = (res xor (res shl 11)) and 16777215
        return res
    }

    fun part1(input: List<String>): Long {
        return input.map(String::toInt).sumOf {
            var res = it
            repeat(2000) {
                res = next(res)
            }
            res.toLong()
        }
    }

    fun part2(input: List<String>): Int {
        return input.map(String::toInt).fold(mutableMapOf<Int, Int>()) { map, x ->
            var price = x
            var prv = price % 10
            var key = 0
            val vis: MutableSet<Int> = mutableSetOf()
            repeat(2000) {
                price = next(price)
                key = (key * 19 + price % 10 - prv + 9) % 130321
                prv = price % 10
                if (it >= 3 && vis.add(key))
                    map[key] = map.getOrDefault(key, 0) + prv
            }
            map
        }.values.max()
    }

    val testInput1 = readInput("Day22_test1")
    check(part1(testInput1) == 37327623L)
    val testInput2 = readInput("Day22_test2")
    check(part2(testInput2) == 23)

    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
