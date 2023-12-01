import java.io.File
import java.io.InputStream

fun sumFirstAndLast(value: String): Int {
    var digits = value.filter { it.isDigit() }
    var first: String = digits.first().toString()
    var last: String = digits.last().toString()

    return first.plus(last).toInt()
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 
    var sumList: List<Int> = lineList.map{sumFirstAndLast(it)}
    println(sumList.sum())
}