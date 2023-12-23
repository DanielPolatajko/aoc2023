import java.io.File
import java.io.InputStream
import java.util.*


class Node(nodeString: String) {
    var type: Char = nodeString.first()

    var key: String = if (type !in listOf('%', '&')) nodeString.split(" ")[0] else nodeString.drop(1).split(" ")[0]

    var targets: List<String> = if (nodeString.indexOf(" -> ") > -1) nodeString.split(" -> ")[1].split(", ") else listOf<String>()

    var flipFlopState: Boolean = false

    var prevPulses: MutableMap<String, Boolean> = mutableMapOf<String, Boolean>()

    var pulse: Boolean = false
}

var highCycles = mutableMapOf<String,Int>()
var cycleLength = 0

fun constructNodeMap(input: MutableList<String>): MutableMap<String, Node> { 
    var outMap = mutableMapOf<String, Node>()
    for (line in input) {
        var node = Node(line)
        outMap[node.key] = node

        for (nodeKey in node.targets) {
            outMap[nodeKey] = (outMap[nodeKey] ?: Node(nodeKey))
        }
    }

    for (entry in outMap.entries.iterator()) {
        var current = entry.value
        for (nextKey in current.targets) {
            var nextNode = outMap[nextKey]!!
            if (nextNode.type == '&') {
                nextNode.prevPulses[current.key] = false
            }
        }
    }

    return outMap
}

fun traverseNodes(nodeMap: MutableMap<String,Node>, cycleLength: Int) {

    var q: Queue<Node> = LinkedList()

    q.add(nodeMap["broadcaster"]!!)


    while (!q.isEmpty()) {
        var current = q.remove()

        if ("rg" in current.targets && current.pulse) {
            highCycles[current.key] = cycleLength
        }

        for (nextKey in current.targets) {

            var nextNode = nodeMap[nextKey]!!
            if (nextNode.type == '%') {
                // Flip flop
                if (!current.pulse) {
                    nextNode.flipFlopState = !nextNode.flipFlopState
                    nextNode.pulse = !nextNode.pulse
                    q.add(nextNode)
                }
            } else if (nextNode.type == '&') {
                // Conjunction
                nextNode.prevPulses[current.key] = current.pulse
                if (nextNode.prevPulses.values.all{ it }) nextNode.pulse = false else nextNode.pulse = true
                q.add(nextNode)
            }
        }
    }
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

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var nodeMap = constructNodeMap(lineList)

    var stop = false
    var cycleLength = 1
    while (!stop) {
        traverseNodes(nodeMap, cycleLength)
        cycleLength += 1

        stop = highCycles.size == 4
    }
    println(highCycles.values.map { it -> it.toLong() }.reduce { agg, it -> LCM(agg, it) })

}