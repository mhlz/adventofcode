import org.json.JSONArray
import org.json.JSONObject
import java.io.File

data class Guest(
        val name: String,
        val happiness: MutableMap<String, Int>
)

var max = Integer.MIN_VALUE

val guests = arrayListOf<Guest>()


fun calcHappiness(seating: List<Guest>): Int {
    var sum = 0

    seating.forEachIndexed { i, guest ->

        val left = (i - 1 + seating.size) % seating.size
        val right = (i + 1 + seating.size) % seating.size

        val neighbours = listOf(seating[left], seating[right])

        neighbours.forEach {
            val hap = guest.happiness[it.name]!!
            sum += hap
        }
    }

    return sum
}

fun findHighestHap(start: Int = 0, seating: MutableList<Guest> = arrayListOf()) {

    if(start == guests.size) {
        val test = calcHappiness(seating)
        if(test > max) {
            max = test
        }
    } else {
        for(i in 0..(guests.size - 1)) {
            if(!seating.contains(guests[i])) {
                val copy = arrayListOf<Guest>()
                copy.addAll(seating)
                copy.add(guests[i])
                findHighestHap(start + 1, copy)
            }
        }
    }
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {

    val regex = "(.+?) would (lose|gain) (\\d+) happiness units by sitting next to (.+?)\\.".toRegex()

    File("input.txt").readLines().forEach {
        val match = regex.matchEntire(it)!!
        val name = match.groups[1]!!.value
        val neg = match.groups[2]!!.value.equals("lose")
        val hap = match.groups[3]!!.value.toInt() * (if(neg) -1 else 1)
        val neighbour = match.groups[4]!!.value

        var guest = guests.filter { it.name.equals(name) }.firstOrNull()
        if(guest == null) {
            guest = Guest(name, hashMapOf())
            guests.add(guest)
        }

        guest.happiness.put(neighbour, hap)
    }

    findHighestHap()
    println(max)

    // part 2

    val me = Guest("me", hashMapOf())
    guests.forEach {
        me.happiness.put(it.name, 0)
        it.happiness.put(me.name, 0)
    }

    guests.add(me)

    max = Integer.MIN_VALUE
    findHighestHap()
    println(max)
}
