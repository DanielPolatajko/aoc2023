import java.io.File
import java.io.InputStream
import kotlin.math.*

fun calculateNextCopiesFromCard(card: String, cardCounter: MutableMap<Int, Int>) {

    var cardNumber = card
        .split(":")[0]
        .split(" ")
        .filter { !it.isNullOrEmpty() }[1]
        .toInt()

    cardCounter += cardNumber to (cardCounter[cardNumber] ?: 0) + 1

    var numbers = card.split(": ")[1]
    var winners: Set<Int> = numbers
        .split(" | ")[0]
        .split(" ")
        .filter { !it.isNullOrEmpty() }
        .map { it.trim().toInt() }
        .toSet()
    var ours: Set<Int> = numbers
        .split(" | ")[1]
        .split(" ")
        .filter { !it.isNullOrEmpty() }
        .map { it.trim().toInt() }
        .toSet()

    for (i in cardNumber+1..(cardNumber+ours.intersect(winners).size)) {
        cardCounter += i to ((cardCounter[i] ?: 0) + (cardCounter[cardNumber] ?: 0))
    }
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var cardCounter = mutableMapOf<Int, Int>()
    
    for (card in lineList) {
        calculateNextCopiesFromCard(card, cardCounter)
    }

    println(cardCounter.values.sum())
}