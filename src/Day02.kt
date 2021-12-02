fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.forEach {
            val (direction, value) = it.split(' ')
            val x = value.toInt()

            when (direction) {
                "up" -> depth -= x
                "down" -> depth += x
                else -> horizontal += x
            }
        }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.forEach {
            val (direction, value) = it.split(' ')
            val x = value.toInt()

            when (direction) {
                "down" -> aim += x
                "up" -> aim -= x
                else -> {
                    horizontal += x
                    depth += aim * x
                }
            }
        }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
