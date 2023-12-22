import java.io.File
import java.io.InputStream
import java.util.*


class Part(x: Pair<Int,Int>, m: Pair<Int,Int>, a: Pair<Int,Int>, s: Pair<Int,Int>) {
    var x: Pair<Int,Int> = x
    var m: Pair<Int,Int> = m
    var a: Pair<Int,Int> = a
    var s: Pair<Int,Int> = s

    val value: Long
        get() = (x.second-x.first+1).toLong()*(m.second-m.first+1).toLong()*(a.second-a.first+1).toLong()*(s.second-s.first+1).toLong()
}

fun constructWorkflowMap(input: MutableList<String>): MutableMap<String, List<String>> {
    var workflowMap = mutableMapOf<String,List<String>>()
    for (line in input) {
        var key = line.split("{")[0]
        var value = line.split("{")[1].split("}")[0].split(",")
        workflowMap[key] = value
    }
    return workflowMap
}

fun applyWorkflow(workflowMap: MutableMap<String, List<String>>): MutableList<Part> {

    var q: Queue<Pair<String, Part>> = LinkedList()

    q.add(Pair("in", Part(Pair(1,4000), Pair(1,4000),Pair(1,4000),Pair(1,4000))))

    var accepted = mutableListOf<Part>()

    while (!q.isEmpty()) {
        var thing = q.remove()

        var key = thing.first
        var part = thing.second

        var currentFlow = workflowMap[key]!!

        for (step in currentFlow) { 
            if (step.indexOf(":") == -1) {
                var nextKey = step
                if (nextKey == "A") {
                    accepted.add(part)
                }
                else if (nextKey != "R") {
                    q.add(Pair(nextKey, part))
                }
                break
            }
            var rule = step.split(":")[0]
            var nextKey = step.split(":")[1]

            var attribute = rule.get(0)
            var comparator = rule.get(1)
            var value = rule.split(comparator)[1].toInt()

            var partValue = Pair(0,0)

            if (attribute == 'x') partValue = part.x
            if (attribute == 'm') partValue = part.m
            if (attribute == 'a') partValue = part.a
            if (attribute == 's') partValue = part.s

            if (comparator == '>') {
                if (partValue.first > value) {
                    if (nextKey == "A") accepted.add(part)
                    else if (nextKey != "R") {
                        q.add(Pair(nextKey, part))
                    }
                    break
                } else if (partValue.second > value) {
                    if (attribute == 'x') {
                        if (nextKey == "A") accepted.add(Part(Pair(value+1, part.x.second), part.m, part.a, part.s))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey, Part(Pair(value+1, part.x.second), part.m, part.a, part.s)))
                        }
                        part = Part(Pair(part.x.first, value), part.m, part.a, part.s)
                    }
                    if (attribute == 'm') {
                        if (nextKey == "A") accepted.add(Part(part.x, Pair(value+1, part.m.second), part.a, part.s))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey, Part(part.x, Pair(value+1, part.m.second), part.a, part.s)))
                        }
                        part = Part(part.x, Pair(part.m.first, value), part.a, part.s)
                    }
                    if (attribute == 'a') {
                        if (nextKey == "A") accepted.add(Part(part.x, part.m, Pair(value+1, part.a.second), part.s))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey,Part(part.x, part.m, Pair(value+1, part.a.second), part.s)))
                        }
                        part = Part(part.x, part.m, Pair(part.a.first, value), part.s)
                    }
                    if (attribute == 's') {
                        if (nextKey == "A") accepted.add(Part(part.x, part.m, part.a, Pair(value+1, part.s.second)))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey,Part(part.x, part.m, part.a, Pair(value+1, part.s.second))))
                        }
                        part = Part(part.x, part.m, part.a, Pair(part.s.first, value))
                    }

                }
            } else if (comparator == '<') {
                if (partValue.second < value) {
                    if (nextKey == "A") accepted.add(part)
                    else if (nextKey != "R") {
                        q.add(Pair(nextKey, part))
                    }
                    break
                } else if (partValue.first < value) {
                    if (attribute == 'x') {
                        if (nextKey == "A") accepted.add(Part(Pair(part.x.first, value-1), part.m, part.a, part.s))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey, Part(Pair(part.x.first, value-1), part.m, part.a, part.s)))
                        }
                        part = Part(Pair(value, part.x.second), part.m, part.a, part.s)
                    }
                    if (attribute == 'm') {
                        if (nextKey == "A") accepted.add(Part(part.x, Pair(part.m.first, value-1), part.a, part.s))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey,Part(part.x, Pair(part.m.first, value-1), part.a, part.s)))
                        }
                        part = Part(part.x, Pair(value, part.m.second), part.a, part.s)
                    }
                    if (attribute == 'a') {
                        if (nextKey == "A") accepted.add(Part(part.x, part.m, Pair(part.a.first, value-1), part.s))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey,Part(part.x, part.m, Pair(part.a.first, value-1), part.s)))
                        }
                        part = Part(part.x, part.m, Pair(value, part.a.second), part.s)
                    }
                    if (attribute == 's') {
                        if (nextKey == "A") accepted.add(Part(part.x, part.m, part.a, Pair(part.s.first, value-1)))
                        else if (nextKey != "R") {
                            q.add(Pair(nextKey,Part(part.x, part.m, part.a, Pair(part.s.first, value-1))))
                        }
                        part = Part(part.x, part.m, part.a, Pair(value, part.s.second))
                    }

                }
            }
        }
    }
    return accepted
}

fun calculateAcceptedParts(parts: MutableList<Part>): Long {
    var count = 0L

    for (part in parts) {
        count += part.value
    }

    return count
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var split = lineList.indexOf("")
    var workflows = lineList.subList(0, split)

    var workflowMap = constructWorkflowMap(workflows)

    var acceptedPartRanges = applyWorkflow(workflowMap)

    println(calculateAcceptedParts(acceptedPartRanges))

}