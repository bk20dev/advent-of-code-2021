typealias Board = Array<Array<Boolean>>
typealias Step = Pair<Boolean, Int>

fun main() {
    fun getSteps(input: List<String>) = input.map {
        val (instruction, value) = it.split('=')
        val isX = instruction.last() == 'x'
        isX to value.toInt()
    }

    fun getPoints(input: List<String>) = input.map { line ->
        val (a, b) = line.split(',').map { it.toInt() }
        a to b
    }

    fun read(input: List<String>): Pair<Board, List<Step>> {
        val gap = input.indexOf("")

        val steps = getSteps(input.slice(gap + 1 until input.size))
        val points = getPoints(input.slice(0 until gap))

        // Width and height is based on folding instructions,
        // because the paper can be blank on edges.
        // If we check the max coordinate on a single axis
        // we would eventually get an error.
        val (vertical, horizontal) = steps.partition { it.first }
        val width = vertical.maxOf { it.second * 2 + 1 }
        val height = horizontal.maxOf { it.second * 2 + 1 }

        val board = Array(height) { Array(width) { false } }
        points.forEach { (x, y) -> board[y][x] = true }

        return board to steps
    }

    fun foldHorizontally(board: Board, y: Int) {
        val width = board[0].size

        repeat(y) {
            val other = y * 2 - it
            repeat(width) { x ->
                board[it][x] = board[it][x] || board[other][x]
            }
        }
    }

    fun foldVertically(board: Board, x: Int) {
        val height = board.size

        repeat(x) {
            val other = x * 2 - it
            repeat(height) { y ->
                board[y][it] = board[y][it] || board[y][other]
            }
        }
    }

    fun countFilled(board: Board, width: Int, height: Int): Int {
        var sum = 0
        repeat(height) { y ->
            repeat(width) { x ->
                sum += if (board[y][x]) 1 else 0
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        val (board, steps) = read(input)

        var height = board.size
        var width = board[0].size

        val (vertically, at) = steps[0]

        if (vertically) {
            foldVertically(board, at)
            width = at
        } else {
            foldHorizontally(board, at)
            height = at
        }

        return countFilled(board, width, height)
    }

    fun part2(input: List<String>) {
        val (board, steps) = read(input)

        var height = board.size
        var width = board[0].size

        steps.forEach { (vertically, at) ->
            if (vertically) {
                foldVertically(board, at)
                width = at
            } else {
                foldHorizontally(board, at)
                height = at
            }
        }

        println(board.copyOfRange(0, height)
            .joinToString("\n") { line -> line.copyOfRange(0, width).joinToString("") { if (it) "#" else " " } })
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input)) // 790
    part2(input)
//    ###   ##  #  # #### ###  ####   ##  ##
//    #  # #  # #  #    # #  # #       # #  #
//    #  # #    ####   #  ###  ###     # #
//    ###  # ## #  #  #   #  # #       # #
//    #    #  # #  # #    #  # #    #  # #  #
//    #     ### #  # #### ###  #     ##   ##
}
