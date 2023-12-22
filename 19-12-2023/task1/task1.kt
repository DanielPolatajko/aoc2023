import java.io.File
import java.io.InputStream
import kotlin.math.*

class Part(x: Int, m: Int, a: Int, s: Int) {
    var x: Int = x
    var m: Int = m
    var a: Int = a
    var s: Int = s

    val value: Int 
        get() = x+m+a+s
}

fun constructPartList(input: MutableList<String>): MutableList<Part> {
    var outList = mutableListOf<Part>()
    for (line in input) {
        var relevantLine = line.substring(1, line.length-1)
        var x = relevantLine.split(",")[0].split("=")[1].toInt()
        var m = relevantLine.split(",")[1].split("=")[1].toInt()
        var a = relevantLine.split(",")[2].split("=")[1].toInt()
        var s = relevantLine.split(",")[3].split("=")[1].toInt()
        outList.add(Part(x,m,a,s))
    }
    return outList
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

fun applyWorkflow(part: Part, workflowMap: MutableMap<String, List<String>>): Int {
    var completed = false
    var key = "in"

    while (!completed) {
        var currentFlow = workflowMap[key]!!

        for (step in currentFlow) { 
            if (step.indexOf(":") == -1) {
                var nextKey = step
                if (nextKey == "R") return 0
                else if (nextKey == "A") return part.value
                else {
                    key = nextKey 
                    break
                }
            }
            var rule = step.split(":")[0]
            var nextKey = step.split(":")[1]

            var attribute = rule.get(0)
            var comparator = rule.get(1)
            var value = rule.split(comparator)[1].toInt()

            var partValue = 0

            if (attribute == 'x') partValue = part.x
            if (attribute == 'm') partValue = part.m
            if (attribute == 'a') partValue = part.a
            if (attribute == 's') partValue = part.s

            if (comparator == '>') {
                if (partValue > value) {
                    if (nextKey == "R") return 0
                    else if (nextKey == "A") return part.value
                    else {
                        key = nextKey 
                        break
                    }
                }
            } else if (comparator == '<') {
                if (partValue < value) {
                    if (nextKey == "R") return 0
                    else if (nextKey == "A") return part.value
                    else {
                        key = nextKey 
                        break
                    }
                }
            }
        }
    }
    return 0
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var split = lineList.indexOf("")
    var workflows = lineList.subList(0, split)
    var parts = lineList.subList(split+1, lineList.size)

    var workflowMap = constructWorkflowMap(workflows)
    var partList = constructPartList(parts)

    var acceptedPartScores = partList.map {it -> applyWorkflow(it, workflowMap) }


    println(acceptedPartScores.sum())

}