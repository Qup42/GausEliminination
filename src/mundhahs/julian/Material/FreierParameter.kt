package mundhahs.julian.Material

import mundhahs.julian.Material.Ergebnis

class FreierParameter(index: Int) : Ergebnis(index) {
    override fun getResult(): String {
        return "${getName()}=${getName()}"
    }
}
