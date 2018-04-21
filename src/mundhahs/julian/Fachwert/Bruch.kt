package mundhahs.julian.Fachwert

data class Bruch(val numerator: Int, val denominator: Int) {
    //TODO: Konstruktor kürzen lassen

    private fun gcd(int1: Int, int2: Int): Int {
        var a = int1
        var b = int2
        while (true) {
            if (a == 0) {
                return b
            }

            val tempA = a
            a = b
            b = tempA

            a %= b
        }
    }

    private fun reduce(numerator: Int, nenner: Int): Bruch {
        val gcd = gcd(numerator, nenner)
        return Bruch(numerator / gcd, nenner / gcd)
    }

    operator fun unaryMinus() = Bruch(-numerator, denominator)

    operator fun plus(bruch: Bruch): Bruch {
        return when {
            numerator == 0 -> bruch
            bruch.numerator == 0 -> this
            else -> reduce(numerator * bruch.denominator + bruch.numerator * denominator, denominator * bruch.denominator)
        }
    }

    operator fun minus(bruch: Bruch) = (this + -bruch)

    operator fun times(bruch: Bruch): Bruch = reduce(numerator * bruch.numerator, denominator * bruch.denominator)

    operator fun div(bruch: Bruch): Bruch = this * Bruch(bruch.denominator, bruch.numerator)

    override operator fun equals(other: Any?): Boolean {
        if (other is Int) {
            return (denominator == 1) && (numerator == other)
        } else if (other is Bruch) {
            return (denominator == other.denominator) && (numerator == other.numerator)
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = numerator
        result = 31 * result + denominator
        return result
    }

    override fun toString(): String {
        return "($numerator/$denominator)"
    }
}