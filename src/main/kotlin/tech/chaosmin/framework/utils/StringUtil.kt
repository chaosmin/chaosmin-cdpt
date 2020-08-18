package tech.chaosmin.framework.utils

import com.google.common.base.CaseFormat
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
}