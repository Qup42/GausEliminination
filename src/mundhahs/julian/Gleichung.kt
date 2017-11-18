package mundhahs.julian

import java.util.*

class Gleichung(val ergebnis: Bruch, val faktoren: Array<Bruch>) {

    override fun toString(): String {
        var toString: String = "["
        faktoren.forEach { faktor -> toString += faktor }
        return "$toString] = $ergebnis"
    }

    operator fun plus(gleichung: Gleichung): Gleichung
    {
        val neueFaktoren: Array<Bruch> = Array<Bruch>(maxOf(faktoren.size, gleichung.faktoren.size),{i -> (faktoren.getOrElse(i, {0.br}))+gleichung.faktoren.getOrElse(i, {0.br})})

        return Gleichung(ergebnis+gleichung.ergebnis, neueFaktoren)
    }

    operator fun div(bruch: Bruch): Gleichung
    {
        return Gleichung(ergebnis/bruch, Array<Bruch>(faktoren.size, {i -> (faktoren[i]/bruch)}))
    }

    operator fun minus(gleichung: Gleichung): Gleichung
    {
        return this+-gleichung
    }

    operator fun unaryMinus(): Gleichung = Gleichung(-ergebnis, Array<Bruch>(faktoren.size, {i -> -faktoren.get(i)}))

    operator fun times(faktor: Bruch): Gleichung
    {
        return Gleichung(ergebnis*faktor, Array<Bruch>(faktoren.size, {i -> (faktoren[i]*faktor)}))
    }

    fun fillTo(size: Int): Gleichung
    {
        return Gleichung(ergebnis, Array<Bruch>(size, {i -> faktoren.getOrElse(i, {0.br})}))
    }
}
