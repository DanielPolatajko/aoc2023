import java.io.File
import java.io.InputStream
import kotlin.math.*
import java.math.BigInteger

fun processLine(line: String, currentNumber: BigInteger) : BigInteger { 
    var destStart = line.trim().split(" ")[0].toBigInteger()
    var sourceStart = line.trim().split(" ")[1].toBigInteger()
    var range = line.trim().split(" ")[2].toBigInteger()

    var newNumber = currentNumber

    if ((currentNumber >= sourceStart) && (currentNumber < (sourceStart + range))) newNumber = destStart + (currentNumber-sourceStart) 

    return newNumber
}

fun applyMappings(input: MutableList<String>, number: BigInteger): Pair< MutableList<String>,BigInteger> {

    var blockEnd = input.indexOf("")

    if (blockEnd == -1) blockEnd = input.size

    var newInput: MutableList<String>

    if (blockEnd < input.size) {
        newInput = input.subList(blockEnd+1,input.size).toMutableList()
    } else {
        newInput = mutableListOf<String>()
    }

    var newNumber = number

    for (line in input.subList(1, blockEnd)) {
        newNumber = processLine(line, newNumber)
        if (newNumber != number) return Pair(newInput, newNumber)
    }

   return Pair(newInput, newNumber)
}

fun applyAllMappings(input: MutableList<String>, number: BigInteger): BigInteger {
    var newInput = input.subList(2,input.size).toMutableList()
    var newNumber = number

    while (newInput.size > 0) {
        var (newInputModified, newNumberModified) = applyMappings(newInput, newNumber)
        newInput = newInputModified
        newNumber = newNumberModified
    }

    return newNumber
}

fun constructLocationsList(input: MutableList<String>): List<BigInteger> {

    var seedsLine = input[0].split(": ")[1]

    var seedsList = seedsLine.split(" ").map { it.trim().toBigInteger() }

    var locationsList = seedsList.map { applyAllMappings(input, it) }

    return locationsList
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(constructLocationsList(lineList).min())
}