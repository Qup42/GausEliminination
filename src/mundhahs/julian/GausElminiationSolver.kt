package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */
class GausElminiationSolver : LinearEquationSolver {
    override fun solve(system: LinearEquationSystem): LinearEquationSystem {
        //Unter der Annamhe, dass alle gleich lang sind
        checkApparentDeterminationOfEqautionSystem(system.gleichungen)

        var sprungStellen = 0
        var freieParameter = 0

        for (spalte in 0 until system.getEquationsLength()) {
            //eigentlich sollten die spalten in diesem fall lieber getauscht werden
            val notZeroRowIndex: Int? = findNotZeroRow(system, spalte, sprungStellen)

            if (notZeroRowIndex == null) {
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
        var emptySolutionSet: Boolean = false
        for (i in sprungStellen until system.getEquationsAmount()) {
            if (system[i].ergebnis.zähler == 0) {
                println("Die Lösungsmenge ist leer!")
                emptySolutionSet = true
            }
        }

        println("Fertig")
        println("Stats:\nFreie Parameter: $freieParameter\nSprungstellen: $sprungStellen\nLösungsmenge ist leer: $emptySolutionSet\n")

        harvest(system, freieParameter)

        return system
    }

    private fun swap(gleichungen: Array<Gleichung>, index1: Int, index2: Int) {
        gleichungen[index1] = gleichungen[index2].also { gleichungen[index2] = gleichungen[index1] }
    }

    private fun findNotZeroRow(system: LinearEquationSystem, spalte: Int, zeile: Int): Int? {

        return (zeile until system.getEquationsAmount()).firstOrNull { system[it, spalte].zähler != 0 }
    }

    private fun checkApparentDeterminationOfEqautionSystem(gleichungen: Array<Gleichung>) {
        if (gleichungen.size == gleichungen[0].faktoren.size) {
            println("Das Gleichungsystem ist scheinbar bestimmt.")
        }
    }

    fun harvest1(system: LinearEquationSystem): Map<Int, Ergebnis> {
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
//        console.log(unbestimmt)
//        console.log(bestimmt)

        val results: MutableMap<Int, Wert> = mutableMapOf()

        bestimmt.reversed().forEach { position ->
            run {
                val freieParameter: MutableList<Pair<FreierParameter, Bruch>> = mutableListOf()
                //console.log("x${position.first+1} in row ${position.second} = ${system.gleichungen[position.second].faktoren[position.first]}")
                //console.log("x${position.first + 1}")
                for (i in position.first + 1 until system.getEquationsLength()) {
                    val value = system[position.second, i]
                    if (value != 0.br) {
                        //console.log(value)
                        freieParameter.add(Pair(FreierParameter(i+1), system[position.second, i]))
                    }
                }
                val ergebnis = Wert(position.first + 1, system[position.second].ergebnis, freieParameter)
                //console.log(ergebnis)
                results.put(position.first,ergebnis)
            }
        }

        val freierParameter: Map<Int, FreierParameter> = unbestimmt.associate { it to FreierParameter(it+1) }

        val total:MutableMap<Int, Ergebnis> = mutableMapOf()
        total.let {
            it.putAll(results)
            it.putAll(freierParameter)
        }

        console.log(total)

        return total.toList().sortedBy(Pair<Int, Ergebnis>::first).toMap()
    }

    private fun harvest(system: LinearEquationSystem, freieParameter: Int) {
        val results: MutableMap<Int, Bruch> = mutableMapOf()

        for (row in system.getEquationsAmount() - 1 downTo 0) {
            var result: Bruch = system[row].ergebnis
            val freeParams: MutableMap<Int, Bruch> = mutableMapOf()
            //replace the already known variables
            for (fillIn in row + 1 until system.getEquationsLength()) {
                if (results.containsKey(fillIn)) {
                    result += system[row].faktoren[fillIn] * results[fillIn]!!
                } else {
                    freeParams[fillIn] = system[row, fillIn]
                }
            }
            results[row] = result

            println("x${row + 1}=$result${formatMap(freeParams)}")
        }
    }

    private fun formatMap(map: Map<Int, Bruch>): String {
        return (if (map.isEmpty()) "" else " + ") + map.map { "${it.value}*x${it.key + 1}" }.joinToString(" + ")
    }
}