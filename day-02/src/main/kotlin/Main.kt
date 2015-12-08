import java.io.File

/**
 * @author Mischa Holz
 */

fun surfaceArea(l: Int, w: Int, h: Int): Int = 2 * l * w + 2 * w * h + 2 * h * l;

fun neededPresentWrapping(l: Int, w: Int, h: Int): Int {
    val sides = listOf(l * w, l * h, h * w)
    val extraSide = sides.min()!!

    return surfaceArea(l, w, h) + extraSide
}

fun neededBowToWrap(l: Int, w: Int, h: Int): Int {
    val sizeArray = arrayListOf(l, w, h)
    val side1 = sizeArray.min()!!
    sizeArray.remove(side1)
    val side2 = sizeArray.min()!!

    val ribbonToWrap = 2 * side1 + 2 * side2

}

fun main(args: Array<String>) {
    File("input.txt")
            .readLines()
            .map { it.split("x") }
            .map { listOf(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
            .sumBy { neededPresentWrapping(it[0], it[1], it[2]) }
            .let { println(it) }

}