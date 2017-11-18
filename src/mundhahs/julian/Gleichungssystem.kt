package mundhahs.julian

/**
 * Created by Julian Mundhahs on 18.10.2017.
 */

class Gleichungssystem(vararg gleichungen: Gleichung) {
    var gleichungen: Array<Gleichung>

    init {
        this.gleichungen = gleichungen.toList().toTypedArray()
    }

}