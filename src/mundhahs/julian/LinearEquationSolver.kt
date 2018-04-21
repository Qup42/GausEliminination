package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.11.2017.
 */

abstract class LinearEquationSolver(val system: LinearEquationSystem) {

    abstract fun solve(): LinearEquationSystem
    abstract fun isSolutionSetEmpty(): Boolean
    abstract fun orderedResult(): Map<Int, Ergebnis>

}