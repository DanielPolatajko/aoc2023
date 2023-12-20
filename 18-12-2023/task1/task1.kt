import java.io.File
import java.io.InputStream
import kotlin.math.*

fun findCorners(input: MutableList<String>): Pair<List<List<Int>>,Int> {
    var corners = mutableListOf<List<Int>>()
    var currentCoord = listOf(0,0)
    var boundaryPoints = 0
    for (line in input) {
        var direction = line.split(" ")[0].single()
        var number = line.split(" ")[1].toString().toInt()
        boundaryPoints += number

        corners.add(currentCoord)
        if (direction == 'R') currentCoord = listOf(currentCoord[0], currentCoord[1]+number)
        else if (direction == 'D') currentCoord = listOf(currentCoord[0] + number, currentCoord[1])
        else if (direction == 'L') currentCoord = listOf(currentCoord[0], currentCoord[1]-number)
        else if (direction == 'U') currentCoord = listOf(currentCoord[0]-number, currentCoord[1])
    }

    return Pair(corners, boundaryPoints)
}

fun shoelaceFormula(corners: List<List<Int>>): Int {
    var cornerCircular = corners.toMutableList()
    cornerCircular.add(cornerCircular[0])
    var cornerPairs = cornerCircular.windowed(2)
    

    return abs(cornerPairs.map { it -> (it[0][0] * it[1][1] - it[0][1] * it[1][0])}.sum() / 2)
}

fun picksTheorem(area: Int, boundaryPoints: Int): Int {
    return area + 1 - boundaryPoints / 2
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var corners = findCorners(lineList)

    println(picksTheorem(shoelaceFormula(corners.first), corners.second)+corners.second)

}