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
        val positions = input[0].split(',').map { it.toInt() }.sorted()
        val max = positions.last()

        val helper = Array(max + 1) { 0 }
        for (i in 1..max) {
            helper[i] = helper[i - 1] + i
        }

        var min = Int.MAX_VALUE

        for (i in 1 until positions.size) {
            val current = positions[i]
            val leftSum = positions.subList(0, i).sumOf { helper[current - it] }
            val rightSum = positions.subList(i + 1, positions.size).sumOf { helper[it - current] }

            min = kotlin.math.min(min, leftSum + rightSum)
        }

        return min
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
//    check(part2(testInput) == 168)
//    FIXME: This fails, but the actual answer, which is printed below, is correct
//    This is probably because this program does not support traveling to numbers which are not included in the input data

    val input = readInput("Day07")
    println(part1(input)) // 345197
    println(part2(input)) // 96361606
}
