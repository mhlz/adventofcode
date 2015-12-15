import java.io.File
import java.util.*


fun firstReq(s: String): Boolean {
    var ret = false

    s.forEachIndexed { i, c ->
        if(i + 2 >= s.length) return@forEachIndexed

        var step = true
        for(j in 0..2) {
            step = step && s[i + j].toInt() == c.toInt() + j
        }
        ret = ret || step
    }

    return ret
}

fun secondReq(s: String): Boolean {
    return !(s.contains("i") || s.contains("o") || s.contains("l"))
}

fun thirdReq(s: String): Boolean {
    var firstPair: Char? = null
    s.forEachIndexed { i, c ->
        if(i + 1 >= s.length) return@forEachIndexed

        if(firstPair == null) {
            if(c == s[i + 1]) {
                firstPair = c
            }
        } else {
            if(c != firstPair && c == s[i + 1]) {
                return true
            }
        }
    }

    return false
}

val letters = "abcdefghijklmnopqrstuvwxyz".toCharList()

fun increment(s: String): String {
    val ret = StringBuilder()

    var overflow = true

    s.reversed().forEachIndexed { i, c ->
        if(c == 'z' && overflow) {
            overflow = true
            ret.append("a")
        } else {
            val result = if(overflow) (c.toInt() + 1).toChar() else c
            ret.append(result)
            overflow = false
        }
    }
    return ret.toString().reversed()
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {

    var input = "cqjxxyzz"

    while(true) {
        input = increment(input)
        if(firstReq(input) && secondReq(input) && thirdReq(input)) {
            println(input)
            break
        }
    }
}
