import java.io.File
import java.io.InputStream

fun findDigitIndices(value: String): String {
    var numbers = arrayOf(
         "0",
        "one",
         "1",
        "two",
         "2",
        "three", 
        "3",
        "four",
         "4",
        "five", 
        "5",
        "six", 
        "6",
        "seven", 
        "7",
        "eight", 
        "8",
        "nine", 
        "9"
    )
    var maxIndices: List<Int> = numbers.map { value.lastIndexOf(it) }
    var argMax = maxIndices.withIndex().maxByOrNull { it.value }?.index

    var minIndices: List<Int> = numbers.map { value.indexOf(it) }.map { if (it == -1) Int.MAX_VALUE else it }
    var argMin = minIndices.withIndex().minByOrNull { it.value }?.index

    return numbers[argMin!!].plus(numbers[argMax!!])
}

fun sumFirstAndLastWithWordNumbers(value: String): Int {
    var digits = findDigitIndices(value)
    digits = digits
        .replace("one", "1")
        .replace("two", "2")
        .replace("three", "3")
        .replace("four", "4")
        .replace("five", "5")
        .replace("six", "6")
        .replace("seven", "7")
        .replace("eight", "8")
        .replace("nine", "9")
    var first: String = digits.first().toString()
    var last: String = digits.last().toString()

    return first.plus(last).toInt()
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 
    var sumList: List<Int> = lineList.map{sumFirstAndLastWithWordNumbers(it)}
    println(sumList.sum())
}