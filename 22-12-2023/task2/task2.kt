import java.io.File
import java.io.InputStream
import java.util.*

fun constructSandGrid(input: MutableList<String>): Pair<MutableList<MutableList<MutableList<Int>>>, MutableMap<Int, Pair<List<Int>, List<Int>>>> {
    var sandGrid = mutableListOf<MutableList<MutableList<Int>>>()
    var blockMap = mutableMapOf<Int, Pair<List<Int>, List<Int>>>()

    var xDim = 0
    var yDim = 0
    var zDim = 0

    for (line in input) {
        var end = line.split("~")[1].split(",").map { it.toString().toInt() }
        if (end[0] > xDim) xDim = end[0]
        if (end[1] > yDim) yDim = end[1]
        if (end[2] > zDim) zDim = end[2]
    }

    for (i in 0..xDim) {
        sandGrid.add(mutableListOf<MutableList<Int>>())
        for (j in 0..yDim) {
            sandGrid[i].add(mutableListOf<Int>())
            for (k in 0..zDim) {
                sandGrid[i][j].add(0)
            }
        }
    }

    for ((ix,line) in input.withIndex()) {
        var start = line.split("~")[0].split(",").map { it.toString().toInt() }
        var end = line.split("~")[1].split(",").map { it.toString().toInt() }

        blockMap[ix+1] = Pair(start, end)

        for (i in start[0]..end[0]) {
            for (j in start[1]..end[1]) {
                for (k in start[2]..end[2]) {
                    sandGrid[i][j][k] = ix+1
                }
            }
        }
    }

    return Pair(sandGrid, blockMap)
}

fun compressSandGrid(sandGrid: MutableList<MutableList<MutableList<Int>>>, blockMap: MutableMap<Int, Pair<List<Int>, List<Int>>>): Triple<MutableList<MutableList<MutableList<Int>>>, MutableMap<Int, Pair<List<Int>, List<Int>>>, Int> {
    var prevGrid = sandGrid.map {it -> it.map { it2 -> it2.toMutableList() }.toMutableList() }.toMutableList()
    var newMap: MutableMap<Int, Pair<List<Int>, List<Int>>>
    var fallenList = mutableSetOf<Int>()
    while (true) {
        var newGrid = sandGrid.map {it -> it.map { it2 -> it2.toMutableList() }.toMutableList() }.toMutableList()
        var sortedBricks = blockMap.toList().map { (k,v) -> Pair(k,Pair(v.first.toList(), v.second.toList()))}.toMap().toMutableMap().toList().sortedBy { (_,v) -> v.first[2] }.toMap()
        newMap = sortedBricks.toMutableMap()
        for (entry in sortedBricks.toList().map { (k,v) -> Pair(k,Pair(v.first.toList(), v.second.toList()))}.toMap().entries.iterator()) {
            var key = entry.key
            var (start, end) = entry.value
            if (start[2]-1 >= 0) {
                drop@while (start[2]-1 >= 0) {
                    for (i in start[0]..end[0]) {
                        for (j in start[1]..end[1]) {
                            if (newGrid[i][j][start[2]-1] != 0) {
                                break@drop
                            }
                        }
                    }
                    // drop
                    for (i in start[0]..end[0]) {
                        for (j in start[1]..end[1]) {
                            newGrid[i][j][start[2]-1] = key
                            newGrid[i][j][end[2]] = 0
                        }
                    }
                    start = listOf(start[0], start[1], start[2]-1)
                    end = listOf(end[0], end[1], end[2]-1)
                    newMap[key] = Pair(start, end)
                    fallenList.add(key)
                }
            }
        }
        if (newGrid == prevGrid) break
        prevGrid = newGrid
    }

    return Triple(prevGrid, newMap, fallenList.size)
}

fun countFallingBricks(sandGrid: MutableList<MutableList<MutableList<Int>>>, blockMap: MutableMap<Int, Pair<List<Int>, List<Int>>>): Int {
    var count = 0
    for (entry in blockMap.entries.iterator()) {
        var checkGrid = sandGrid.map {it -> it.map { it2 -> it2.toMutableList() }.toMutableList() }.toMutableList()
        var tempMap = blockMap.toList().map { (k,v) -> Pair(k,Pair(v.first.toList(), v.second.toList()))}.toMap().toMutableMap()
        var (start, end) = entry.value

        for (i in start[0]..end[0]) {
            for (j in start[1]..end[1]) {
                for (k in start[2]..end[2]) {
                    checkGrid[i][j][k] = 0
                }
            }
        } 
        tempMap.remove(entry.key)


        var (_, _, fallenCount) = compressSandGrid(checkGrid, tempMap)

        count += fallenCount
    }
    return count
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 


    var (sandGridSnapshot, blockMapSnapshot) = constructSandGrid(lineList)
    var (sandGrid, blockMap) = compressSandGrid(sandGridSnapshot, blockMapSnapshot)
    println(countFallingBricks(sandGrid, blockMap))

}