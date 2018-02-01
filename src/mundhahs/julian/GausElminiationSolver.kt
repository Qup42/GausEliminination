package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */
class GausElminiationSolver: LinearEquationSolver {
    override fun solve(system: LinearEquationSystem): LinearEquationSystem {
        //Unter der Annamhe, dass alle gleich lang sind
        checkApparentDeterminationOfEqautionSystem(system.gleichungen)

        var sprungStellen = 0
        var freieParameter = 0

        for(spalte in 0  until system.getEquationsLength())
        {
            //eigentlich sollten die spalten in diesem fall lieber getauscht werden
            val notZeroRowIndex: Int? = findNotZeroRow(system.gleichungen, spalte, sprungStellen)

            if(notZeroRowIndex == null)
            {
                freieParameter++
                continue
            }
            else
            {
                //die gleichung mit dem faktor ungleich 0 wird mit einer anderen getauscht, so dass sie an der richtigen steht (=über der letzten sprungstelle)
                if(notZeroRowIndex!=sprungStellen) swap(system.gleichungen, notZeroRowIndex, sprungStellen)

                system.gleichungen[sprungStellen] = system.gleichungen[sprungStellen] / system.gleichungen[sprungStellen].faktoren[spalte]
                (0 until system.getEquationsAmount())
                        .filter { it != sprungStellen }
                        .forEach { system.gleichungen[it] = system.gleichungen[it] - system.gleichungen[sprungStellen] * system.gleichungen[it].faktoren[spalte] }
                sprungStellen++
                //zero out below
            }
        }

        //lösungsmenge!={leereMenge} prüfen
        var emptySolutionSet: Boolean = false
        for(i in sprungStellen until system.getEquationsAmount())
        {
            if(system.gleichungen[i].ergebnis.zähler == 0)
            {
                println("Die Lösungsmenge ist leer!")
                emptySolutionSet = true
            }
        }

        println("Fertig")
        println("Stats:\nFreie Parameter: $freieParameter\nSprungstellen: $sprungStellen\nLösungsmenge ist leer: $emptySolutionSet\n")

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