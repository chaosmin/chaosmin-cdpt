package tech.chaosmin.framework.utils

import com.google.common.base.CaseFormat
import org.apache.commons.codec.digest.DigestUtils
import java.util.regex.Pattern

object StringUtil {
    fun String.upperCamel(): String {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this)
    }

    fun String.lowerUnderscore(): String {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this)
    }

    fun String.isNumber(): Boolean {
        if (this.isBlank()) return false
        val pattern = Pattern.compile("^[-+]?[.\\d]*$")
        return pattern.matcher(this).matches()
    }

    fun String.number(): String {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(this)
        return m.replaceAll("").trim { it <= ' ' }
    }

    fun String.endWithNumber(): Boolean {
        val pattern = Pattern.compile("[0-9*]")
        return pattern.matcher(last() + "").matches()
    }

    fun String?.getCodeFromZh(length: Int = 5): String {
        val url = if (this.isNullOrBlank()) ""
        else java.net.URLEncoder.encode(this, "GBK").replace("%", "")

        val key = ""
        val chars = arrayOf(
            "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
        )
        val sTempSubString = DigestUtils.md5Hex(key + url).substring(8, 16)
        var lHexLong = (0x3FFFFFFF and sTempSubString.toLong(16).toInt()).toLong()
        var outChars = ""
        for (j in 0..length) {
            val index = (0x0000003D and lHexLong.toInt()).toLong()
            outChars += chars[index.toInt()]
            lHexLong = lHexLong shr length
        }
        return outChars
    }
}