import java.io.File
import java.io.InputStream

fun findNextLine(input: MutableList<String>, coordMap: MutableMap<String,Pair<String,String>>, coord: String): Pair<String,String> {
    if (coordMap.containsKey(coord)) return coordMap[coord] ?: Pair("u got", "pranked")
    else {
        loop@ for (line in input) {
            var key = line.split(" = ")[0]
            if (coordMap.containsKey(key)) continue@loop
            var values = line.split(" = ")[1]
            var left = values.substring(1, values.length-1).split(", ")[0]
            var right = values.substring(1, values.length-1).split(", ")[1]

            coordMap[key] = Pair(left, right)
            if (key == coord) return coordMap[key] ?: Pair("u got", "pranked")
        }
    }
    return Pair("u got", "pranked")
}

fun navigateLines(input: MutableList<String>): Long {

    var coordMap = mutableMapOf<String,Pair<String,String>>()

    var sequence = input[0]

    var next = "AAA"

    var count: Long = 0

    while (next != "ZZZ") {
        var operation = sequence[(count % sequence.length.toLong()).toInt()]
        var (l,r) = findNextLine(input.subList(2, input.size), coordMap, next)
        if (operation == 'L') next = l else next = r
        count += 1
    }

    return count

}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(navigateLines(lineList))
}