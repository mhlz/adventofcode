
import java.io.File
import kotlin.util.measureTimeMillis

/**
 * @author Mischa Holz
 */

val lights = hashMapOf<Pair<Int, Int>, Int>()

fun applyToRect(one: Pair<Int, Int>, two: Pair<Int, Int>, function: (Pair<Int, Int>) -> Unit) {
    for (x in one.first..two.first) {
        for (y in one.second..two.second) {
            function(Pair(x, y))
        }
    }
}

interface LightSwitcher {
    fun toggle(): (coords: Pair<Int, Int>) -> Unit

    fun turnOn(): (coords: Pair<Int, Int>) -> Unit

    fun turnOff(): (coords: Pair<Int, Int>) -> Unit
}

class Task2 : LightSwitcher {
    override fun toggle() = { it: Pair<Int, Int> -> lights.put(it, lights[it]!! + 2); Unit }

    override fun turnOn() = { it: Pair<Int, Int> -> lights.put(it, lights[it]!! + 1); Unit }

    override fun turnOff() = { coords: Pair<Int, Int> ->
        if(lights[coords]!! > 0) {
            lights.put(coords, lights[coords]!! - 1)
        }
    }
}

class Task1 : LightSwitcher {
    override fun toggle() = { coords: Pair<Int, Int> ->
        val current = lights[coords]!!
        if(current == 1) {
            lights.put(coords, 0)
        } else {
            lights.put(coords, 1)
        }
        Unit
    }

    override fun turnOn() = { coords: Pair<Int, Int> ->
        lights.put(coords, 1)
        Unit
    }

    override fun turnOff() = { coords: Pair<Int, Int> ->
        lights.put(coords, 0)
        Unit
    }
}

fun executeTask(switcher: LightSwitcher) {
    println("init...")
    applyToRect(Pair(0, 0), Pair(999, 999)) { lights.put(it, 0) }

    println("reading file...")

    var sum = 0L
    var count = 0L

    File("input.txt")
            .readLines()
            .forEach {
                sum += measureTimeMillis {
                    var func = switcher.toggle()
                    var offset = -1

                    if(it.startsWith("turn on")) {
                        func = switcher.turnOn()
                        offset = 0
                    } else if(it.startsWith("turn off")) {
                        func = switcher.turnOff()
                        offset = 0
                    }

                    val parts = it.split(" ")
                    val oneCoords = parts[2 + offset].split(",")
                    val twoCoords = parts[4 + offset].split(",")

                    val one = Pair(oneCoords[0].toInt(), oneCoords[1].toInt())
                    val two = Pair(twoCoords[0].toInt(), twoCoords[1].toInt())

                    applyToRect(one, two, func)

                    count++
                }
            }


    println("lights that are on...")
    println(lights.values.count { it > 0 })

    println("sum of the lights...")
    println(lights.values.sum())
}


fun main(args: Array<String>) {
    println("task 1")
    executeTask(Task1())
    println("task 2")
    executeTask(Task2())
}