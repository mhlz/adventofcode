import org.json.JSONArray
import org.json.JSONObject
import java.io.File


fun sum(obj: JSONArray, sum: Int = 0): Int {
    var ret = sum
    for(i in 0..(obj.length() - 1)) {
        val sub = obj[i]
        when(sub) {
            is JSONObject -> ret += sum(sub, sum)
            is JSONArray -> ret += sum(sub, sum)
            is Int -> ret += sub
        }
    }
    return ret
}

fun sum(obj: JSONObject, sum: Int = 0): Int {
    var ret = sum
    obj.keys().forEach {
        if (obj[it] is String && "red".equals(obj[it])) {
            return sum
        }
    }

    obj.keys().forEach {
        val sub = obj[it]
        when(sub) {
            is JSONObject -> ret += sum(sub, sum)
            is JSONArray -> ret += sum(sub, sum)
            is Int -> ret += sub
        }
    }
    return ret
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {

    var sum = 0

    File("input.txt").readLines().forEach {
        val regex = "-?\\d+".toRegex()

        regex.findAll(it).forEach {
            val number = it.value.toInt()
            sum += number
        }
    }

    println(sum)

    sum = 0

    val obj = JSONObject(File("input.txt").readText())
    println(sum(obj))
}
