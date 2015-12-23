import java.io.File

var lights = hashMapOf<Pair<Int, Int>, Boolean>()

fun countOnNeighbours(coord: Pair<Int, Int>): Int {

    var sum = 0

    for(y in (coord.second - 1)..(coord.second + 1)) {
        for(x in (coord.first - 1)..(coord.first + 1)) {
            if(lights[Pair(x, y)] ?: false) {
                sum++
            }
        }
    }

    if(lights[coord]!!) {
        sum--
    }

    return sum
}

var maxY = 0
var maxX = 0

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {


    File("input.txt").readLines().forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if(x > maxX) {
                maxX = x
            }
            if(y > maxY) {
                maxY = y
            }
            val coords = Pair(x, y)
            lights.put(coords, c == '#')
        }
    }

    for(i in 1..100) {
        lights.put(Pair(0, 0), true)
        lights.put(Pair(0, maxY), true)
        lights.put(Pair(maxX, 0), true)
        lights.put(Pair(maxX, maxY), true)

        val newState = hashMapOf<Pair<Int, Int>, Boolean>()

        lights.forEach { pair, b ->
            val neighbours = countOnNeighbours(pair)
            if(b) {
                newState.put(pair, neighbours == 2 || neighbours == 3)
            } else {
                newState.put(pair, neighbours == 3)
            }
        }

        lights = newState

        lights.put(Pair(0, 0), true)
        lights.put(Pair(0, maxY), true)
        lights.put(Pair(maxX, 0), true)
        lights.put(Pair(maxX, maxY), true)
    }

    println(lights.values.count { it })
}
