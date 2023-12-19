import java.io.File
import java.io.InputStream

fun getMaxColourPerTrial(trial: String, colour: String): Int {
    var numbers: List<Int> = trial
        .split(", ")
        .filter { it.endsWith(colour) }
        .map { it.split(" ")[0].toInt() }
        
    return numbers.maxOrNull() ?: 0
}

fun findPossibleGames(game: String): Int {
    var id: Int = game.split(": ")[0].split(" ")[1].toInt()

    var trials: List<String> = game.split(": ")[1].split("; ")

    var maxRed: Int = trials.map { getMaxColourPerTrial(it, "red") }.maxOrNull() ?: 0
    var maxBlue: Int = trials.map { getMaxColourPerTrial(it, "blue") }.maxOrNull() ?: 0
    var maxGreen: Int = trials.map { getMaxColourPerTrial(it, "green") }.maxOrNull() ?: 0

    var out: Int = if (maxRed <= 12 && maxGreen <= 13 && maxBlue <= 14) id else 0
    
    return out
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 
    var sumList: List<Int> = lineList.map{findPossibleGames(it)}
    println(sumList.sum())
}