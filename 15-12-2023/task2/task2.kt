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

fun hashmapAlgorithm(input: List<String>): Int {
    var hashMap = mutableMapOf<Int,MutableList<Pair<String,Int>>>()
    for (i in 0..256) hashMap[i] = mutableListOf<Pair<String,Int>>()
    for (code in input) {
        if ('=' in code) {
            var mapCode = code.split("=")[0]
            var codeValue = hashAlgorithm(mapCode)
            var focalLength = code.split("=")[1].toString().toInt()
            var mapTo = Pair(mapCode, focalLength)
            if (hashMap[codeValue]!!.filter{ (it.first == mapCode) }.size > 0 ) {
                var lensPosition = hashMap[codeValue]!!.indexOfFirst({it.first == mapCode})
                hashMap[codeValue]!![lensPosition] = mapTo
            } 
            else hashMap[codeValue]!!.add(mapTo)
        }
        else {
            var mapCode = code.split("-")[0]
            var codeValue = hashAlgorithm(mapCode)
            hashMap[codeValue]!!.retainAll { it -> it.first != mapCode}
        }
    }
    var outputSums = hashMap.entries.map { (k,v) ->  v.mapIndexed { ix,it -> (k+1) * (ix+1) * it.second }.sum() }
    var output = outputSums.sum()
    return output
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var seqList = lineList[0].split(",")

    println(hashmapAlgorithm(seqList))

}