package mundhahs.julian

import kotlinx.html.dom.append
import kotlinx.html.js.hr
import kotlinx.html.p
import mundhahs.julian.Fachwert.br
import mundhahs.julian.Material.Gleichung
import mundhahs.julian.Material.LinearEquationSystem
import mundhahs.julian.Service.GausElminiationSolver
import mundhahs.julian.Service.LinearEquationSolver
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    //calculateExampleSystem()
}

fun calculateExampleSystem() {
    val a = Gleichung(2.br, arrayOf(1.br, 1.br, 5.br, 3.br, 3.br))
    val b = Gleichung(3.br, arrayOf(2.br, 3.br, (-1).br, 7.br, 3.br))
    val c = Gleichung(3.br, arrayOf(2.br, 3.br, 2.br, 12.br, 3.br))
    val d = Gleichung(1.br, arrayOf(4.br, 2.br, 0.br, 69.br, 3.br))
    val lgs = LinearEquationSystem(a, b, c, d)
    /*val a = Gleichung(2.br, arrayOf(1.br, 0.br))
    val b = Gleichung(4.br, arrayOf(2.br, 0.br))
    val c = Gleichung(6.br, arrayOf(3.br, 0.br))
    val d = Gleichung(5.br, arrayOf(0.br, 2.br))
    val lgs = LinearEquationSystem(a,b,c,d)*/

    val solver: LinearEquationSolver = GausElminiationSolver(lgs)
    solver.solve()

    val values = (solver as GausElminiationSolver).orderedResult()

    window.onload = {
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