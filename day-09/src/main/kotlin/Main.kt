import java.io.File
import java.util.*

val distances = hashMapOf<Pair<String, String>, Int>()

val permutations = hashSetOf<List<String>>()
/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {

    val cities = arrayListOf<String>()

    File("input.txt").forEachLine {
        val regex = "(.*?) to (.*?) = (\\d*?)$".toRegex()
        val result = regex.find(it)!!
        val city1 = result.groups[1]!!.value
        val city2 = result.groups[2]!!.value
        val distance = result.groups[3]!!.value.toInt()

        distances.put(Pair(city1, city2), distance);
        distances.put(Pair(city2, city1), distance);
        cities.add(city1)
        cities.add(city2)
    }

    val distinctCities = arrayListOf<String>()

    distinctCities.addAll(cities.distinct())

    permute(distinctCities)

    val minDistance = permutations.map {
        var distance = 0
        it.forEachIndexed { i, s ->
            if(i == it.size - 1 || distance == Int.MAX_VALUE) return@forEachIndexed
            val city1 = s
            val city2 = it[i + 1]
            val hopDistance = distances[Pair(city1, city2)]
            if(hopDistance == null) {
                distance = Int.MAX_VALUE
                return@forEachIndexed
            }
            distance += hopDistance
        }
        distance
    }. filter { it < Int.MAX_VALUE }
    .min()
    //.max()

    println(minDistance)
}

fun permute(list: ArrayList<String>, k: Int = 0): Unit {

    for(i in (k..list.size - 1)) {
        Collections.swap(list, i, k)
        permute(list, k + 1)
        Collections.swap(list, k, i)
    }

    if(k == list.size - 1) {
        permutations.add(list.clone() as List<String>)
    }
}