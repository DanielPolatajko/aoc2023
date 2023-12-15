import java.io.File
import java.io.InputStream

var stateSpace = mutableMapOf<Pair<String,List<Int>>, Long>()

fun calculateCombinations(line:String, blockSizes: List<Int>): Long {

    if (blockSizes.size == 0) return if ("#" in line) 0L else 1L
    if (line == "") return 0L
    if (stateSpace.containsKey(Pair(line, blockSizes))) return stateSpace[Pair(line, blockSizes)] ?: 0L    
    
    var possibleCombinations = 0L

    if (line.first() == '?' || line.first() == '.') {
        possibleCombinations += calculateCombinations(line.substring(1), blockSizes)
    }
    if (line.first() == '?' || line.first() == '#') {
        if (blockSizes[0] <= line.length && !("." in line.take(blockSizes[0])) && (blockSizes[0] == line.length || line[blockSizes[0]] != '#')) {
            if (line.length < blockSizes[0] + 1) {
                possibleCombinations += calculateCombinations("", blockSizes.subList(1, blockSizes.size))
            } else {
                possibleCombinations += calculateCombinations(line.substring(blockSizes[0]+1), blockSizes.subList(1, blockSizes.size))
            }
        }
    }
        
    stateSpace[Pair(line, blockSizes)] = possibleCombinations

    return possibleCombinations.toLong()
}

fun unfoldRecords(input: MutableList<String>): MutableList<String> {
    return input.map { it -> (it.split(" ")[0] + "?").repeat(5).dropLast(1) + " " + (it.split(" ")[1] +",").repeat(5).dropLast(1)}.toMutableList()
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var unfolded = unfoldRecords(lineList)

    println(unfolded.map {it -> calculateCombinations(it.split(" ")[0], it.split(" ")[1].split(",").map { it.toInt() }) }.sum())

}