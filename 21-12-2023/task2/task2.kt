import java.io.File
import java.io.InputStream
import java.util.*
import java.lang.Math.floorMod

fun traverseGarden(input: MutableList<String>, stepCount: Int): Long {
    var start = Triple(0,0,0)
    for ((ix,line) in input.withIndex()) {
        for ((jx,c) in line.withIndex()) {
            if (c == 'S') start = Triple(ix,jx,0)
        }
    }

    var q: Queue<Triple<Int,Int,Int>> = LinkedList()
    var count = 0L
    var seen = mutableListOf<Pair<Int,Int>>()

    q.add(start)

    while (!q.isEmpty()) {
        var current = q.remove()
        if (Pair(current.first, current.second) in seen) continue
        if ((current.third % 2) == (stepCount % 2)) {
            count += 1
        }
        if (current.third < stepCount) {
            var neighbours = listOf(
                Pair(current.first-1, current.second),
                Pair(current.first+1, current.second),
                Pair(current.first, current.second-1),
                Pair(current.first, current.second+1)
            )
            for (n in neighbours) {
                if (input[floorMod(n.first, input.size)][floorMod(n.second, input[0].length)] != '#' && Pair(n.first, n.second) !in seen) {
                    q.add(Triple(n.first, n.second, current.third+1))
                }
            }
        }
        seen.add(Pair(current.first, current.second))
    }
    return count
}

fun curveFit(points: MutableList<Long>, x: Long): Long {
    var xd = x.toDouble()
    var l0 = ((xd - 196.0) * (xd-327.0)) / ((65.0 - 196.0) * (65.0 - 327.0))
    var l1 = ((xd - 65.0) * (xd-327.0)) / ((196.0 - 65.0) * (196.0 - 327.0))
    var l2 = ((xd - 196.0) * (xd-65.0)) / ((327.0 - 196.0) * (327.0 - 65.0))

    var y = points[0].toDouble() * l0 + points[1].toDouble() * l1 + points[2].toDouble() * l2

    return y.toLong()
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 


    var linePoints = mutableListOf<Long>()

    for (i in listOf(65, 196, 327)) {
        linePoints.add(traverseGarden(lineList, i))
    }

    println(curveFit(linePoints, 26501365L))



}