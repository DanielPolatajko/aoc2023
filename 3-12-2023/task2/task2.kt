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

fun findGearIndexInNumberSurroundings(
    array: Array<CharArray>,
    startIndex: Int, 
    endIndex: Int,
    lineNumber: Int
    ): Pair<Int, Int> {
    for ((ix, line) in array.withIndex()) {
        if (ix >= lineNumber - 1 && ix <= lineNumber + 1) {
            for ((ix2, c) in line.withIndex()) {
                if (ix2 >= startIndex -1 && ix2 <= endIndex +1 && c.equals('*')) {
                    return Pair(ix, ix2)
                }
            }
        }
    }
    return Pair(-1,-1)
}

fun sumGearRatios(array: Array<CharArray>): Int {
    var out = 0
    var gearMap = mutableMapOf<Pair<Int, Int>, Int>()
    for ((ix, line) in array.withIndex()) {
        var numberIndices = findNumbers(line)
        for ((start, end) in numberIndices) {
            if (start > -1 && end > -1) {
                var gearIndex = findGearIndexInNumberSurroundings(array, start, end, ix)
                if (gearIndex != Pair(-1, -1)) {
                    if (gearMap.containsKey(gearIndex)) {
                        out += gearMap[gearIndex]!!.times(String(line.sliceArray(IntRange(start, end))).toInt())
                        gearMap.remove(gearIndex)
                    } else {
                        gearMap.put(gearIndex, (String(line.sliceArray(IntRange(start, end))).toInt()))
                    }
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
    println(sumGearRatios(charArray))
}