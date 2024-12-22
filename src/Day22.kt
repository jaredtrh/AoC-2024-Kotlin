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
        val consec = input.map(String::toInt).map { x ->
            val map: MutableMap<Int, Int> = mutableMapOf()
            var price = x
            var prv = price % 10
            var key = 0
            repeat(2000) {
                price = next(price)
                key = (key * 19 + price % 10 - prv + 9) % 130321
                prv = price % 10
                if (it >= 3) map.putIfAbsent(key, prv)
            }
            map
        }
        return consec.map(Map<Int, Int>::keys).flatten().distinct().maxOf {
            consec.sumOf { map -> map.getOrDefault(it, 0) }
        }
    }

    val testInput1 = readInput("Day22_test1")
    check(part1(testInput1) == 37327623L)
    val testInput2 = readInput("Day22_test2")
    check(part2(testInput2) == 23)

    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
