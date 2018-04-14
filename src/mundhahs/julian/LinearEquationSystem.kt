package mundhahs.julian

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

    fun getEquationsAmount(): Int
    {
        return gleichungen.size
    }

    fun getEquationsLength(): Int
    {
        return gleichungen[0].faktoren.size
    }

}