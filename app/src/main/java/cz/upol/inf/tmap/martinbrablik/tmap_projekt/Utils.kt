package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.util.Log
import java.lang.NumberFormatException
import java.util.*
import kotlin.math.pow

class Utils {
    companion object {

        private fun convertChar(c: Char, asciiOffset: Int): Int = c.code - asciiOffset

        private fun anyToDecimal(n: String, base: Int): Long {
            val length = n.length
            val charArray = StringBuilder(n).reverse().toString().toCharArray()
            var result: Long = 0

            for ((i, c) in charArray.withIndex()) {
                result += when {
                    c.isDigit() -> convertChar(c, 48) * base.toDouble().pow(i).toLong()
                    c.isUpperCase() -> convertChar(c, 55) * base.toDouble().pow(i)
                        .toLong()
                    c.isLowerCase() -> convertChar(c, 87) * base.toDouble().pow(i)
                        .toLong()
                    else -> throw NumberFormatException()
                }
            }
            return result
        }

        private fun decimalToAny(n: Long, base: Int): String = n.toString(base).toUpperCase(Locale.getDefault())


        fun anyToAny(n1: String, base1: Int, base2: Int): String =
            decimalToAny(anyToDecimal(n1, base1), base2)

        fun anyToAscii(n: String, base: Int): Char = anyToDecimal(n, base).toInt().toChar()

        fun asciiToAny(s: String, base: Int): String {
            val sb = StringBuilder()
            val charArray = s.toCharArray()
            for(c in charArray) {
                sb.append(c.code.toString(base).uppercase(Locale.getDefault())).append("\n\n")
            }
            return sb.toString()
        }
    }
}