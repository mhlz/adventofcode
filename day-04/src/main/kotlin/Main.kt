import org.apache.commons.codec.digest.DigestUtils
import java.util.stream.Stream

/**
 * @author Mischa Holz
 */

public fun main(args: Array<String>) {

    val input = "iwrupvqb"

    println("task 1")
    Stream
            .iterate(1) { it + 1 }
            .limit(1000000000)
            .map { Pair(it, DigestUtils.md5Hex(input + it)) }
            .filter { it.second.startsWith("0000") }
            .forEach { println(it) }

    println("task 2")
    Stream
            .iterate(1) { it + 1 }
            .limit(1000000000)
            .map { Pair(it, DigestUtils.md5Hex(input + it)) }
            .filter { it.second.startsWith("00000") }
            .forEach { println(it) }
}