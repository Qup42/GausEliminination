package mundhahs.julian

import mundhahs.julian.Fachwert.Bruch

/**
 * Created by Julian Mundhahs on 18.10.2017.
 */

class LinearEquationSystem() {
    lateinit var gleichungen: Array<Gleichung>

    constructor(gleichungen: List<Gleichung>): this(gleichungen.toTypedArray())

    constructor(gleichungen: Array<Gleichung>): this() {
        check(gleichungen.isNotEmpty())

        val equalLength = gleichungen.all { it.faktoren.size==gleichungen[0].faktoren.size }
        check(equalLength, { "All Arrays must have the same length" } )

        this.gleichungen = gleichungen
    }

    constructor(vararg gleichungen: Gleichung): this() {
        check(gleichungen.isNotEmpty())

        val max: Int = gleichungen.maxBy { it.faktoren.size }!!.faktoren.size

        this.gleichungen = gleichungen.toList().toTypedArray()

        this.gleichungen = Array(gleichungen.size, { i -> this.gleichungen[i].fillTo(max)})
    }

    operator fun get(i: Int): Gleichung {
        return gleichungen[i]
    }

    operator fun get(i: Int, j: Int): Bruch {
        return gleichungen[i].faktoren[j]
    }

    operator fun set(i: Int, b: Gleichung) {
        gleichungen[i] = b
    }

    operator fun set(i: Int, j: Int, b: Bruch) {
        gleichungen[i].faktoren[j] = b
    }

    fun getEquationsAmount(): Int
    {
        return gleichungen.size
    }

    fun getEquationsLength(): Int
    {
        return gleichungen[0].faktoren.size
    }

}