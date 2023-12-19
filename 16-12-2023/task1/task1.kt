import java.io.File
import java.io.InputStream

fun traverseGrid(input: MutableList<String>): Int {

    var beamMapper = mutableMapOf<Triple<Int,Int,Char>()

    

}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(traverseGrid(it))

}