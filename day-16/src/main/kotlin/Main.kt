
import java.io.File
import kotlin.properties.setValue
import kotlin.reflect.KProperty


data class Aunt(
        val name: String,
        val values: MutableMap<String, Int?>
) {
    var children: Int? by values
    var cats: Int? by values
    var samoyeds: Int? by values
    var pomeranians: Int? by values
    var akitas: Int? by values
    var vizslas: Int? by values
    var goldfish: Int? by values
    var trees: Int? by values
    var cars: Int? by values
    var perfumes: Int? by values
}

operator fun Map<String, Int?>.getValue(aunt: Aunt, property: KProperty<*>): Int? {
    return this[property.name]
}

val aunts = arrayListOf<Aunt>()

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val regex = "(.+?): (.+)".toRegex()

    File("input.txt").readLines().forEach {
        val match = regex.matchEntire(it)!!
        val valueString = match.groups[2]!!.value
        val name = match.groups[1]!!.value

        val values = hashMapOf<String, Int?>()

        valueString.split(", ").forEach {
            val pair = it.split(": ")
            values.put(pair[0], pair[1].toInt())
        }

        val aunt = Aunt(name, values)

        aunts.add(aunt)
    }

    val giftAunt = Aunt("Gifter", hashMapOf())
    giftAunt.children = 3
    giftAunt.cats = 7
    giftAunt.samoyeds = 2
    giftAunt.pomeranians = 3
    giftAunt.akitas = 0
    giftAunt.vizslas = 0
    giftAunt.goldfish = 5
    giftAunt.trees = 3
    giftAunt.cars = 2
    giftAunt.perfumes = 1

    for(aunt in aunts) {
        var same = true
        giftAunt.values.keys.forEach {
            if(aunt.values[it] != null) {
                // part 2
                if(it == "cats" || it == "trees") {
                    same = same && (giftAunt.values[it]!! < aunt.values[it]!!)
                } else if(it == "pomeranians" || it == "goldfish") {
                    same = same && (giftAunt.values[it]!! > aunt.values[it]!!)
                } else {
                    same = same && aunt.values[it] == giftAunt.values[it]
                }
            }
        }
        if(same) {
            println(aunt)
        }
    }
}
