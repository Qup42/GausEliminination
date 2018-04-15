import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.FunSpec
import mundhahs.julian.Bruch

/**
 * Created by Julian Mundhahs on 18.10.2017.
 */


class BruchTest: FunSpec() {
    val einsI = 1
    val zweiI = 2
    val dreiI = 3
    val vierI = 4

    val halb = Bruch(einsI, zweiI)
    val drittel = Bruch(einsI, dreiI)
    val dreiViertel = Bruch(dreiI, vierI)
    val zwei = Bruch(zweiI, einsI)
    val eins = Bruch(einsI, einsI)

    val zweiViertel = Bruch(zweiI, vierI)

    init {
        test("Bruch Konstruktor funktioniert")
        {
            halb.zähler shouldBe einsI
            halb.nenner shouldBe zweiI

            drittel.zähler shouldBe einsI
            drittel.nenner shouldBe dreiI

            dreiViertel.zähler shouldBe dreiI
            dreiViertel.nenner shouldBe vierI

            zwei.zähler shouldBe zweiI
            zwei.nenner shouldBe einsI
        }

        test("Bruch Konstruktor kürzt zahlen")
        {
            zweiViertel.zähler shouldBe einsI
            zweiViertel.nenner shouldBe zweiI
        }

        test("Unäres Minus funktioniert")
        {
            val minuesHalb = -halb
            minuesHalb.zähler shouldBe -einsI

            val minuesDrittel = -drittel
            minuesDrittel.zähler shouldBe -einsI

            val minuesDreiViertel = -dreiViertel
            minuesDreiViertel.zähler shouldBe -dreiI

            val minuesZwei = -zwei
            minuesZwei.zähler shouldBe -zweiI

            /*
            Funktioniert nicht solange der Konstruktor nicht kürzt
            val minuesZweiViertel = -zweiViertel
            minuesZweiViertel.numerator shouldBe -einsI
            */
        }

        test("Multiplikation funktioniert")
        {
            val sechstel = halb * drittel
            sechstel.zähler shouldBe einsI
            sechstel.nenner shouldBe zweiI * dreiI
        }

        test("Kürzen nach Multiplikation funktioniert")
        {
            zwei * halb shouldBe eins
            halb * zwei shouldBe eins
            zwei * zweiViertel shouldBe eins
            zweiViertel * zwei shouldBe eins
        }
    }
}