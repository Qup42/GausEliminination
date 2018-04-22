package mundhahs.julian.Material

import mundhahs.julian.Fachwert.Bruch

class Wert(index: Int, val ergebnis: Bruch, val freieParameter: List<Pair<FreierParameter,Bruch>>): Ergebnis(index)
{
    override fun getResult(): String {
        return getName() + "=" + ergebnis.toString() + (if(freieParameter.isNotEmpty()) " - " else "") + freieParameter.joinToString(" - ") { "${it.second}*${it.first.getName()}" }
    }

}