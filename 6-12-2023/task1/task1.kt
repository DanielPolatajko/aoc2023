import java.io.File
import java.io.InputStream
import kotlin.math.*

fun findNumberOfSpeedSolutions(timeDistancePair: Pair<Int,Int>): Int { 
    var (tInt,dInt) = timeDistancePair
    var t = tInt.toDouble()
    var d = dInt.toDouble()
    var root1 = (t + sqrt((t.pow(2.0) - 4.0 * d))) / 2.0
    var root2 = (t - sqrt((t.pow(2.0) - 4.0 * d))) / 2.0

    var speedSolutions = (floor(root1) - ceil(root2)).toInt() + 1

    if (floor(root1) == root1) speedSolutions -= 1
    if (ceil(root2) == root2) speedSolutions -= 1
    return speedSolutions
}

fun constructTimeDistancePairs(input: MutableList<String>): MutableList<Pair<Int,Int>> {
    var times = input[0].split(": ")[1].trim().split(" ").filter { !it.isNullOrEmpty() }.map { it.trim().toInt() }
    var distanceRecords = input[1].split(": ")[1].trim().split(" ").filter { !it.isNullOrEmpty() }.map { it.trim().toInt() }

    return (times zip distanceRecords).toMutableList()
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(constructTimeDistancePairs(lineList).map { findNumberOfSpeedSolutions(it) }.reduce { acc, it -> acc * it })
}