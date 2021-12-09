fun main() {
    fun getAdjacent(board: List<String>, x: Int, y: Int): Int {
        val width = board[0].length
        val height = board.size

        if (x < 0 || y < 0 || x >= width || y >= height) return Int.MAX_VALUE
        return board[y][x].digitToInt()
    }

    fun compareWithAdjacent(board: List<String>, x: Int, y: Int): Boolean {
        val current = board[y][x].digitToInt()
        val left = getAdjacent(board, x - 1, y)
        val right = getAdjacent(board, x + 1, y)
        val up = getAdjacent(board, x, y - 1)
        val down = getAdjacent(board, x, y + 1)

        return current < left && current < right && current < up && current < down
    }

    fun findLowestPoints(input: List<String>): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()

        for (y in input.indices) {
            for (x in input[0].indices) {
                val isLowest = compareWithAdjacent(input, x, y)
                if (isLowest) points.add(x to y)
            }
        }

        return points
    }

    fun part1(input: List<String>): Int {
        val points = findLowestPoints(input)
        return points.sumOf { (x, y) -> input[y][x].digitToInt() + 1 }
    }

    fun findBasin(board: List<MutableList<Int>>, x: Int, y: Int): Int {
        val width = board[0].size
        val height = board.size

        if (x < 0 || y < 0 || x >= width || y >= height || board[y][x] == 9) return 0
        board[y][x] = 9

        var length = 1

        length += findBasin(board, x - 1, y)
        length += findBasin(board, x + 1, y)
        length += findBasin(board, x, y - 1)
        length += findBasin(board, x, y + 1)

        return length
    }

    fun part2(input: List<String>): Int {
        val board = input.map { line -> line.map { it.digitToInt() }.toMutableList() }

        val points = findLowestPoints(input)
        val sizes = points.map { (x, y) -> findBasin(board, x, y) }.sortedDescending()
        val largest = sizes.subList(0, 3)

        return largest.reduce { acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input)) // 504
    println(part2(input)) // 1558722
}
