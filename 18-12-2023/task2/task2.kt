import java.io.File
import java.io.InputStream
import kotlin.math.*

fun findCorners(input: MutableList<String>): Pair<List<List<Long>>,Long> {
    var corners = mutableListOf<List<Long>>()
    var currentCoord = listOf(0L,0L)
    var boundaryPoints = 0L
    var dirMap = mapOf(0 to 'R', 1 to 'D', 2 to 'L', 3 to 'U')
    for (line in input) {
        var hexCode = line.split("#")[1].substring(0, line.split("#")[1].length-1)
        var direction = dirMap[hexCode.last().toString().toInt()]!!
        var number = hexCode.substring(0, hexCode.length-1).toLong(radix=16)
        boundaryPoints += number

        corners.add(currentCoord)
        if (direction == 'R') currentCoord = listOf(currentCoord[0], currentCoord[1]+number)
        else if (direction == 'D') currentCoord = listOf(currentCoord[0] + number, currentCoord[1])
        else if (direction == 'L') currentCoord = listOf(currentCoord[0], currentCoord[1]-number)
        else if (direction == 'U') currentCoord = listOf(currentCoord[0]-number, currentCoord[1])
    }

    return Pair(corners, boundaryPoints)
}

fun shoelaceFormula(corners: List<List<Long>>): Long {
    var cornerCircular = corners.toMutableList()
    cornerCircular.add(cornerCircular[0])
    var cornerPairs = cornerCircular.windowed(2)
    

    return abs(cornerPairs.map { it -> (it[0][0] * it[1][1] - it[0][1] * it[1][0])}.sum() / 2L)
}

fun picksTheorem(area: Long, boundaryPoints: Long): Long {
    return area + 1L - boundaryPoints / 2L
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var corners = findCorners(lineList)

    println(picksTheorem(shoelaceFormula(corners.first), corners.second)+corners.second)

}