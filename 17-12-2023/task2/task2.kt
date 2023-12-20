import java.io.File
import java.io.InputStream
import kotlin.math.*
import java.util.PriorityQueue

val compareByDistance = Comparator<Pair<Int, Triple<Int,Int,Char>>> { a,b ->
    a.first - b.first
}

fun pathSearch(input: MutableList<MutableList<Int>>) : Int {
    var unvisited = mutableListOf<Triple<Int,Int, Char>>()
    var distancesQueue = PriorityQueue<Pair<Int,Triple<Int,Int, Char>>>(compareByDistance)
    var distances = mutableMapOf<Triple<Int,Int, Char>, Int>()
    for (i in 0..input.size-1) {
        for (j in 0..input[0].size-1) {
            for (d in listOf('H', 'V')) {
                unvisited.add(Triple(i,j, d))
                if (i ==0 && j == 0) {
                    distancesQueue.add(Pair(0, Triple(i,j, d)))
                    distances[Triple(i,j,d)] = 0
                } else {
                    distances[Triple(i,j,d)] = Int.MAX_VALUE
                }
            }
        }
    }
    var currentDistance: Int
    while (!distancesQueue.isEmpty()) {

        var nextInQueue = distancesQueue.remove()
        var current = nextInQueue.second
        currentDistance = nextInQueue.first

        if (current !in unvisited) continue

        unvisited = unvisited.filter { it != current }.toMutableList()
        var x = current.first
        var y = current.second
        var weight = currentDistance
        if (current.third == 'V') {
            for (step in 1..10) {
                var newX = x - step
                if (newX >= 0) {
                    var node = Triple(newX, y, 'H')
                    weight += input[newX][y]
                    if (node in unvisited && step >= 4) {
                        distancesQueue.add(Pair(weight, node))
                        distances[node] = min(distances[node]!!, weight)
                    }
                }
            }
            weight = currentDistance
            for (step in 1..10) {
                var newX = x + step
                if (newX < input.size) {
                    var node = Triple(newX, y, 'H')
                    weight += input[newX][y]
                    if (node in unvisited && step >= 4) {
                        distancesQueue.add(Pair(weight, node))
                        distances[node] = min(distances[node]!!, weight)
                    }
                }
            }
        } else {
            weight = currentDistance
            for (step in 1..10) {
                var newY = y - step
                if (newY >= 0) {
                    var node = Triple(x, newY, 'V')
                    weight += input[x][newY]
                    if (node in unvisited && step >= 4) {
                        distancesQueue.add(Pair(weight, node))
                        distances[node] = min(distances[node]!!, weight)
                    }
                }
            }
            weight = currentDistance
            for (step in 1..10) {
                var newY = y + step
                if (newY < input[0].size) {
                    var node = Triple(x, newY, 'V')
                    weight += input[x][newY]
                    if (node in unvisited && step >= 4) {
                        distancesQueue.add(Pair(weight, node))
                        distances[node] = min(distances[node]!!, weight)
                    }
                }
            }
        }
    }
    return distances.filter { (k, _) -> (k.first == input.size - 1 && k.second == input[0].size - 1) }.values.min()
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var blockList = lineList.map{ it.toList().map { it2 -> it2.toString().toInt() }.toMutableList() }.toMutableList()

    println(pathSearch(blockList))

}