package tech.chaosmin.framework.exception

import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.utils.ExceptionMessageHelper
import java.io.PrintStream
import java.io.PrintWriter

class FrameworkException : RuntimeException {
    var errCode: String? = null

    constructor() : super()

    constructor(errCode: String) : super(ExceptionMessageHelper.getExMsg(errCode)) {
        this.errCode = errCode
    }

    constructor(cause: Throwable) : super("无自定义消息异常: ${cause.message}", cause)

    constructor(errCode: String, cause: Throwable) : super(ExceptionMessageHelper.getExMsg(errCode), cause) {
        this.errCode = errCode
    }

    constructor(errCode: String, vararg params: Any) : super(ExceptionMessageHelper.getExtMsg(errCode, *params)) {
        this.errCode = errCode
    }

    constructor(errorType: ErrorCodeEnum, vararg params: Any) : this(errorType.code, params)

    constructor(errCode: String, cause: Throwable, vararg params: Any) : super(ExceptionMessageHelper.getExtMsg(errCode, *params), cause) {
        this.errCode = errCode
    }

    /**
     * @description 获取本地异常描述
     * @version
     * @title
     * @see java.lang.Throwable#toString()
     * @return 本地异常描述
     */
    override fun toString(): String {
        val s = javaClass.name
        val message = localizedMessage
        if (null != cause && cause is FrameworkException) {
            var causeMessage = cause.getLocalizedMessage()
            if (causeMessage == null) {
                causeMessage = ""
            }
            return message ?: ("" + causeMessage)
        }
        return if (message != null) "$s: $message" else s
    }

    /**
     * @description 打印异常堆栈
     * @version
     * @title
     * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
     * @param s PrintStream输入流
     */
    fun printStackTrace(s: PrintStream) {
        synchronized(s) {
            if (null != cause && cause is FrameworkException) {
                s.println(this)
                return
            }
        }
        super.printStackTrace(s)
    }

    /**
     * @description 打印异常堆栈
     * @version
     * @title
     * @see java.lang.Throwable.printStackTrace
     * @param s PrintWriter
     */
    fun printStackTrace(s: PrintWriter) {
        synchronized(s) {
            if (null != cause && cause is FrameworkException) {
                s.println(this)
                return
            }
        }
        super.printStackTrace(s)
    }

    /**
     * @description 获取异常信息
     * @version
     * @title
     * @see java.lang.Throwable.getMessage
     * @return 异常信息
     */
    fun getMsg(): String? {
        var causeMessage: String? = ""
        if (null != cause) {
            causeMessage = cause.localizedMessage
        }
        var msg = super.message
        if (null == msg) {
            msg = causeMessage
        } else {
            if (null != causeMessage && "" != causeMessage.trim { it <= ' ' }) {
                msg = "$msg-$causeMessage"
            }
        }
        return msg
    }
}