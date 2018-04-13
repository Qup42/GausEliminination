package mundhahs.julian

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
        val newValue = unknown!!.value.toInt()

        when {
            newValue == numUnknowns -> {

            }
            newValue > numUnknowns -> addUnknown()
            newValue < numUnknowns -> removeUnknown()
        }
    })
    equations!!.addEventListener("change", {
        val newValue = equations!!.value.toInt()

        when {
            newValue == numEquations -> {

            }
            newValue > numEquations -> addEquation()
            newValue < numEquations -> removeEquation()
        }
    })
}

fun removeEquation() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun addEquation() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun removeUnknown() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun addUnknown() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun setupTable() {
    document.getElementById("table")!!.append.div {
        span {
            +"hello"
        }
    }
}