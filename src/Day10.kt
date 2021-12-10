import java.util.*

fun main() {
    val brackets = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )

    val errorScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )

    fun validateLine(line: String): Int {
        val stack = Stack<Char>()

        line.forEach {
            if (it in brackets.keys) {
                stack.add(it)
            } else {
                val opening = stack.pop()
                val closing = brackets[opening]

                if (closing != it) {
                    return errorScores[it]!!
                }
            }
        }

        return 0
    }

    val completionScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4,
    )

    fun complete(line: String): Long {
        val stack = Stack<Char>()

        line.forEach {
            if (it in brackets.keys) {
                stack.add(it)
            } else {
                stack.pop()
            }
        }

        val left = stack.toList().reversed()
        val scores = left.map {
            val closing = brackets[it]
            completionScores[closing]!!
        }

        return scores.fold(0) { acc, i -> acc * 5 + i }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf(::validateLine)
    }

    fun part2(input: List<String>): Long {
        val scores = input.filter { validateLine(it) == 0 }.map(::complete).sorted()
        return scores[scores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input)) // 388713
    println(part2(input)) // 3539961434
}
