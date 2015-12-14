import java.io.File
import java.util.*


fun generateNext(s: String): String {
    val ret = StringBuilder()

    var currentChar: Char = s[0]
    var counter = 0
    s.forEach {
        if(it == currentChar) {
            counter++
        } else {
            ret.append(counter).append(currentChar)
            currentChar = it
            counter = 1
        }
    }
    ret.append(counter).append(currentChar)

    return ret.toString()
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    var start = "3113322113"
    for(i in 1..40) {
        start = generateNext(start)
    }
    println(start.length)
    for(i in 1..10) {
        start = generateNext(start)
    }
    println(start.length)
}
