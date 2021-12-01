fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }

        var count = 0
        for (i in 1 until numbers.size) {
            if (numbers[i] > numbers[i - 1]) count++
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }

        var sum = numbers[0] + numbers[1] + numbers[2]
        var count = 0
        for (i in 3 until numbers.size) {
            val updated = sum - numbers[i - 3] + numbers[i]
            if(updated > sum) count++
            sum = updated
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
