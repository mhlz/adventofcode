import org.json.JSONArray
import org.json.JSONObject
import java.io.File

var currentTime = 0
val reindeers = arrayListOf<Reindeer>()

data class Reindeer(
        val name: String,
        val speed: Int,
        val speedDuration: Int,
        val pauseDuration: Int,
        var distanceTraveled: Int = 0
) {
    fun doTick() {
        val timeInInterval = currentTime % (speedDuration + pauseDuration)
        if(timeInInterval <= speedDuration) {
            distanceTraveled += speed
        }
    }
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {

    val regex = "(.+?) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds\\.".toRegex()

    File("input.txt").readLines().forEach {
        val match = regex.matchEntire(it)!!
        val name = match.groups[1]!!.value
        val speed = match.groups[2]!!.value.toInt()
        val speedDuration = match.groups[3]!!.value.toInt()
        val pauseDuration = match.groups[4]!!.value.toInt()

        val reindeer = Reindeer(name, speed, speedDuration, pauseDuration)
        reindeers.add(reindeer)
    }

    for(i in 0..2502) {
        currentTime = i
        reindeers.forEach { it.doTick() }
    }

    println(reindeers)

    println(reindeers.maxBy { it.distanceTraveled })
}
