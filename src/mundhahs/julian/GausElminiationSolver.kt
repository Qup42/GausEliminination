package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */
class GausElminiationSolver: LinearEquationSolver {
    override fun solve(system: LinearEquationSystem): LinearEquationSystem {
        //Unter der Annamhe, dass alle gleich lang sind
        checkApparentDeterminationOfEqautionSystem(system.gleichungen)

        var zeile = 0;

        for(spalte in 0  until system.getEquationsLength())
        {
            val notZeroRowIndex: Int? = findNotZeroRow(system.gleichungen, spalte, zeile)

            if(notZeroRowIndex == null)
            {
                continue
            }
            else
            {
                system.gleichungen[notZeroRowIndex] = system.gleichungen[notZeroRowIndex] / system.gleichungen[notZeroRowIndex].faktoren[spalte]
                for(zeroOut in 0 until system.getEquationsAmount())
                {
                    if(zeroOut == notZeroRowIndex) continue
                    system.gleichungen[zeroOut] = system.gleichungen[zeroOut] - system.gleichungen[notZeroRowIndex] * system.gleichungen[zeroOut].faktoren[spalte]
                }
                zeile++
                //zero out below
            }
        }

        println("Fertig")

        return system
    }

    private fun findNotZeroRow(gleichungen: Array<Gleichung>, spalte: Int, zeile: Int): Int?
    {

        return (zeile until gleichungen.size).firstOrNull { gleichungen[it].faktoren[spalte].zähler!=0 }
    }

    private fun checkApparentDeterminationOfEqautionSystem(gleichungen: Array<Gleichung>) {
        if (gleichungen.size == gleichungen[0].faktoren.size) {
            println("Das Gleichungsystem ist scheinbar bestimmt.")
        }
    }
}