import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {


    val ints = arrayListOf<Int>()

    File("input.txt").readLines().forEach {
        ints.add(it.toInt())
    }

    var combinations = 0

    var minNumber = Integer.MAX_VALUE

    for(i in 1..(Math.pow(2.0, ints.size.toDouble()).toInt() + 2)) {
        var sum = 0
        var number = 0
        for(j in 0..(ints.size - 1)) {
            if((i and (1 shl j)) != 0) {
                sum += ints[j]
                number++
            }
        }
        if(sum == 150) {
            combinations++
            if(number < minNumber) {
                minNumber = number
            }
        }
    }

    println(combinations)

    var minNumberCombinations = 0

    for(i in 1..(Math.pow(2.0, ints.size.toDouble()).toInt() + 2)) {
        var sum = 0
        var number = 0
        for(j in 0..(ints.size - 1)) {
            if((i and (1 shl j)) != 0) {
                sum += ints[j]
                number++
            }
        }
        if(sum == 150) {
            combinations++
            if(number == minNumber) {
                minNumberCombinations++
            }
        }
    }

    println(minNumberCombinations)
}
