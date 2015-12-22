import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

data class Ingredient(
        val name: String,
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int
)

val ingredients = arrayListOf<Ingredient>()

fun calcScore(input: Map<Ingredient, Int>): Int {
    var capacity = 0
    var durability = 0
    var flavor = 0
    var texture = 0

    input.entries.forEach {
        capacity += it.key.capacity * it.value
        durability += it.key.durability * it.value
        flavor +=it.key.flavor * it.value
        texture += it.key.texture * it.value
    }

    return Math.max(capacity, 0) * Math.max(durability, 0) * Math.max(flavor, 0) * Math.max(texture, 0)
}

fun calcCalories(input: Map<Ingredient, Int>): Int {
    var calories = 0

    input.entries.forEach {
        calories += it.key.calories * it.value
    }

    return calories
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val regex = "(.+?): capacity (.+?), durability (.+?), flavor (.+?), texture (.+?), calories (.+?)".toRegex()

    File("input.txt").readLines().forEach {
        val match = regex.matchEntire(it)!!
        val name = match.groups[1]!!.value
        val capacity = match.groups[2]!!.value.toInt()
        val durability = match.groups[3]!!.value.toInt()
        val flavor = match.groups[4]!!.value.toInt()
        val texture = match.groups[5]!!.value.toInt()
        val calories = match.groups[6]!!.value.toInt()

        val ingredient = Ingredient(name, capacity, durability, flavor, texture, calories)
        ingredients.add(ingredient)
    }

    val map = linkedMapOf<Ingredient, Int>()
    ingredients.forEach { map.put(it, 1) }

    var max = Integer.MIN_VALUE

    // part 2
    var max500Calories = Integer.MIN_VALUE

    while(true) {

        if(map.values.count { it == 97 } == ingredients.size) {
            break
        }

        var overflow = true
        ingredients.forEachIndexed { i, ingredient ->
            if(map[ingredient] == 97 && overflow) {
                overflow = true
                map.put(ingredient, 1)
            } else {
                val result = if(overflow) (map[ingredient]!! + 1) else map[ingredient]!!
                map.put(ingredient, result)
                overflow = false
            }
        }

        if(map.values.sum() != 100) {
            continue
        }

        val score = calcScore(map)
        if(score > max) {
            max = score
        }

        // part 2
        if(calcCalories(map) == 500 && score > max500Calories) {
            max500Calories = score
        }
    }

    println(max)

    // part2
    println(max500Calories)
}
