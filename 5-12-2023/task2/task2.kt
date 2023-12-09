import java.io.File
import java.io.InputStream
import kotlin.math.*

fun processLine(line: String, currentRanges: MutableList<MutableList<Long>>) : Pair<MutableList<MutableList<Long>>, MutableList<MutableList<Long>>> { 
    var destStart = line.trim().split(" ")[0].toLong()
    var sourceStart = line.trim().split(" ")[1].toLong()
    var range = line.trim().split(" ")[2].toLong()

    var unmappedRanges = mutableListOf<MutableList<Long>>()

    var mappedRanges = mutableListOf<MutableList<Long>>()


    var sourceEnd = sourceStart + range - 1

    var destTransform = destStart - sourceStart

    for (currentRange in currentRanges) {

        if (sourceStart <= currentRange[0]) {
            if (sourceEnd < currentRange[0]) {
                unmappedRanges.add(currentRange)
            } else {
                if (sourceEnd < currentRange[1]) {
                    mappedRanges.add(mutableListOf<Long>(currentRange[0] + destTransform, sourceEnd + destTransform))
                    unmappedRanges.add(mutableListOf<Long>(sourceEnd+1, currentRange[1]))

                } else {
                    mappedRanges.add(mutableListOf<Long>(currentRange[0] + destTransform, currentRange[1] + destTransform))
                }
            }
        } else { 
            if (sourceStart > currentRange[1]) {
                unmappedRanges.add(currentRange)
            } else {
                unmappedRanges.add(mutableListOf<Long>(currentRange[0], sourceStart-1))
                if (sourceEnd < currentRange[1]) {
                    mappedRanges.add(mutableListOf<Long>(sourceStart + destTransform, sourceEnd + destTransform))
                    unmappedRanges.add(mutableListOf<Long>(sourceEnd+1, currentRange[1]))
                } else {
                    mappedRanges.add(mutableListOf<Long>(sourceStart + destTransform, currentRange[1] + destTransform))
                }
            }
        }
    }

    return Pair(unmappedRanges, mappedRanges)
}

fun applyMappings(input: MutableList<String>, ranges: MutableList<MutableList<Long>>): Pair<MutableList<String>,MutableList<MutableList<Long>>> {

    var blockEnd = input.indexOf("")

    if (blockEnd == -1) blockEnd = input.size

    var newInput: MutableList<String>

    if (blockEnd < input.size) {
        newInput = input.subList(blockEnd+1,input.size).toMutableList()
    } else {
        newInput = mutableListOf<String>()
    }

    var latestRanges = ranges
    var newRanges = mutableListOf<MutableList<Long>>()

    for (line in input.subList(1, blockEnd)) {
        var (unmappedRanges, mappedRanges) = processLine(line, latestRanges)
        newRanges += mappedRanges
        latestRanges = unmappedRanges
    }

    newRanges += latestRanges

   return Pair(newInput, newRanges)
}

fun applyAllMappings(input: MutableList<String>, ranges: MutableList<MutableList<Long>>): MutableList<MutableList<Long>> {
    var newInput = input.subList(2,input.size).toMutableList()
    var newRanges = ranges

    while (newInput.size > 0) {
        var (newInputModified, newRangesModified) = applyMappings(newInput, newRanges)
        newInput = newInputModified
        newRanges = newRangesModified
    }

    return newRanges
}

fun constructLocationRangesList(input: MutableList<String>): MutableList<MutableList<Long>> {

    var seedsLine = input[0].split(": ")[1]

    var seedsList = seedsLine.split(" ").map { it.trim().toLong() }

    var seedRangesList = seedsList.windowed(2, step=2).map { it -> mutableListOf<Long>(it[0], it[0] + it[1])}.toMutableList()

    var locationRanges = applyAllMappings(input, seedRangesList)

    return locationRanges
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(constructLocationRangesList(lineList).flatten().min())
}