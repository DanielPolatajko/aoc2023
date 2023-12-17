import java.io.File
import java.io.InputStream
import kotlin.math.*

fun tiltInDirection(
    input: MutableList<MutableList<Char>>,
    direction: Char,
): MutableList<MutableList<Char>> {
    var output = input
    if (direction == 'S') output = output.reversed().toMutableList()
    if (direction == 'E') output = output.map{ it.reversed().toMutableList() }.toMutableList()

    for ((ix, row) in output.toMutableList().withIndex()) {
        for ((jx, rock) in row.withIndex()) {
            if (rock == 'O') {
                if (direction == 'N' || direction == 'S') {
                    var newRow = ix-1
                    while (newRow >= 0 && output[newRow][jx] !in listOf('#', 'O')) {
                        output[newRow+1][jx] = output[newRow][jx]
                        output[newRow][jx] = 'O'
                        newRow -= 1
                    }

                } else if (direction == 'W' || direction == 'E') {
                    var newCol = jx-1
                    while (newCol >= 0 && output[ix][newCol] !in listOf('#', 'O')) {
                        output[ix][newCol+1] = output[ix][newCol]
                        output[ix][newCol] = 'O'
                        newCol -= 1
                    }

                }
            }
        }
    }
    if (direction == 'S') output = output.reversed().toMutableList()
    if (direction == 'E') output = output.map{ it.reversed().toMutableList() }.toMutableList()
    return output
}

fun calculateLoad(tiltedRocks: MutableList<MutableList<Char>>): Int {
    var load = 0
    for ((ix,row) in tiltedRocks.withIndex()) {
        load += row.count{ it == 'O' } * (tiltedRocks.size - ix)
    }
    return load
}

fun buildCycleMap (input: MutableList<MutableList<Char>>): MutableList<String> { 
    var cycleMap = mutableListOf<String>()
    var nextPositions = input
    while (nextPositions.map { it.joinToString() }.joinToString("\n") !in cycleMap) {
        cycleMap.add(nextPositions.map { it.joinToString() }.joinToString("\n"))
        for (direction in listOf('N', 'W', 'S', 'E')) {
            nextPositions = tiltInDirection(
                nextPositions,
                direction
            )
        }
    }
    cycleMap.add(nextPositions.map { it.joinToString() }.joinToString("\n"))
    return cycleMap
}

fun findPositionFromCycleMap(
    cycleMap: MutableList<MutableList<MutableList<Char>>>,
    cycleNumber: Int
): MutableList<MutableList<Char>> {
    var lead = cycleMap.indexOf(cycleMap.last())
    var actualCycles = cycleMap.subList(lead, cycleMap.size-1)
    return actualCycles[(cycleNumber - (lead)) % (actualCycles.size)]
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var mapList = lineList.map { it.toMutableList() }.toMutableList()

    var cycleMap = buildCycleMap(mapList).map {it -> it.split("\n").map { it2 -> it2.toMutableList() }.toMutableList() }.toMutableList()

    var finalPosition = findPositionFromCycleMap(cycleMap, 1000000000)

    println(calculateLoad(finalPosition))

}