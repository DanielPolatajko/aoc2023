import java.io.File
import java.io.InputStream

var beamMapper = mutableMapOf<Triple<Int,Int,Char>, List<Triple<Int,Int,Char>>>()

fun traverseGrid(input: MutableList<String>, key: Triple<Int,Int,Char>) {
    if (!beamMapper.containsKey(key)) {
        var x = key.first
        var y = key.second
        var direction = key.third 
        var stillTraversing = true
        while (stillTraversing) {
            var newKey = Triple(x,y,direction)
            if (beamMapper.containsKey(newKey)) stillTraversing = false
            else {
                if (direction == 'N') {
                    if (x-1 >= 0) {
                        var nextTile = input[x-1][y]
                        if (nextTile == '.' || nextTile == '|') {
                            beamMapper[newKey] = listOf(Triple(x-1, y, 'N'))
                            x = x-1
                            direction = 'N'
                        }
                        if (nextTile == '\\') {
                            beamMapper[newKey] = listOf(Triple(x-1, y, 'W'))
                            x = x-1
                            direction = 'W'
                        }
                        if (nextTile == '/') {
                            beamMapper[newKey] = listOf(Triple(x-1, y, 'E'))
                            x = x-1
                            direction = 'E'
                        }
                        if (nextTile == '-') {
                            beamMapper[newKey] = listOf(Triple(x-1, y, 'W'), Triple(x-1, y, 'E'))
                            traverseGrid(input, Triple(x-1, y, 'W'))
                            traverseGrid(input, Triple(x-1, y, 'E'))
                        }
                    } else {
                        beamMapper[newKey] = listOf(Triple(x-1, y, 'X'))
                        stillTraversing = false
                    }
                } else if (direction == 'E') {
                    if (y+1 < input[0].length) {
                        var nextTile = input[x][y+1]
                        if (nextTile == '.' || nextTile == '-') {
                            beamMapper[newKey] = listOf(Triple(x, y+1, 'E'))
                            y = y + 1
                            direction = 'E'
                        }
                        if (nextTile == '\\') {
                            beamMapper[newKey] = listOf(Triple(x, y+1, 'S'))
                            y = y + 1
                            direction = 'S'
                        }
                        if (nextTile == '/') {
                            beamMapper[newKey] = listOf(Triple(x, y+1, 'N'))
                            y = y + 1
                            direction = 'N'
                        }
                        if (nextTile == '|') {
                            beamMapper[newKey] = listOf(Triple(x, y+1, 'N'), Triple(x, y+1, 'S'))
                            traverseGrid(input, Triple(x, y+1, 'N'))
                            traverseGrid(input, Triple(x, y+1, 'S'))
                        }
                    } else {
                        beamMapper[newKey] = listOf(Triple(x, y+1, 'X'))
                        stillTraversing = false
                    }
                } else if (direction == 'S') {
                    if (x+1 < input.size) {
                        var nextTile = input[x+1][y]
                        if (nextTile == '.' || nextTile == '|') {
                            beamMapper[newKey] = listOf(Triple(x+1, y, 'S'))
                            x = x + 1
                            direction = 'S'
                        }
                        if (nextTile == '\\') {
                            beamMapper[newKey] = listOf(Triple(x+1, y, 'E'))
                            x = x + 1
                            direction = 'E'
                        }
                        if (nextTile == '/') {
                            beamMapper[newKey] = listOf(Triple(x+1, y, 'W'))
                            x = x + 1
                            direction = 'W'
                        }
                        if (nextTile == '-') {
                            beamMapper[newKey] = listOf(Triple(x+1, y, 'E'), Triple(x+1, y, 'W'))
                            traverseGrid(input, Triple(x+1, y, 'E'))
                            traverseGrid(input, Triple(x+1, y, 'W'))
                        }
                    } else {
                        beamMapper[newKey] = listOf(Triple(x+1, y, 'X'))
                        stillTraversing = false
                    }
                } else if (direction == 'W') {
                    if (y-1 >= 0) {
                        var nextTile = input[x][y-1]
                        if (nextTile == '.' || nextTile == '-') {
                            beamMapper[newKey] = listOf(Triple(x, y-1, 'W'))
                            y = y - 1
                            direction = 'W'
                        }
                        if (nextTile == '\\') {
                            beamMapper[newKey] = listOf(Triple(x, y-1, 'N'))
                            y = y - 1
                            direction = 'N'
                        }
                        if (nextTile == '/') {
                            beamMapper[newKey] = listOf(Triple(x, y-1, 'S'))
                            y = y - 1
                            direction = 'S'
                        }
                        if (nextTile == '|') {
                            beamMapper[newKey] = listOf(Triple(x, y-1, 'S'), Triple(x, y-1, 'N'))
                            traverseGrid(input, Triple(x, y-1, 'S'))
                            traverseGrid(input, Triple(x, y-1, 'N'))
                        }
                    } else {
                        beamMapper[newKey] = listOf(Triple(x, y-1, 'X'))
                        stillTraversing = false
                    }
                }
            }
        }
    }
}

fun findEnergisedTiles(input: MutableList<String>): Int {
    var output = 0
    var numberTiles: Int
    
    for (i in 0..input.size-1) {
        beamMapper.clear()
        traverseGrid(input, Triple(i,-1,'E'))
        numberTiles = beamMapper.keys.map {it -> Pair(it.first, it.second)}.toSet().size - 1
        if (numberTiles > output) output = numberTiles

        beamMapper.clear()
        traverseGrid(input, Triple(i,input[0].length,'W'))
        numberTiles = beamMapper.keys.map {it -> Pair(it.first, it.second)}.toSet().size - 1
        if (numberTiles > output) output = numberTiles
    }

    for (j in 0..input[0].length-1) {
        beamMapper.clear()
        traverseGrid(input, Triple(-1,j,'S'))
        numberTiles = beamMapper.keys.map {it -> Pair(it.first, it.second)}.toSet().size - 1
        if (numberTiles > output) output = numberTiles

        beamMapper.clear()
        traverseGrid(input, Triple(input.size,j,'N'))
        numberTiles = beamMapper.keys.map {it -> Pair(it.first, it.second)}.toSet().size - 1
        if (numberTiles > output) output = numberTiles
    }

    return output
}


fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(findEnergisedTiles(lineList))

}