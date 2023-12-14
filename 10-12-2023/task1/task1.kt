import java.io.File
import java.io.InputStream

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

fun followPaths(pipeArray: List<List<Char>>, start: List<Int>): Int {
    var lastCoords1 = start
    var lastCoords2 = start
    var startingDirections = findStartingDirections(pipeArray, start)
    var currentCoords1 = startingDirections[0]
    var currentCoords2 = startingDirections[1]
    var count1 = 1
    var count2 = 1

    while (currentCoords1 != currentCoords2) {
        var coordPairs1 = findNextStep(pipeArray, lastCoords1, currentCoords1)
        lastCoords1 = coordPairs1[0]
        currentCoords1 = coordPairs1[1]
        count1 += 1
        if (currentCoords1 == currentCoords2) break
        var coordPairs2 = findNextStep(pipeArray, lastCoords2, currentCoords2)
        lastCoords2 = coordPairs2[0]
        currentCoords2 = coordPairs2[1]
        count2 += 1
    }

    return count2 
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

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var (pipeArray, start) = constructPipeArrayAndFindStart(lineList)

    println(followPaths(pipeArray, start))
}