package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */
class GausElminiationSolver(system: LinearEquationSystem) : LinearEquationSolver(system) {

    var freieParameter = 0
    var sprungStellen = 0

    override fun solve(): LinearEquationSystem {
        //Unter der Annamhe, dass alle gleich lang sind
        checkApparentDeterminationOfEqautionSystem(system.gleichungen)

        for (spalte in 0 until system.getEquationsLength()) {
            //eigentlich sollten die spalten in diesem fall lieber getauscht werden
            val notZeroRowIndex: Int = findNotZeroRow(spalte, sprungStellen)

            if(sprungStellen==system.getEquationsAmount())
                break

            if (notZeroRowIndex == -1) {
                freieParameter++
                continue
            } else {
                //die gleichung mit dem faktor ungleich 0 wird mit einer anderen getauscht, so dass sie an der richtigen steht (=über der letzten sprungstelle)
                if (notZeroRowIndex != sprungStellen) swap(system.gleichungen, notZeroRowIndex, sprungStellen)

                system[sprungStellen] = system[sprungStellen] / system[sprungStellen, spalte]
                (0 until system.getEquationsAmount())
                        .filter { it != sprungStellen }
                        .forEach { system[it] = system[it] - system[sprungStellen] * system[it, spalte] }
                sprungStellen++
                //zero out below
            }
        }

        //lösungsmenge!={leereMenge} prüfen
        val emptySolutionSet: Boolean = isSolutionSetEmpty()
        if(emptySolutionSet) {
            println("Die Lösungsmenge ist leer!")
        }

        println("Fertig")
        println("Stats:\nFreie Parameter: $freieParameter\nSprungstellen: $sprungStellen\nLösungsmenge ist leer: $emptySolutionSet\n")

        return system
    }

    private fun swap(gleichungen: Array<Gleichung>, index1: Int, index2: Int) {
        gleichungen[index1] = gleichungen[index2].also { gleichungen[index2] = gleichungen[index1] }
    }

    private fun findNotZeroRow(spalte: Int, zeile: Int): Int {
        //we're cutting of the first zeile elements and only return the index of the match.
        //zeile is therefore added back when returning to get the total and not the position relative to the start of the search
        val index = (zeile until system.getEquationsAmount()).indexOfFirst { system[it, spalte].numerator != 0 }
        return index+zeile
    }

    private fun checkApparentDeterminationOfEqautionSystem(gleichungen: Array<Gleichung>) {
        if (gleichungen.size == gleichungen[0].faktoren.size) {
            println("Das Gleichungsystem ist scheinbar bestimmt.")
        }
    }

    override fun orderedResult(): Map<Int, Ergebnis> {
        val bestimmt: MutableList<Pair<Int, Int>> = mutableListOf()
        /*similar to unbestimmt indexes*/
        system.gleichungen.forEach { gleichung ->
            run {
                if (gleichung.faktoren.indexOfFirst { it == 1.br } != -1)
                    bestimmt.add(Pair(gleichung.faktoren.indexOfFirst { it == 1.br }, system.gleichungen.indexOf(gleichung)))
            }
        }
        val bestimmtIndex = bestimmt.map { it.first }
        val unbestimmt: List<Int> = (0 until system.getEquationsLength()).toList().subtract(bestimmtIndex).toList()

        val results: MutableMap<Int, Wert> = mutableMapOf()

        bestimmt.reversed().forEach { position ->
            run {
                val freieParameter: MutableList<Pair<FreierParameter, Bruch>> = mutableListOf()
                for (i in position.first + 1 until system.getEquationsLength()) {
                    val value = system[position.second, i]
                    if (value != 0.br) {
                        freieParameter.add(Pair(FreierParameter(i+1), system[position.second, i]))
                    }
                }
                val ergebnis = Wert(position.first + 1, system[position.second].ergebnis, freieParameter)
                results.put(position.first,ergebnis)
            }
        }

        val freierParameter: Map<Int, FreierParameter> = unbestimmt.associate { it to FreierParameter(it+1) }

        val total:MutableMap<Int, Ergebnis> = mutableMapOf()
        total.let {
            it.putAll(results)
            it.putAll(freierParameter)
        }

        return total.toList().sortedBy(Pair<Int, Ergebnis>::first).toMap()
    }

    override fun isSolutionSetEmpty(): Boolean
    {
        return (sprungStellen until system.getEquationsAmount()).none { system[it].ergebnis.numerator!=0 }
    }
}