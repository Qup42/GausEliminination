package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.10.2017.
 */

class LinearEquationSystem(vararg gleichungen: Gleichung) {
    var gleichungen: Array<Gleichung>

    init {
        check(gleichungen.size != 0)

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