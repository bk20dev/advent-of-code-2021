import kotlin.math.pow

fun main() {
    /** Converts an array with binary number to a decimal number */
    fun List<Boolean>.toIntFromBinary(): Int {
        var value = 0

        for (i in this.indices) {
            val position = this.size - i - 1
            val digit = if (this[position]) 1 else 0
            value += digit * 2.toDouble().pow(i.toDouble()).toInt()
        }

        return value
    }

    /** Counts ones the in indicated column */
    fun countOnes(input: List<String>, column: Int): Int {
        return input.fold(0) { acc, line ->
            if (line[column] == '1') acc + 1
            else acc
        }
    }

    /** Counts zeros and ones in each column */
    fun countBits(input: List<String>): Pair<List<Int>, List<Int>> {
        val digits = input[0].length

        val ones = MutableList(digits) { 0 }
        repeat(digits) { ones[it] = countOnes(input, it) }

        val zeros = ones.map { input.size - it }
        return ones to zeros
    }

    fun part1(input: List<String>): Int {
        val digits = input[0].length
        val (ones, zeros) = countBits(input)

        val number = Array(digits) { false }
        repeat(digits) {
            number[it] = zeros[it] <= ones[it]
        }

        val gamma = number.toBooleanArray().toList().toIntFromBinary()
        val epsilon = number.map { !it }.toIntFromBinary()

        return gamma * epsilon
    }

    /** Finds rating of oxygen or co2 */
    fun findRating(input: List<String>, oxygen: Boolean): Int {
        fun count(lines: List<String>, index: Int, comparator: (Int, Int) -> Boolean): String {
            val ones = countOnes(lines, index)
            val zeros = lines.size - ones

            val rating = if (comparator(ones, zeros)) '1' else '0'
            val passing = lines.filter { it[index] == rating }

            if (passing.size <= 1) return passing[0]

            val newIndex = if (index + 1 >= lines[0].length) 0 else index + 1
            return count(passing, newIndex, comparator)
        }

        val comparator: (Int, Int) -> Boolean =
            if (oxygen) { ones, zeros -> ones >= zeros } else { ones, zeros -> ones < zeros }
        val number = count(input, 0, comparator)

        return number
            .split("")
            .subList(1, number.length + 1)
            .map { it == "1" }
            .toIntFromBinary()
    }

    fun part2(input: List<String>): Int {
        val oxygen = findRating(input, true)
        val co2 = findRating(input, false)

        return oxygen * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
