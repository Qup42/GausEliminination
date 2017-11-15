package mundhahs.julian

data class Bruch(val zähler: Int, val nenner: Int) {
    //TODO: Konstruktor kürzen lassen

    operator fun times(bruch: Bruch): Bruch {
        return kürzen(zähler * bruch.zähler, nenner * bruch.nenner)
    }

    operator fun div(bruch: Bruch): Bruch {
        return this*Bruch(bruch.nenner, bruch.zähler)
    }

    operator fun plus(bruch: Bruch): Bruch {
        return kürzen(zähler*bruch.nenner+bruch.zähler*nenner, nenner*bruch.nenner)
    }

    override fun equals(other: Any?): Boolean {
        if(other is Int)
        {
            return (nenner == 1) && (zähler == other)
        }
        else if(other is Bruch)
        {
            return (nenner == other.nenner) && (zähler == other.zähler)
        }
        return super.equals(other)
    }

    operator fun unaryMinus() = Bruch(-zähler, nenner)

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

    private fun kürzen(zähler: Int, nenner: Int): Bruch {
        val gcd = gcd(zähler, nenner)
        return Bruch(zähler / gcd, nenner / gcd)
    }

    override fun hashCode(): Int {
        var result = zähler
        result = 31 * result + nenner
        return result
    }
}