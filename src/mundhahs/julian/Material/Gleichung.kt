package mundhahs.julian.Material

import mundhahs.julian.Fachwert.Bruch
import mundhahs.julian.Fachwert.br

class Gleichung(val ergebnis: Bruch, val faktoren: Array<Bruch>) {

    fun fillTo(size: Int): Gleichung
    {
        return Gleichung(ergebnis, Array(size, { i -> faktoren.getOrElse(i, { 0.br }) }))
    }

    operator fun unaryMinus(): Gleichung = Gleichung(-ergebnis, Array(faktoren.size, { i -> -faktoren[i] }))

    operator fun plus(gleichung: Gleichung): Gleichung
    {
        val neueFaktoren: Array<Bruch> = Array(maxOf(faktoren.size, gleichung.faktoren.size),{ i -> (faktoren.getOrElse(i, {0.br}))+gleichung.faktoren.getOrElse(i, {0.br})})

        return Gleichung(ergebnis + gleichung.ergebnis, neueFaktoren)
    }

    operator fun minus(gleichung: Gleichung): Gleichung
    {
        return this+-gleichung
    }

    operator fun times(faktor: Bruch): Gleichung
    {
        return Gleichung(ergebnis * faktor, Array(faktoren.size, { i -> (faktoren[i] * faktor) }))
    }

    operator fun div(bruch: Bruch): Gleichung
    {
        return Gleichung(ergebnis / bruch, Array(faktoren.size, { i -> (faktoren[i] / bruch) }))
    }

    operator fun contains(bruch: Bruch) = faktoren.contains(bruch)

    operator fun get(i: Int) = faktoren[i]

    operator fun set(i: Int, bruch: Bruch)
    {
        faktoren[i] = bruch
    }

    override operator fun equals(other: Any?): Boolean {
        return (other is Gleichung && other.ergebnis==this.ergebnis && other.faktoren.contentEquals(this.faktoren))
    }

    override fun hashCode(): Int
    {
        var result = ergebnis.hashCode()
        result = 31 * result + faktoren.hashCode()
        return result
    }

    override fun toString(): String
    {
        return "${faktoren.joinToString("",prefix = "[", postfix = "]")} = $ergebnis"
    }
}
