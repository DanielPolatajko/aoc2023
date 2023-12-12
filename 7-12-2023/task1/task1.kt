import java.io.File
import java.io.InputStream
import kotlin.math.*

class Hand(cards: String, wager: Int) {
    var cards: String = cards
    var wager: Int = wager
}

fun classifyHand(hand: Hand): Int {
    var distinctCards = hand.cards.toSet().size
    if (distinctCards == 1) return 7
    if (distinctCards == 2) {
        var firstCard = hand.cards.first()
        if (hand.cards.count { it == firstCard } == 2 || hand.cards.count { it == firstCard } == 3) return 5 else return 6
    }
    if (distinctCards == 3) {
        var firstCard = hand.cards.first()
        if (hand.cards.count { it == firstCard } == 3) {
            return 4
        } else if (hand.cards.count { it == firstCard } == 2) {
            return 3
        } else { 
            var secondCard = hand.cards[1]
            if (hand.cards.count { it == secondCard } == 2) {
                return 3
            } else {
                return 4
            }
        }
    }
    if (distinctCards == 4) return 2 else return 1
}

val cardOrderingMap = mapOf<Char,Int>(
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9,
    'T' to 10,
    'J' to 11,
    'Q' to 12,
    'K' to 13,
    'A' to 14
)

fun compareHands(hand1: Hand, hand2: Hand): Int {
    var handQuality1 = classifyHand(hand1)
    var handQuality2 = classifyHand(hand2)
    if (handQuality1 == handQuality2) {
        for (i in  0..5) {
            var firstHand1 = hand1.cards[i]
            var firstHand2 = hand2.cards[i]
            if (firstHand1 != firstHand2) return (cardOrderingMap[firstHand1] ?: 1) - (cardOrderingMap[firstHand2] ?: 1)
        }
    } else {
        return handQuality1 - handQuality2
    }
    return 0
}

val handComparator = Comparator { hand1: Hand, hand2: Hand -> compareHands(hand1, hand2) }

fun calculateTotalWinnings(input: MutableList<String>): Long {
    var hands = input.map { Hand(it.split(" ")[0], it.split(" ")[1].toInt()) }

    var sortedHands = hands.sortedWith(handComparator)

    return sortedHands.mapIndexed { ix, it -> (it.wager * (ix+1)).toLong()}.sum()
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File(args[0]).inputStream()
    var lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    println(calculateTotalWinnings(lineList))
}