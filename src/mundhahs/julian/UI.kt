package mundhahs.julian

import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.dom.create
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

var unknown: HTMLInputElement? = null
var equations: HTMLInputElement? = null
var numEquations = 4
var numUnknowns = 4

fun setupListeners() {
    unknown = document.getElementById("numUnknows") as HTMLInputElement
    equations = document.getElementById("numEquations") as HTMLInputElement

    unknown!!.addEventListener("change", {
        console.log("change")
        val oldValue = numUnknowns
        numUnknowns = unknown!!.value.toInt()

        when {
            numUnknowns == oldValue -> {

            }
            numUnknowns > oldValue -> addUnknown()
            numUnknowns < oldValue -> removeUnknown()
        }
    })
    equations!!.addEventListener("change", {
        console.log("change")
        val old = numEquations
        numEquations = equations!!.value.toInt()

        when {
            numEquations == old -> {

            }
            numEquations > old -> addEquation()
            numEquations < old -> removeEquation()
        }
    })
}

fun removeEquation() {
    println(numEquations)
    document.getElementById("row${numEquations + 1}")!!.remove()
}

fun addEquation() {
    println(numEquations)
    document.getElementById("table")!!.append {
        tbody {
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
}

fun removeUnknown() {
    for (i in 0..numEquations) {
        document.getElementById("r$i c${numUnknowns + 1}")!!.remove()

        document.getElementById("r$i c${numUnknowns+2}").let {
            it!!.id = "r$i c${numUnknowns + 1}"
        }
    }
}

fun addUnknown() {
    for (i in 1..numEquations) {
        document.getElementById("r$i c$numUnknowns").let {
            it!!.id = "r$i c${numUnknowns + 1}"
        }

        document.getElementById("r$i c${numUnknowns+1}").let {
            val newElement = document.createElement("td")
            newElement.id = "r$i c$numUnknowns"
            val input =  document.create.input(type = InputType.number) { }
            newElement.append(input)
            it!!.parentNode!!.insertBefore(newElement, it)
        }
    }

    //add header seperately
    document.getElementById("r0 c$numUnknowns").let {
        it!!.id = "r0 c${numUnknowns + 1}"
    }

    document.getElementById("r0 c${numUnknowns+1}").let {
        val newElement = document.createElement("th")
        newElement.setAttribute("scope", "col")
        newElement.id = "r0 c$numUnknowns"
        newElement.textContent = "x$numUnknowns"
        it!!.parentNode!!.insertBefore(newElement, it)
    }
}

fun setupTable() {
    document.getElementById("table")!!.append {
        thead {
            tr {
                id = "row0"
                th(scope = ThScope.col) { +"Number" }
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