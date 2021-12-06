fun main() {
    fun simulate(input: List<String>, days: Int): Long {
        val timers = Array(9) { 0L }

        val read = input[0].split(',').map { it.toInt() }
        for (entry in read) {
            timers[entry]++
        }

        repeat(days) {
            val mature = timers[0]
            for (i in 0 until 8) {
                timers[i] = timers[i + 1]
            }

            timers[6] += mature
            timers[8] = mature
        }

        return timers.reduce { acc, i -> acc + i }
    }

    fun part1(input: List<String>): Long {
        return simulate(input, 80)
    }

    fun part2(input: List<String>): Long {
        return simulate(input, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
