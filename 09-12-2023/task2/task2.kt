import java.io.File
import java.io.InputStream

fun makePrediction(sequence: List<Int>): Int {
    var diffList = sequence.toMutableList()
    var seqEnds = mutableListOf<Int>()
    while (diffList.toSet() != setOf(0)) {
        seqEnds.add(diffList.first())
        diffList = diffList.windowed(2).map { it[1] - it[0] }.toMutableList()
    }
    seqEnds.add(0)
    seqEnds.reversed()
    return seqEnds.reduceRight { agg, it -> agg - it }
}

fun findPredictions(input: MutableList<String>): List<Int> {
    return input.map { makePrediction(it.split(" ").map{ it.toInt() }) }
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(findPredictions(lineList).sum())
}