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

var lowCount = 0
var highCount = 0

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

fun traverseNodes(nodeMap: MutableMap<String,Node>): Boolean {

    var q: Queue<Node> = LinkedList()

    q.add(nodeMap["broadcaster"]!!)


    while (!q.isEmpty()) {
        var current = q.remove()

        if (current.pulse) highCount += 1 * current.targets.size else lowCount += 1 * current.targets.size

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

    return !(nodeMap.values.any { it -> if (it.type == '%') it.flipFlopState else false })
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var nodeMap = constructNodeMap(lineList)

    var cycled = false
    var cycleLength = 0
    while (!cycled && cycleLength < 1000) {
        lowCount += 1
        cycled = traverseNodes(nodeMap)
        cycleLength += 1

        println(cycleLength)

    }


    println(lowCount * highCount * (1000 / cycleLength) * (1000 / cycleLength))

}