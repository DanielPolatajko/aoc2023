import java.io.File
import java.io.InputStream

fun buildCoordMap(input: MutableList<String>, coordMap: MutableMap<String,Pair<String,String>>): MutableMap<String,Pair<String,String>> {
    for (line in input) {
        var key = line.split(" = ")[0]
        var values = line.split(" = ")[1]
        var left = values.substring(1, values.length-1).split(", ")[0]
        var right = values.substring(1, values.length-1).split(", ")[1]

        coordMap[key] = Pair(left, right)
    }
    return coordMap
}

fun findCycleLength(coordMap: MutableMap<String,Pair<String,String>>, startCoord: String, sequence: String): Long {

    var count = 0

    var next = startCoord

    while (next.last() != 'Z') {
        var operation = sequence[count % sequence.length]
        var (l,r) = coordMap[next] ?: Pair("somethings", "wrong")
        if (operation == 'L') next = l else next = r
        count += 1
    }

    return count.toLong()
}

fun LCM(x: Long, y: Long): Long {
    var product = x * y
    var biggest = if (x < y) y else x
    var lcm = biggest
    while (lcm <= product) {
        if (lcm % x == 0.toLong() && lcm % y == 0.toLong()) {
            return lcm
        }
        lcm += biggest
    }
    return product
}

fun findAllCycleLengthsAndLCM(input: MutableList<String>): Long {
    var sequence = input[0]

    var coordMap = buildCoordMap(input.subList(2, input.size), mutableMapOf<String, Pair<String,String>>())

    var starters = coordMap.keys.toSet().filter { it.last() == 'A' }

    var cycleLengths = mutableListOf<Long>()

    for (startCoord in starters) {
        cycleLengths.add(findCycleLength(coordMap, startCoord, sequence))
    }

    return cycleLengths.reduce { agg, it -> LCM(agg, it) }
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(findAllCycleLengthsAndLCM(lineList))
}