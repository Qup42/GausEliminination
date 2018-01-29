package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */
class GausElminiationSolver: LinearEquationSolver {
    override fun solve(system: LinearEquationSystem): LinearEquationSystem {
        //Unter der Annamhe, dass alle gleich lang sind
        checkApparentDeterminationOfEqautionSystem(system.gleichungen)

        var sprungStellen = 0;

        for(spalte in 0  until system.getEquationsLength())
        {
            //eigentlich sollten die spalten in diesem fall lieber getauscht werden
            val notZeroRowIndex: Int? = findNotZeroRow(system.gleichungen, spalte, sprungStellen)

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
                sprungStellen++
                //zero out below
            }
        }

        val freieParameter: Int = system.getEquationsLength() - sprungStellen

        //lösungsmenge!={leereMenge} prüfen

        println("Fertig")
        println("Stats:\nFreie Parameter: $freieParameter\n")

        return system
    }

    private fun swap(gleichungen: Array<Gleichung>, index1: Int, index2: Int)
    {
        gleichungen[index1] = gleichungen[index2].also { gleichungen[index2] = gleichungen[index1] }
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