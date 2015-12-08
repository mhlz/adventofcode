
import java.io.File

/**
 * @author Mischa Holz
 */

val wires = hashMapOf<String, Gate>()

data class Gate(val name: String, var inputs: List<String>, var constant: Int? = null, var op: String = "CONSTANT") {

    private var value: Int? = null

    private fun evalInput(input: String): Int {
        if(wires[input] == null) {
            val intInput = input.toInt()

            return intInput.toInt()
        }
        return wires[input]!!.eval()
    }

    public fun eval(): Int {
        if(value != null) {
            return value!!
        }

        when(op) {
            "CONSTANT" -> value = constant!!
            "NOT" -> value = evalInput(inputs[0]).inv()
            "RSHIFT" -> value = evalInput(inputs[0]).shr(constant!!)
            "LSHIFT" -> value = evalInput(inputs[0]).shl(constant!!)
            "OR" -> value = evalInput(inputs[0]) or evalInput(inputs[1])
            "AND" -> value = evalInput(inputs[0]) and evalInput(inputs[1])
            "LINK" -> value = evalInput(inputs[0])
        }

        return value!!
    }

}


fun main(args: Array<String>) {
    File("input.txt")
            .readLines()
            .forEach {
                val parts = it.split(" ")
                if(parts.size == 3) {
                    val input = parts[0]
                    val output = parts[2]

                    try {
                        val intInput = input.toInt()

                        wires.put(output, Gate(output, listOf(), constant = intInput))
                        return@forEach
                    } catch(e: NumberFormatException) {

                    }

                    wires.put(output, Gate(output, listOf(input), null, "LINK"))
                } else if(parts.size == 4) {
                    val input = parts[1]
                    val output = parts[3]

                    wires.put(output, Gate(output, listOf(input), null, "NOT"))
                } else if(parts.size == 5) {
                    val op = parts[1]
                    when(op) {
                        "RSHIFT" -> {
                            val input = parts[0]
                            val amount = parts[2].toInt()
                            val output = parts[4]

                            wires.put(output, Gate(output, listOf(input), amount, op))
                            return@forEach
                        }
                        "LSHIFT" -> {
                            val input = parts[0]
                            val amount = parts[2].toInt()
                            val output = parts[4]

                            wires.put(output, Gate(output, listOf(input), amount, op))
                            return@forEach
                        }
                        "OR" -> {
                            val input1 = parts[0]
                            val input2 = parts[2]

                            val output = parts[4]

                            wires.put(output, Gate(output, listOf(input1, input2), null, op))
                            return@forEach
                        }
                        "AND" -> {
                            val input1 = parts[0]
                            val input2 = parts[2]

                            val output = parts[4]

                            wires.put(output, Gate(output, listOf(input1, input2), null, op))
                            return@forEach
                        }
                    }

                    throw RuntimeException(it)
                } else {
                    throw RuntimeException(it)
                }
            }

    val a = wires["a"]!!
    val b = wires["b"]!!
//  UNCOMMENT FOR TASK 2
//    b.constant = 46065
//    b.op = "CONSTANT"
//    b.inputs = listOf()
    if(wires["a"] == null) {
        println("no wire a found")
    } else {
        println("eval")
        val value = a.eval()
        println(value)
    }
}