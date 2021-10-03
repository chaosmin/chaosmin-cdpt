package tech.chaosmin.framework.utils

/**
 * @author Romani min
 * @since 2021/7/6 17:17
 */
object NumberUtil {
    // 大写数字
    private val NUMBERS = arrayOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")

    // 整数部分的单位
    private val UNIT = arrayOf("元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟")

    // 小数部分的单位
    private val DUNIT = arrayOf("角", "分", "厘")

    // 转成中文的大写金额
    fun toChinese(string: String): String {
        // 判断输入的金额字符串是否符合要求
        var str = string
        if (str.isBlank() || !str.matches(Regex("(-)?[\\d]*(.)?[\\d]*"))) {
            println("抱歉，请输入数字！")
            return str
        }
        if ("0" == str || "0.00" == str || "0.0" == str) {
            return "零元"
        }

        //判断是否存在负号"-"
        var flag = false
        if (str.startsWith("-")) {
            flag = true
            str = str.replace("-".toRegex(), "")
        }
        str = str.replace(",".toRegex(), "") // 去掉","
        val integerStr: String // 整数部分数字
        val decimalStr: String // 小数部分数字


        //初始化：分离整数部分和小数部分
        when {
            str.indexOf(".") > 0 -> {
                integerStr = str.substring(0, str.indexOf("."))
                decimalStr = str.substring(str.indexOf(".") + 1)
            }
            str.indexOf(".") == 0 -> {
                integerStr = ""
                decimalStr = str.substring(1)
            }
            else -> {
                integerStr = str
                decimalStr = ""
            }
        }

        //beyond超出计算能力，直接返回
        if (integerStr.length > UNIT.size) {
            println("$str：超出计算能力")
            return str
        }
        val integers = toIntArray(integerStr)
        //判断整数部分是否存在输入012的情况
        if (integers.size > 1 && integers[0] == 0) {
            println("抱歉，请输入数字！")
            if (flag) {
                str = "-$str"
            }
            return str
        }
        val isWan: Boolean = isWan5(integerStr) //设置万单位
        val decimals = toIntArray(decimalStr) //小数部分数字
        val result: String = getChineseInteger(integers, isWan) + getChineseDecimal(decimals) //返回最终的大写金额
        return if (flag) {
            "负$result"
        } else {
            result
        }
    }

    private fun toIntArray(number: String): IntArray {
        val array = IntArray(number.length)
        for (i in number.indices) {
            array[i] = number.substring(i, i + 1).toInt()
        }
        return array
    }

    private fun getChineseInteger(integers: IntArray, isWan: Boolean): String {
        val chineseInteger = StringBuffer("")
        val length = integers.size
        if (length == 1 && integers[0] == 0) {
            return ""
        }
        for (i in 0 until length) {
            var key = ""
            if (integers[i] == 0) {
                if (length - i == 13) //万（亿）
                    key = UNIT[4] else if (length - i == 9) { //亿
                    key = UNIT[8]
                } else if (length - i == 5 && isWan) { //万
                    key = UNIT[4]
                } else if (length - i == 1) { //元
                    key = UNIT[0]
                }
                if (length - i > 1 && integers[i + 1] != 0) {
                    key += NUMBERS[0]
                }
            }
            chineseInteger.append(if (integers[i] == 0) key else NUMBERS[integers[i]] + UNIT[length - i - 1])
        }
        return chineseInteger.toString()
    }

    private fun getChineseDecimal(decimals: IntArray): String {
        val chineseDecimal = StringBuffer("")
        for (i in decimals.indices) {
            if (i == 3) break
            chineseDecimal.append(if (decimals[i] == 0) "" else NUMBERS[decimals[i]] + DUNIT[i])
        }
        return chineseDecimal.toString()
    }

    private fun isWan5(integerStr: String): Boolean {
        val length = integerStr.length
        return if (length > 4) {
            val subInteger = if (length > 8) {
                integerStr.substring(length - 8, length - 4)
            } else {
                integerStr.substring(0, length - 4)
            }
            subInteger.toInt() > 0
        } else false
    }
}