import java.io.File
import java.io.InputStream
import kotlin.math.*

fun calculateCardValue(card: String): Int {
    var numbers = card.split(": ")[1]
    var winners: Set<Int> = numbers
        .split(" | ")[0]
        .split(" ")
        .filter { !it.isNullOrEmpty() }
        .map { it.trim().toInt() }
        .toSet()
    var ours: Set<Int> = numbers
        .split(" | ")[1]
        .split(" ")
        .filter { !it.isNullOrEmpty() }
        .map { it.trim().toInt() }
        .toSet()

    return 2.toFloat().pow(ours.intersect(winners).size - 1).toInt()
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 
    
    var sumList: List<Int> = lineList.map { calculateCardValue(it) }
    println(sumList.sum())
}