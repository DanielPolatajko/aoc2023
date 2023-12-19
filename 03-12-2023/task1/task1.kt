import java.io.File
import java.io.InputStream

fun findNumbers(array: CharArray) : MutableList<Pair<Int, Int>> {
    var startIndex: Int = -1
    var out = mutableListOf<Pair<Int, Int>>()

    for ((ix, c) in array.withIndex()) {
        if (startIndex == -1 && c.isDigit()) startIndex = ix
        if (startIndex != -1 && !c.isDigit()) {
            out.add(Pair(startIndex, ix -1))
            startIndex = -1
        }
    }
    if (startIndex != -1) {
        out.add(Pair(startIndex, array.size - 1))
    }
    return out
}

fun findSymbolInNumberSurroundings(
    array: Array<CharArray>,
    startIndex: Int, 
    endIndex: Int,
    lineNumber: Int
    ): Boolean {
    for ((ix, line) in array.withIndex()) {
        if (ix >= lineNumber - 1 && ix <= lineNumber + 1) {
            for ((ix2, c) in line.withIndex()) {
                if (ix2 >= startIndex -1 && ix2 <= endIndex +1 && !c.isDigit() && !c.equals('.')) {
                    return true
                }
            }
        }
    }
    return false
}

fun sumPartNumbers(array: Array<CharArray>): Int {
    var out = 0
    for ((ix, line) in array.withIndex()) {
        var numberIndices = findNumbers(line)
        for ((start, end) in numberIndices) {
            if (start > -1 && end > -1) {
                if (findSymbolInNumberSurroundings(array, start, end, ix)) {
                    out += (String(line.sliceArray(IntRange(start, end))).toInt())
                }
            }
        } 
    }
    return out
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 
    
    var charArray: Array<CharArray> = lineList.map { it.toCharArray() }.toTypedArray()
    println(sumPartNumbers(charArray))
}