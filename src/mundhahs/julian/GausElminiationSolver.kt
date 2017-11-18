package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */
class GausElminiationSolver: LinearEquationSolver {
    override fun solve(system: LinearEquationSystem): LinearEquationSystem {
        //Unter der Annamhe, dass alle gleich lang sind
        checkApparentDeterminationOfEqautionSystem(system.gleichungen)

        //move the zeros away from the diagonal - sort of
        preSortEquations(system.gleichungen)

        //zero out the bottom left half
        tranformToUpperTriangularMatrix(system.gleichungen)

        //only leave one coefficent != 0 by subtracting the values from the bottom to the top only leaving the diagonal
        addRowsTogether(system.gleichungen)

        println("Fertig")

        return system
    }

    private fun preSortEquations(gleichungen: Array<Gleichung>) {
        for (i in 0 until gleichungen.size) {
            //TODO: funktioniert noch nicht ganz
            if (gleichungen[i].faktoren[i].equals(0)) {
                println("Faktor ist null")

                var deltaI = 1
                if (i == gleichungen.size - 1) {
                    deltaI = -1
                }

                val temp: Gleichung = gleichungen[i]
                gleichungen[i] = gleichungen[i + deltaI]
                gleichungen[i + deltaI] = temp
            }
        }
    }

    private fun addRowsTogether(gleichungen: Array<Gleichung>) {
        for (i in gleichungen.size - 1 downTo 0) {
            println(gleichungen[i])
            for (j in i - 1 downTo 0) {
                gleichungen[j] = gleichungen[j] - (gleichungen[i] / gleichungen[i].faktoren[i] * gleichungen[j].faktoren[i])
            }
        }
    }

    private fun tranformToUpperTriangularMatrix(gleichungen: Array<Gleichung>) {
        for (i in 0 until gleichungen.size) {
            for (j in gleichungen.size - 1 downTo i + 1) {
                try {
                    gleichungen[j] = gleichungen[j] - (gleichungen[i] / gleichungen[i].faktoren[i] * gleichungen[j].faktoren[i])
                } catch (a: ArithmeticException) {
                    throw Exception("Das Gleichungssystem ist unterbesetzt!")
                }
            }
        }
    }

    private fun checkApparentDeterminationOfEqautionSystem(gleichungen: Array<Gleichung>) {
        if (gleichungen.size == gleichungen[0].faktoren.size) {
            println("Das Gleichungsystem ist scheinbar bestimmt.")
        }
    }
}