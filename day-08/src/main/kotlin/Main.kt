
import java.io.File

/**
 * @author Mischa Holz
 */


fun main(args: Array<String>) {

    var codeCount = 0
    var stringCount = 0
    var escapeCount = 0

    File("input.txt")
            .readLines()
            .map {
                codeCount += it.length
                escapeCount += it.length
                escapeCount += 4

                it.substring(1, it.length - 1)
            }
            .map {
                var ret = ""

                var skip = 0
                it.forEachIndexed { i, c ->
                    if(skip > 0) {
                        skip--
                        return@forEachIndexed
                    }
                    if(c != '\\') {
                        ret += c
                        return@forEachIndexed
                    }
                    val next = it[i + 1]
                    if(next == 'x') {
                        escapeCount++
                    } else {
                        escapeCount += 2
                    }
                    when(next) {
                        '\\' -> {
                            ret += "\\"
                            skip = 1
                        }
                        '"' -> {
                            ret += "\""
                            skip = 1
                        }
                        'x' -> {
                            val code = it.substring(i + 2, i + 2 + 2)
                            val character = Integer.parseInt(code, 16).toChar()
                            ret += character
                            skip = 3
                        }
                    }
                }

                ret
            }
            .forEach {
                stringCount += it.length
            }

    println("task 1")
//    println(codeCount)
//    println(stringCount)
    println(codeCount - stringCount)

    println("task 2")
//    println(escapeCount)
    println(escapeCount - codeCount)
}