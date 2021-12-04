fun main() {
    fun loadBoards(input: List<String>): List<Array<Int>> {
        val result = mutableListOf<Array<Int>>()
        val numberMatcher = """\d+""".toRegex()

        repeat((input.size - 1) / 6) { block ->
            val board = Array(25) { 0 }

            for (i in 1..5) {
                val line = input[block * 6 + i + 1]
                val numbers = numberMatcher.findAll(line).map { it.value.toInt() }.toList()
                numbers.toTypedArray().copyInto(board, (i - 1) * 5)
            }

            result.add(board)
        }

        return result
    }

    fun removeNumber(board: Array<Int>, number: Int): Int {
        repeat(25) { i ->
            if (board[i] == number) {
                board[i] = -1
                return@removeNumber i
            }
        }

        return -1
    }

    fun checkWin(board: Array<Int>, position: Int): Boolean {
        val posX = position % 5
        val posY = position / 5

        // Check column
        for (y in 0 until 5) {
            if (board[y * 5 + posX] != -1) break
            if (y == 4) return true
        }

        // Check row
        for (x in 0 until 5) {
            if (board[posY * 5 + x] != -1) break
            if (x == 4) return true
        }

        return false
    }

    fun sumUnmarked(board: Array<Int>): Int {
        return board.fold(0) { acc, i -> if (i == -1) acc else acc + i }
    }

    fun part1(input: List<String>): Int {
        val guesses = input[0].split(',').map { it.toInt() }
        val boards = loadBoards(input)

        for (guess in guesses) {
            for (board in boards) {
                val position = removeNumber(board, guess)
                if (position == -1) continue

                if (checkWin(board, position)) {
                    val sum = sumUnmarked(board)
                    return sum * guess
                }
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val guesses = input[0].split(',').map { it.toInt() }
        val boards = loadBoards(input).toMutableList()
        val wins = mutableSetOf<Array<Int>>()

        val count = boards.size

        for (guess in guesses) {
            for (board in boards) {
                if (board in wins) continue

                val position = removeNumber(board, guess)
                if (position == -1) continue

                if (checkWin(board, position)) {
                    wins.add(board)
                    if (wins.size == count) {
                        val sum = sumUnmarked(board)
                        return sum * guess
                    }
                }

            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
