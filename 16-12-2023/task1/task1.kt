import java.io.File
import java.io.InputStream

var beamMapper = mutableMapOf<Triple<Int,Int,Char>, List<Triple<Int,Int,Char>>>()

fun traverseGrid(input: MutableList<String>, key: Triple<Int,Int,Char>) {
    if (!beamMapper.containsKey(key)) {
        var x = key.first
        var y = key.second
        var direction = key.third 
        if (direction == 'N') {
            if (x-1 >= 0) {
                var nextTile = input[x-1][y]
                if (nextTile == '.' || nextTile == '|') {
                    beamMapper[key] = listOf(Triple(x-1, y, 'N'))
                    traverseGrid(input, Triple(x-1, y, 'N'))
                }
                if (nextTile == '\\') {
                    beamMapper[key] = listOf(Triple(x-1, y, 'W'))
                    traverseGrid(input, Triple(x-1, y, 'W'))
                }
                if (nextTile == '/') {
                    beamMapper[key] = listOf(Triple(x-1, y, 'E'))
                    traverseGrid(input, Triple(x-1, y, 'E'))
                }
                if (nextTile == '-') {
                    beamMapper[key] = listOf(Triple(x-1, y, 'W'), Triple(x-1, y, 'E'))
                    traverseGrid(input, Triple(x-1, y, 'W'))
                    traverseGrid(input, Triple(x-1, y, 'E'))
                }
            } else {
                beamMapper[key] = listOf(Triple(x-1, y, 'X'))
            }
        } else if (direction == 'E') {
            if (y+1 < input[0].length) {
                var nextTile = input[x][y+1]
                if (nextTile == '.' || nextTile == '-') {
                    beamMapper[key] = listOf(Triple(x, y+1, 'E'))
                    traverseGrid(input, Triple(x, y+1, 'E'))
                }
                if (nextTile == '\\') {
                    beamMapper[key] = listOf(Triple(x, y+1, 'S'))
                    traverseGrid(input, Triple(x, y+1, 'S'))
                }
                if (nextTile == '/') {
                    beamMapper[key] = listOf(Triple(x, y+1, 'N'))
                    traverseGrid(input, Triple(x, y+1, 'N'))
                }
                if (nextTile == '|') {
                    beamMapper[key] = listOf(Triple(x, y+1, 'N'), Triple(x, y+1, 'S'))
                    traverseGrid(input, Triple(x, y+1, 'N'))
                    traverseGrid(input, Triple(x, y+1, 'S'))
                }
            } else {
                beamMapper[key] = listOf(Triple(x, y+1, 'X'))
            }
        } else if (direction == 'S') {
            if (x+1 < input.size) {
                var nextTile = input[x+1][y]
                if (nextTile == '.' || nextTile == '|') {
                    beamMapper[key] = listOf(Triple(x+1, y, 'S'))
                    traverseGrid(input, Triple(x+1, y, 'S'))
                }
                if (nextTile == '\\') {
                    beamMapper[key] = listOf(Triple(x+1, y, 'E'))
                    traverseGrid(input, Triple(x+1, y, 'E'))
                }
                if (nextTile == '/') {
                    beamMapper[key] = listOf(Triple(x+1, y, 'W'))
                    traverseGrid(input, Triple(x+1, y, 'W'))
                }
                if (nextTile == '-') {
                    beamMapper[key] = listOf(Triple(x+1, y, 'E'), Triple(x+1, y, 'W'))
                    traverseGrid(input, Triple(x+1, y, 'E'))
                    traverseGrid(input, Triple(x+1, y, 'W'))
                }
            } else {
                beamMapper[key] = listOf(Triple(x+1, y, 'X'))
            }
        } else if (direction == 'W') {
            if (y-1 >= 0) {
                var nextTile = input[x][y-1]
                if (nextTile == '.' || nextTile == '-') {
                    beamMapper[key] = listOf(Triple(x, y-1, 'W'))
                    traverseGrid(input, Triple(x, y-1, 'W'))
                }
                if (nextTile == '\\') {
                    beamMapper[key] = listOf(Triple(x, y-1, 'N'))
                    traverseGrid(input, Triple(x, y-1, 'N'))
                }
                if (nextTile == '/') {
                    beamMapper[key] = listOf(Triple(x, y-1, 'S'))
                    traverseGrid(input, Triple(x, y-1, 'S'))
                }
                if (nextTile == '|') {
                    beamMapper[key] = listOf(Triple(x, y-1, 'S'), Triple(x, y-1, 'N'))
                    traverseGrid(input, Triple(x, y-1, 'S'))
                    traverseGrid(input, Triple(x, y-1, 'N'))
                }
            } else {
                beamMapper[key] = listOf(Triple(x, y-1, 'X'))
            }
        }
    }
}

fun findEnergisedTiles(input: MutableList<String>): Int {
    traverseGrid(input, Triple(0,-1,'E'))

    var numberTiles = beamMapper.keys.map {it -> Pair(it.first, it.second)}.toSet()

    return numberTiles.size - 1
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(findEnergisedTiles(lineList))

}