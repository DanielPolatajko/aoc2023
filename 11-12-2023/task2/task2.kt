import java.io.File
import java.io.InputStream
import kotlin.math.*

fun starDistance(firstStar: List<Int>, secondStar: List<Int>, expandingSpace: List<List<Int>>): Long {
    var rowDistance = abs(secondStar[0] - firstStar[0]) + 999999 * expandingSpace[0].filter { it < max(secondStar[0], firstStar[0]) && it > min(secondStar[0], firstStar[0]) }.size
    var colDistance = abs(secondStar[1] - firstStar[1]) + 999999 *expandingSpace[1].filter { it < max(secondStar[1], firstStar[1]) && it > min(secondStar[1], firstStar[1]) }.size
    return (rowDistance + colDistance).toLong()
}

fun findExpandingSpaceAndStars(galaxy: MutableList<String>): Pair<List<List<Int>>, List<List<Int>>> {
    var nonExpandingRows = mutableListOf<Int>()
    var nonExpandingCols = mutableListOf<Int>()
    var stars = mutableListOf<List<Int>>()
    for ((ix, line) in galaxy.withIndex()) {
        var starIndices = line.mapIndexed { idx, it -> if (it == '#') idx else -1 }.filter { it > -1 }
        for (star in starIndices) {
            nonExpandingCols.add(star)
            nonExpandingRows.add(ix)
            stars.add(listOf(ix, star))
        }
    }

    var expandingCols = (0..galaxy.size-1).toSet().subtract(nonExpandingCols.toSet())
    var expandingRows = (0..galaxy[0].length-1).toSet().subtract(nonExpandingRows.toSet())

    return Pair(listOf(expandingRows.toList(), expandingCols.toList()), stars)
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var (expandingSpace, stars) = findExpandingSpaceAndStars(lineList)

    println(stars.map { i -> stars.map{ j -> starDistance(i,j,expandingSpace) }.sum() }.sum() / 2.toLong())
}