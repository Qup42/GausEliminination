package mundhahs.julian

import mundhahs.julian.Material.LinearEquationSystem

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */

abstract class LinearEquationSolver(protected val system: LinearEquationSystem) {

    abstract fun solve(): LinearEquationSystem
    abstract fun isSolutionSetEmpty(): Boolean
    abstract fun orderedResult(): Map<Int, Ergebnis>

}