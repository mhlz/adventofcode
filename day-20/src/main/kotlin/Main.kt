


fun findDivisors(n: Int): Set<Int> {
    val ret = hashSetOf<Int>()
    for(i in 1..(Math.sqrt(n.toDouble()) + 1).toInt()) {
        if(n % i == 0) {
            ret.add(i)
            if(i * i != n) {
                ret.add(n / i)
            }
        }
    }

    return ret
}

fun countPresents(n: Int): Int {
    return findDivisors(n).sum() * 10
}

fun countPresents50(n: Int): Int {
    val step = findDivisors(n).filter { n / it <= 50 }

    return step.sum() * 11
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {

    var i = 100
    while(true) {
        val count = countPresents50(i)
//        println(count)
        if(count >= 29000000) {
            println(i)
            return
        }

        i++
    }

}
