package mundhahs.julian

import kotlinx.html.dom.append
import kotlinx.html.js.hr
import kotlinx.html.p
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    val a = Gleichung(2.br, arrayOf(1.br, 1.br, 5.br, 3.br, 3.br))
    val b = Gleichung(3.br, arrayOf(2.br, 3.br, (-1).br, 7.br, 3.br))
    val c = Gleichung(3.br, arrayOf(2.br, 3.br, 2.br, 12.br, 3.br))
    val d = Gleichung(1.br, arrayOf(4.br, 2.br, 0.br, 69.br, 3.br))
    val lgs = LinearEquationSystem(a, b, c, d)

    val solver: LinearEquationSolver = GausElminiationSolver()

    val solved = solver.solve(lgs)

    val values = (solver as GausElminiationSolver).harvest1(solved)

    console.log(values.keys.joinToString(", "))
    window.onload = {
        values.forEach { console.log(it.value.getResult()) }
        val resultDiv = document.getElementById("result")
        values.forEach {
            resultDiv!!.append {
                p {
                    +it.value.getResult()
                }
            }
        }
        resultDiv!!.append { hr { } }
    }
}

val Int.br: Bruch
    get() {
        return Bruch(this, 1)
    }

val String.toBruch: Bruch
    get() {
        return Bruch(this.toInt(), 1)
    }
