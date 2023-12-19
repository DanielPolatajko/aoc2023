import java.io.File
import java.io.InputStream
import kotlin.math.*

fun hashAlgorithm(input: String): Int {
    var output = 0
    for (c in input) {
        output += c.code
        output *= 17
        output = output % 256
    }
    return output

}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var seqList = lineList[0].split(",")

    println(seqList.map{ hashAlgorithm(it) }.sum())

}