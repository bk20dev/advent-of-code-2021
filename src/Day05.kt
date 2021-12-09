typealias Point = Pair<Int, Int>

data class Vector(val x: Int, val y: Int, val tx: Int, val ty: Int)

fun main() {
    fun validRange(a: Int, b: Int): IntRange {
        return if (a < b) a..b else b..a
    }

    fun getPoints(x: Int, y: Int, tx: Int, ty: Int, includeDiagonal: Boolean): Set<Point> {
        val horizontal = validRange(x, tx)
        val vertical = validRange(y, ty)

        when {
            x == tx -> return vertical.map { x to it }.toSet()
            y == ty -> return horizontal.map { it to y }.toSet()
        }

        val result = mutableSetOf<Point>()
        if (includeDiagonal) {
            val up = (if (horizontal.first == x) -1 else 1) * (ty - y) < 0
            val direction = if (up) 1 else -1

            var n = if (horizontal.first == x) y else ty

            horizontal.forEach {
                result.add(it to n)
                n += direction
            }
        }

        return result
    }

    fun readVectors(input: List<String>): List<Vector> {
        val splitter = "\\d+".toRegex()
        return input.map { line ->
            val (x, y, tx, ty) = splitter.findAll(line).map { it.value.toInt() }.toList()
            Vector(x, y, tx, ty)
        }
    }

    fun part1(input: List<String>): Int {
        val vectors = readVectors(input)

        val visited = mutableSetOf<Point>()
        val dangerous = mutableSetOf<Point>()

        for ((x, y, tx, ty) in vectors) {
            val points = getPoints(x, y, tx, ty, includeDiagonal = false)
            for (point in points) {
                val success = visited.add(point)
                if (!success) dangerous.add(point)
            }
        }

        return dangerous.size
    }

    fun part2(input: List<String>): Int {
        val vectors = readVectors(input)

        val visited = mutableSetOf<Point>()
        val dangerous = mutableSetOf<Point>()

        for ((x, y, tx, ty) in vectors) {
            val points = getPoints(x, y, tx, ty, includeDiagonal = true)

            for (point in points) {
                val success = visited.add(point)
                if (!success) dangerous.add(point)
            }
        }


        return dangerous.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input)) // 4655
    println(part2(input)) // 20500
}
