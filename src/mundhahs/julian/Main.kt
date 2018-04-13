package mundhahs.julian

fun main(args: Array<String>) {
    Main.main(arrayOf())
}

object Main {

    //@JvmStatic fun main(args: Array<String>) {
    fun main(args: Array<String>) {
        /*
        val a = Gleichung(2.br, arrayOf(1.br, 1.br, 5.br, 3.br, 3.br))
        val b = Gleichung(3.br, arrayOf(2.br,3.br, -1.br, 7.br, 3.br))
        val c = Gleichung(3.br, arrayOf(2.br,3.br, 2.br, 12.br, 3.br))
        val d = Gleichung(1.br, arrayOf(4.br,2.br, 0.br, 69.br, 3.br))
        val e = Gleichung(1.br, arrayOf(4.br,2.br, 0.br, 1.br, 0.br))
        */


        /*
        1 freier parameter
         */
        val a = Gleichung(2.br, arrayOf(1.br, 1.br, 5.br, 3.br, 3.br))
        val b = Gleichung(3.br, arrayOf(2.br,3.br, (-1).br, 7.br, 3.br))
        val c = Gleichung(3.br, arrayOf(2.br,3.br, 2.br, 12.br, 3.br))
        val d = Gleichung(1.br, arrayOf(4.br,2.br, 0.br, 69.br, 3.br))
        val lgs = LinearEquationSystem(a,b,c, d)

        /*val a = Gleichung(2.br, arrayOf(1.br, 1.br, 5.br, 3.br))
        val b = Gleichung(3.br, arrayOf(2.br,3.br, (-1).br, 7.br))
        val c = Gleichung(3.br, arrayOf(2.br,3.br, 2.br, 12.br))
        val d = Gleichung(1.br, arrayOf(4.br,2.br, 0.br, 69.br))
        val lgs = LinearEquationSystem(a,b,c, d)*/

        /*val a = Gleichung(1.br, arrayOf(1.br, 0.br, 0.br, 0.br))
        val b = Gleichung(2.br, arrayOf(0.br, 0.br, 0.br, 1.br))
        val c = Gleichung(3.br, arrayOf(0.br, 1.br, 0.br, 0.br))
        val d = Gleichung(4.br, arrayOf(0.br, 0.br, 1.br, 0.br))
        val lgs = LinearEquationSystem(a,b,c,d)*/



        /*
        val a = Gleichung(3.br, arrayOf(1.br, 1.br))
        val b = Gleichung(1.br, arrayOf(1.br, 0.br))

        val lgs = LinearEquationSystem(a,b)
        */

        val solver: LinearEquationSolver = GausElminiationSolver()

        solver.solve(lgs)

        print(lgs)
    }
}

public val Int.br: Bruch
    get() {return Bruch(this, 1)}
