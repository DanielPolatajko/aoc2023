import java.io.File
import java.io.InputStream
import kotlin.math.*

fun findStartingDirections(pipeArray: List<List<Char>>, start: List<Int>): List<List<Int>> {
    var startingDirections = mutableListOf(start, start)
    if (start[0] > 0){
        if (pipeArray[start[0]-1][start[1]] == '|' || pipeArray[start[0]-1][start[1]] == '7' || pipeArray[start[0]-1][start[1]] == 'F') {
            startingDirections[0] = listOf(start[0]-1, start[1])
        }
    }
    if (start[0] < pipeArray.size-1) {
        if (pipeArray[start[0]+1][start[1]] == '|' || pipeArray[start[0]+1][start[1]] == 'L' || pipeArray[start[0]+1][start[1]] == 'J') {
            if (startingDirections[0] != start) startingDirections[1] = listOf(start[0]+1, start[1]) else startingDirections[0] = listOf(start[0]+1, start[1])
        }
    }
    if (start[1] > 0) {
        if (pipeArray[start[0]][start[1]-1] == '-' || pipeArray[start[0]][start[1]-1] == 'F' || pipeArray[start[0]][start[1]-1] == 'L') {
            if (startingDirections[0] != start) startingDirections[1] = listOf(start[0], start[1]-1) else startingDirections[0] = listOf(start[0], start[1]-1)
        }
    }
    if (start[1] < pipeArray[0].size - 1) {
        if (pipeArray[start[0]][start[1]+1] == '-' || pipeArray[start[0]][start[1]+1] == '7' || pipeArray[start[0]][start[1]+1] == 'J') {
            if (startingDirections[0] != start) startingDirections[1] = listOf(start[0], start[1]+1) else startingDirections[0] = listOf(start[0], start[1]+1)
        }
    }
    return startingDirections
}

fun findNextStep(pipeArray: List<List<Char>>, lastCoords: List<Int>, currentCoords: List<Int>): List<List<Int>> {
    var currentPipe = pipeArray[currentCoords[0]][currentCoords[1]]
    if (currentCoords[1] > lastCoords[1]){
        if (currentPipe == '7') return listOf(currentCoords, listOf(currentCoords[0]+1, currentCoords[1]))
        if (currentPipe == 'J') return listOf(currentCoords, listOf(currentCoords[0]-1, currentCoords[1]))
        if (currentPipe == '-') return listOf(currentCoords, listOf(currentCoords[0], currentCoords[1]+1))
    }
    if (currentCoords[1] < lastCoords[1]){
        if (currentPipe == 'F') return listOf(currentCoords, listOf(currentCoords[0]+1, currentCoords[1]))
        if (currentPipe == '-') return listOf(currentCoords, listOf(currentCoords[0], currentCoords[1]-1))
        if (currentPipe == 'L') return listOf(currentCoords, listOf(currentCoords[0]-1, currentCoords[1]))
    }
    if (currentCoords[0] < lastCoords[0]){
        if (currentPipe == '|') return listOf(currentCoords, listOf(currentCoords[0]-1, currentCoords[1]))
        if (currentPipe == '7') return listOf(currentCoords, listOf(currentCoords[0], currentCoords[1]-1))
        if (currentPipe == 'F') return listOf(currentCoords, listOf(currentCoords[0], currentCoords[1]+1))
    }
    if (currentCoords[0] > lastCoords[0]){
        if (currentPipe == '|') return listOf(currentCoords, listOf(currentCoords[0]+1, currentCoords[1]))
        if (currentPipe == 'L') return listOf(currentCoords, listOf(currentCoords[0], currentCoords[1]+1))
        if (currentPipe == 'J') return listOf(currentCoords, listOf(currentCoords[0], currentCoords[1]-1)) 
    }
    return listOf(lastCoords, currentCoords)
}

fun followPathAndCountCorners(pipeArray: List<List<Char>>, start: List<Int>): Pair<List<List<Int>>, Int> {
    var lastCoords = start
    var startingDirections = findStartingDirections(pipeArray, start)
    var currentCoords = startingDirections[0]
    var corners = mutableListOf<List<Int>>()
    var count = 1

    if (startingDirections[0][0] - startingDirections[1][0] != 0 && startingDirections[0][1] - startingDirections[1][1] != 0) corners.add(start)

    while (currentCoords != start) {
        if (pipeArray[currentCoords[0]][currentCoords[1]] in listOf('7', 'L', 'F', 'J')) corners.add(currentCoords)
        var coordPairs = findNextStep(pipeArray, lastCoords, currentCoords)
        lastCoords = coordPairs[0]
        currentCoords = coordPairs[1]
        count += 1
    }

    return Pair(corners, count)
}

fun constructPipeArrayAndFindStart(input: MutableList<String>): Pair<List<List<Char>>,List<Int>> {
    var pipeArray = mutableListOf<List<Char>>()
    var start = listOf(0,0)
    for ((ix,line) in input.withIndex()) {
        if ('S' in line) start = listOf(ix, line.indexOf('S'))
        pipeArray.add(line.toList())
    }
    return Pair(pipeArray, start)
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

    var (pipeArray, start) = constructPipeArrayAndFindStart(lineList)

    var (corners, boundaryPoints) = followPathAndCountCorners(pipeArray, start)

    var area = shoelaceFormula(corners)

    println(picksTheorem(area, boundaryPoints))
}