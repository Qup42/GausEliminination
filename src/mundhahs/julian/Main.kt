package mundhahs.julian

object Main {

    @JvmStatic fun main(args: Array<String>) {
        /*
        val a = Gleichung(2.br, arrayOf(1.br, 1.br, 5.br, 3.br, 3.br))
        val b = Gleichung(3.br, arrayOf(2.br,3.br, -1.br, 7.br, 3.br))
        val c = Gleichung(3.br, arrayOf(2.br,3.br, 2.br, 12.br, 3.br))
        val d = Gleichung(1.br, arrayOf(4.br,2.br, 0.br, 69.br, 3.br))
        val e = Gleichung(1.br, arrayOf(4.br,2.br, 0.br, 1.br, 0.br))
        */


        //val lgs = Gleichungssystem(a,b,c, d, e)

        val a = Gleichung(3.br, arrayOf(1.br, 1.br))
        val b = Gleichung(1.br, arrayOf(1.br, 0.br))

        val lgs = Gleichungssystem(a,b)

        lgs.solveSystem()
    }
}

public val Int.br: Bruch
    get() {return Bruch(this, 1)}
