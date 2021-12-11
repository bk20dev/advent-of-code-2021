fun main() {
    fun convert(input: List<String>): List<Int> {
        val output = mutableListOf<Int>()

        for (line in input) {
            val numbers = line.toList().map { it.digitToInt() }
            output.addAll(numbers)
        }

        return output
    }

    fun findAdjacentIndexes(current: Int): List<Int> {
        val out = mutableListOf<Int>()

        val row = current / 10
        val column = current % 10

        for (y in row - 1..row + 1) {
            for (x in column - 1..column + 1) {
                val position = y * 10 + x

                if (position == current) continue
                if (x !in 0..9 || y !in 0..9) continue

                out.add(position)
            }
        }

        return out
    }

    fun simulate(board: MutableList<Int>): Int {
        board.forEachIndexed { index, i -> board[index] = i + 1 }

        val visited = mutableSetOf<Int>()
        val positions = mutableListOf<Int>()

        repeat(100) {
            if (board[it] > 9) {
                positions.add(it)
                visited.add(it)
            }
        }

        while (positions.isNotEmpty()) {
            val position = positions.removeFirst()

            val adjacent = findAdjacentIndexes(position)
            for (element in adjacent) board[element]++
            val flashing = adjacent.filter { board[it] > 9 }.filter { !visited.contains(it) }

            positions.addAll(flashing)
            visited.addAll(flashing)
        }

        visited.forEach { board[it] = 0 }

        return visited.size
    }

    fun part1(input: List<String>): Int {
        val board = convert(input).toMutableList()

        var sum = 0
        repeat(100) {
            sum += simulate(board)
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val board = convert(input).toMutableList()

        var i = 1
        while (true) {
            if (simulate(board) == 100) return i
            i++
        }
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input)) // 1723
    println(part2(input)) // 327
}
