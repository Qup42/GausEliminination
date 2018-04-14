package mundhahs.julian

abstract class Ergebnis(val index: Int) {
    fun getName(): String {
        return "x$index"
    }
    abstract fun getResult(): String
}