package mundhahs.julian.Werkzeug

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.dom.create
import kotlinx.html.js.hr
import mundhahs.julian.Fachwert.Bruch
import mundhahs.julian.Service.GausElminiationSolver
import mundhahs.julian.Service.LinearEquationSolver
import mundhahs.julian.Material.Gleichung
import mundhahs.julian.Material.LinearEquationSystem
import mundhahs.julian.Fachwert.br
import mundhahs.julian.Fachwert.toBruch
import mundhahs.julian.prepend
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

var numEquations = 4
var numUnknowns = 4

@Suppress("unused")
fun setupListeners() {
    val unknown = document.getElementById("numUnknows") as HTMLInputElement
    val equations = document.getElementById("numEquations") as HTMLInputElement

    unknown.addEventListener("input", {
        val difference = unknown.value.toInt() - numUnknowns

        when {
            difference >= 0 -> repeat(difference, {
                numUnknowns++
                addUnknown()
            })
            difference < 0 -> repeat(-difference, {
                numUnknowns--
                removeUnknown()
            })
        }
    })
    equations.addEventListener("input", {
        val difference = equations.value.toInt() - numEquations

        when {
            difference >= 0 -> repeat(difference, {
                numEquations++
                addEquation()
            })
            difference < 0 -> repeat(-difference, {
                numEquations--
                removeEquation()
            })
        }
    })
}

fun removeEquation() {
    document.getElementById("row${numEquations + 1}")!!.remove()
}

fun addEquation() {
    document.getElementById("tbody")!!.append {
        tr {
            id = "row$numEquations"
            th(scope = ThScope.row) { +numEquations.toString() }
            for (j in 1..numUnknowns) {
                td {
                    id = "r$numEquations c$j"
                    input(type = InputType.number) { }
                }
            }
            td {
                id = "r$numEquations c${numUnknowns + 1}"
                input(type = InputType.number) { }
            }
        }
    }
}

fun removeUnknown() {
    for (i in 0..numEquations) {
        document.getElementById("r$i c${numUnknowns + 1}")!!.remove()

        document.getElementById("r$i c${numUnknowns + 2}").let {
            it!!.id = "r$i c${numUnknowns + 1}"
        }
    }
}

fun addUnknown() {
    for (i in 1..numEquations) {
        document.getElementById("r$i c$numUnknowns").let {
            it!!.id = "r$i c${numUnknowns + 1}"
        }

        document.getElementById("r$i c${numUnknowns + 1}").let {
            val newElement = document.createElement("td")
            newElement.id = "r$i c$numUnknowns"
            val input = document.create.input(type = InputType.number) { }
            newElement.append(input)
            it!!.parentNode!!.insertBefore(newElement, it)
        }
    }

    //add header seperately
    document.getElementById("r0 c$numUnknowns").let {
        it!!.id = "r0 c${numUnknowns + 1}"
    }

    document.getElementById("r0 c${numUnknowns + 1}").let {
        val newElement = document.createElement("th")
        newElement.setAttribute("scope", "col")
        newElement.id = "r0 c$numUnknowns"
        newElement.textContent = "x$numUnknowns"
        it!!.parentNode!!.insertBefore(newElement, it)
    }
}

@Suppress("unused")
fun setupTable() {
    document.getElementById("table")!!.append {
        thead {
            tr {
                id = "row0"
                th(scope = ThScope.col) { +"#" }
                for (i in 1..numUnknowns) {
                    th(scope = ThScope.col) {
                        id = "r0 c$i"
                        +"x$i"
                    }
                }
                th(scope = ThScope.col) {
                    id = "r0 c${numUnknowns + 1}"
                    +"="
                }
            }
        }
        tbody {
            id = "tbody"
            for (i in 1..numEquations) {
                tr {
                    id = "row$i"
                    th(scope = ThScope.row) { +i.toString() }
                    for (j in 1..numUnknowns) {
                        td {
                            id = "r$i c$j"
                            input(type = InputType.number) { }
                        }
                    }
                    td {
                        id = "r$i c${numUnknowns + 1}"
                        input(type = InputType.number) { }
                    }
                }
            }
        }
    }
}

@Suppress("unused")
fun calculate() {
    val equations: MutableList<Gleichung> = mutableListOf()

    for (i in 1..numEquations) {
        val coefficents: Array<Bruch> = Array(numUnknowns - 1, { _ -> 0.br })
        for (j in 1..numUnknowns) {
            coefficents[j - 1] = (document.getElementById("r$i c$j")?.firstChild as HTMLInputElement).value.toBruch
        }
        val result: Bruch = (document.getElementById("r$i c${numUnknowns + 1}")?.firstChild as HTMLInputElement).value.toBruch

        equations.add(Gleichung(result, coefficents))
    }

    val lgs = LinearEquationSystem(equations)

    val solver: LinearEquationSolver = GausElminiationSolver(lgs)
    solver.solve()

    when(solver.isSolutionSetEmpty()) {
        false -> displayResultOfSolver(solver)
        true -> displaySolutionSetEmptyMessage()
    }


}

fun displaySolutionSetEmptyMessage() {
    document.getElementById("result")!!.prepend {
        div(classes = "alert alert-danger alert-dismissible fade show") {
            role = "alert"
            p {
                +"The Solution set is empty!"
            }
            button(type = ButtonType.button, classes = "close") {
                attributes["data-dismiss"] = "alert"
                attributes["aria-label"] = "Close"
                span {
                    attributes["aria-hidden"] = "true"
                    unsafe { +"&times;" }
                }
            }
        }
    }
}

fun displayResultOfSolver(solver: LinearEquationSolver) {
    val values = (solver as GausElminiationSolver).orderedResult()

    document.getElementById("result")!!.prepend {
        div(classes = "alert alert-success alert-dismissible fade show") {
            role = "alert"
            values.forEach {
                p {
                    +it.value.getResult()
                }
            }
            button(type = ButtonType.button, classes = "close") {
                attributes["data-dismiss"] = "alert"
                attributes["aria-label"] = "Close"
                span {
                    attributes["aria-hidden"] = "true"
                    unsafe { +"&times;" }
                }
            }
        }
    }
    document.getElementById("result")!!.prepend { hr { } }
}

@Suppress("unused")
fun fillEmptyInputsWithZero() {
    for (i in 1..numEquations) {
        for (j in 1..numUnknowns + 1) {
            (document.getElementById("r$i c$j")?.firstChild as HTMLInputElement).let {
                if ((it.value == undefined) or (it.value.isEmpty()) or (it.value.isBlank())) {
                    it.value = "0"
                }
            }
        }
    }
}