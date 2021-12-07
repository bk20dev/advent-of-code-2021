fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0].split(',').map { it.toInt() }.sorted()

        var leftCount = 0
        var leftSum = 0
        var rightCount = positions.size - 1
        var rightSum = 0

        for (i in 1 until positions.size) {
            rightSum += positions[i] - positions[0]
        }

        var min = rightSum

        for (i in 1 until positions.size) {
            val difference = positions[i] - positions[i - 1]

            leftCount++
            leftSum += leftCount * difference

            rightSum -= rightCount * difference
            rightCount--

            min = kotlin.math.min(min, leftSum + rightSum)
        }

        return min
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
//    check(part2(testInput) == 168)

//    val input = readInput("Day07")
//    println(part1(input))
//    println(part2(input))
}
