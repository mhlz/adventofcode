import java.io.File

/**
 * @author Mischa Holz
 */
val niceLetters = "aeiou";

val naughtyStrings = listOf("ab", "cd", "pq", "xy")

fun containsNiceLetters(it: String): Boolean {
    var count = 0;

    it.forEach {
        if(it in niceLetters) {
            count++
        }
    }

    return count >= 3;
}

fun containsDoubleLetter(it: String): Boolean {
    for(i in 0..(it.length - 2)) {
        if(it[i] == it[i + 1]) {
            return true;
        }
    }

    return false;
}

fun doesNotContainNaughtyStrings(str: String): Boolean {
    naughtyStrings.forEach {
        if(str.contains(it)) {
            return false;
        }
    }

    return true;
}

fun containsRepeatingGroups(it: String): Boolean {
    it.forEachIndexed { i, c ->
        if(i + 1 >= it.length) {
            return@forEachIndexed
        }
        val pair = "" + it[i] + it[i + 1];

        for(j in 0..(it.length - 3)) {
            val pairToCheck = it.substring(j, j + 2);
            if(pairToCheck == pair) {
                if(Math.abs(i + 1 - j + 1) >= 4) {
                    return true;
                }
            }
        }
    }

    return false;
}

fun containsDoubleLetter2(it: String): Boolean {
    for(i in 0..(it.length - 3)) {
        if(it[i] == it[i + 2]) {
            return true;
        }
    }

    return false;
}

fun task1() {
    val niceStrings = File("input.txt")
            .readLines()
            .filter {
                containsNiceLetters(it) && containsDoubleLetter(it) && doesNotContainNaughtyStrings(it)
            }
            .count()

    println(niceStrings)
}

fun task2() {
    val niceStrings = File("input.txt")
            .readLines()
            .filter {
                containsDoubleLetter2(it) && containsRepeatingGroups(it)
            }
            .count()

    println(niceStrings)
}

fun main(args: Array<String>) {
    println("task 1")
    task1()
    println("task 2")
    task2()
}