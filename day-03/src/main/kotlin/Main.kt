
import java.io.File
import java.util.*

/**
 * @author Mischa Holz
 */

fun task1() {
    val gotPresents = HashMap<Pair<Int, Int>, Int>()
    var santaCoords = Pair(0, 0)

    gotPresents.put(santaCoords, 2)

    File("input.txt")
            .readLines()
            .forEach {
                it.forEach {
                    when(it) {
                        '^' -> santaCoords = santaCoords.copy(second = santaCoords.second + 1)
                        '>' -> santaCoords = santaCoords.copy(first = santaCoords.first + 1)
                        '<' -> santaCoords = santaCoords.copy(first = santaCoords.first - 1)
                        'v' -> santaCoords = santaCoords.copy(second = santaCoords.second - 1)
                    }

                    gotPresents.computeIfPresent(santaCoords) { coords, i -> i + 1 }
                    gotPresents.putIfAbsent(santaCoords, 1)
                }
            }

    println(gotPresents.values.filter { it >= 1 }.count())
}


fun task2() {

    val gotPresents = HashMap<Pair<Int, Int>, Int>()
    var santaCoords = Pair(0, 0)
    var roboCoords = Pair(0, 0)
    var roboSwitch = false

    gotPresents.put(santaCoords, 2)

    File("input.txt")
            .readLines()
            .forEach {
                it.forEach {
                    var coords = santaCoords
                    if(roboSwitch) {
                        coords = roboCoords
                    }
                    when(it) {
                        '^' -> coords = coords.copy(second = coords.second + 1)
                        '>' -> coords = coords.copy(first = coords.first + 1)
                        '<' -> coords = coords.copy(first = coords.first - 1)
                        'v' -> coords = coords.copy(second = coords.second - 1)
                    }

                    gotPresents.computeIfPresent(coords) { coords, i -> i + 1 }
                    gotPresents.putIfAbsent(coords, 1)
                    if(roboSwitch) {
                        roboCoords = coords
                    } else {
                        santaCoords = coords
                    }
                    roboSwitch = !roboSwitch
                }
            }

    println(gotPresents.values.filter { it >= 1 }.count())
}

fun main(args: Array<String>) {
    println("task 1")
    task1()
    println("task 2")
    task2()
}