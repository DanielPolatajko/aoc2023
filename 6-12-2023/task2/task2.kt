import java.io.File
import java.io.InputStream
import kotlin.math.*

fun findNumberOfSpeedSolutions(timeDistancePair: Pair<Long,Long>): Long { 
    var (tLong,dLong) = timeDistancePair
    var t = tLong.toDouble()
    var d = dLong.toDouble()
    var root1 = (t + sqrt((t.pow(2.0) - 4.0 * d))) / 2.0
    var root2 = (t - sqrt((t.pow(2.0) - 4.0 * d))) / 2.0

    var speedSolutions = (floor(root1) - ceil(root2)).toLong() + 1

    if (floor(root1) == root1) speedSolutions -= 1
    if (ceil(root2) == root2) speedSolutions -= 1
    return speedSolutions
}

fun findSolution(input: MutableList<String>): Long {
    var time = input[0].split(": ")[1].trim().replace(" ", "").toLong()
    var distanceRecord = input[1].split(": ")[1].trim().replace(" ", "").toLong()
    
    return findNumberOfSpeedSolutions(Pair(time, distanceRecord))
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(findSolution(lineList))
}