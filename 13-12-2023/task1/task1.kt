import java.io.File
import java.io.InputStream
import kotlin.math.*

fun findReflectionSize(map: List<List<Char>>): Int {
    var offset: Int

    for (ix in 1..map.size) {
        offset = min(ix, map.size-ix)
       
        var top = map.subList(ix-offset, ix)
        var bottom = map.subList(ix, ix + offset)
        if (top == bottom.reversed() && offset != 0) {
            return 100 * (ix)
        }
    }
    for (ix2 in 1..map[0].size) {
        offset = min(ix2, map[0].size-ix2)
    
        var left = map.map { it.subList(ix2-offset, ix2) }
        var right = map.map { it.subList(ix2, ix2 + offset).reversed() }
        if (left == right && offset != 0) {
            return ix2
        }
    }
    return 0
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    lineList.add("")

    var tempList = mutableListOf<String>()

    while (lineList.indexOf("") > -1) {
        tempList.add(lineList.subList(0, lineList.indexOf("")).joinToString("\n"))
        lineList = lineList.subList(lineList.indexOf("")+1, lineList.size)
    }

    var mapList = tempList.map { it -> it.split("\n").map { it2 -> it2.toList() } }

    println(mapList.map { it -> findReflectionSize(it) }.sum())

}