package tech.chaosmin.framework.utils

import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.exception.support.ExceptionMsgPath
import java.text.MessageFormat
import java.util.*

object ExceptionMessageHelper {
    private var properties = Properties()
    private var isInit = false

    init {
        getProperties()
    }

    private fun getProperties(): Properties {
        if (!isInit) {
            synchronized(properties) {
                if (!isInit) {
                    properties = PropertiesLoader(ExceptionMsgPath.FRM_EXCEPTION_MSG.path).getProperties()
                    isInit = true
                }
            }
        }
        return properties
    }

    fun getExMsg(code: String?): String {
        return if (null == code) ""
        else {
            var msg = ""
            try {
                msg = getProperties().getProperty(code) ?: "错误代码未定义:$code"
                msg
            } catch (var3: Exception) {
                throw FrameworkException(msg, var3)
            }
        }
    }

    fun getExMsg(code: String?, args: Array<Any>): String = MessageFormat.format(getExMsg(code), *args)

    fun getExtMsg(code: String?, vararg args: Any): String = MessageFormat.format(getExMsg(code), *args)
}