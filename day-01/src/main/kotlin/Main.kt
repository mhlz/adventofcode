import java.io.File

/**
 * Created by mholz on 01.12.2015.
 */

fun main(args: Array<String>) {
    val input = File("input.txt")

    var floor = 0
    var count = 0

    input.forEachLine {
        it.forEach {
            count++
            floor += if(it == '(') 1 else -1
            if(floor == -1) println("Went to the basement at $count")
        }
    }

    println("Am at floor $floor")
}
