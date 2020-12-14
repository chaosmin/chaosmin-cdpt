package tech.chaosmin.framework.handler.base

import org.slf4j.LoggerFactory
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.utils.JsonUtil
import java.io.Serializable

abstract class AbstractTemplateOperate<P, R>(
    internal val logicName: String? = "",
    private val errorCode: String = ErrorCodeEnum.FAILURE.code
) :
    Serializable, Operate<P, R> {
    private val log = LoggerFactory.getLogger(AbstractTemplateOperate::class.java)

    protected open fun validation(arg: P, result: RestResult<R>) {}
    protected abstract fun processor(arg: P, result: RestResult<R>): RestResult<R>
    protected open fun result(arg: P, result: RestResult<R>) {}

    protected open fun exceptionDetail(arg: P, result: RestResult<R>, exception: Exception) {
        result.code = errorCode
        if (result.msg?.isBlank() != false) {
            result.msg = "$logicName handler exception"
        }
        //如果以FrameworkException抛出的异常以此为主
        if (exception is FrameworkException) {
            if (exception.errCode != null) {
                result.code = exception.errCode!!
            }
            if (exception.message != null) {
                result.msg = exception.message!!
            }
        }
        result.success = false
    }

    override fun operate(arg: P): RestResult<R> {
        var res: RestResult<R> = RestResultExt.successRestResult()
        try {
            // 验证
            validation(arg, res)
            if (!res.success) {
                return res
            }

            // 业务处理
            res = processor(arg, res)
            if (!res.success) {
                return res
            }

            // 执行后的操作
            result(arg, res)
        } catch (e: FrameworkException) {
            exceptionDetail(arg, res, e)
            log.error("process {} framework exception, init params:{}", logicName, JsonUtil.encode(arg), e)
        } catch (e: Exception) {
            exceptionDetail(arg, res, e)
            log.error("process {} exception, init params:{}", logicName, JsonUtil.encode(arg), e)
        }
        return res
    }
}