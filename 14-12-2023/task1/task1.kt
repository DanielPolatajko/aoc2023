import java.io.File
import java.io.InputStream
import kotlin.math.*

fun tiltNorth(input: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
    var output = input
    for ((ix, row) in input.withIndex()) {
        for ((jx, rock) in row.withIndex()) {
            if (rock == 'O') {
                var newRow = ix-1
                while (newRow >= 0 && output[newRow][jx] !in listOf('#', 'O')) {
                    output[newRow+1][jx] = output[newRow][jx]
                    output[newRow][jx] = 'O'
                    newRow -= 1
                }
            }
        }
    }
    return output
}

fun calculateLoad(tiltedRocks: MutableList<MutableList<Char>>): Int {
    var load = 0
    for ((ix,row) in tiltedRocks.withIndex()) {
        load += row.count{ it == 'O' } * (tiltedRocks.size - ix)
    }
    return load
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var mapList = lineList.map { it.toMutableList() }.toMutableList()

    mapList = tiltNorth(mapList)

    println(calculateLoad(mapList))

}