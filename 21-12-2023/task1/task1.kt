import java.io.File
import java.io.InputStream
import java.util.*

fun traverseGarden(input: MutableList<String>): Int {
    var start = Triple(0,0,0)
    for ((ix,line) in input.withIndex()) {
        for ((jx,c) in line.withIndex()) {
            if (c == 'S') start = Triple(ix,jx,0)
        }
    }

    var q: Queue<Triple<Int,Int,Int>> = LinkedList()
    var count = 0
    var seen = mutableListOf<Pair<Int,Int>>()

    q.add(start)

    while (!q.isEmpty()) {
        var current = q.remove()
        if (Pair(current.first, current.second) in seen) continue
        if (current.third % 2 == 0) {
            count += 1
        }
        if (current.third < 64) {
            var neighbours = listOf(
                Pair(current.first-1, current.second),
                Pair(current.first+1, current.second),
                Pair(current.first, current.second-1),
                Pair(current.first, current.second+1)
            )
            for (n in neighbours) {
                if (n.first >=0 && n.second >=0 && n.first < input.size && n.second < input[0].length) {
                    if (input[n.first][n.second] != '#' && Pair(n.first, n.second) !in seen) {
                        q.add(Triple(n.first, n.second, current.third+1))
                    }
                }
            }
        }
        seen.add(Pair(current.first, current.second))
    }
    return count
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 


    println(traverseGarden(lineList))

}