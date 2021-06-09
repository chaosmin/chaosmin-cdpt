package tech.chaosmin.framework.base

import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.utils.JsonUtil
import java.io.Serializable

abstract class AbstractTemplateOperate<P, R>(private val errorCode: String = ErrorCodeEnum.FAILURE.code) : Serializable, Operate<P, R> {
    private val logger = LoggerFactory.getLogger(AbstractTemplateOperate::class.java)

    protected open fun validation(arg: P, result: RestResult<R>) {}
    protected abstract fun processor(arg: P, result: RestResult<R>): RestResult<R>
    protected open fun result(arg: P, result: RestResult<R>) {}

    protected open fun exceptionDetail(arg: P, result: RestResult<R>, exception: Exception) {
        result.code = errorCode
        if (result.msg?.isBlank() != false) {
            result.msg = "${this.javaClass.simpleName} handler exception"
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
        val stopWatch = StopWatch(this.javaClass.simpleName)
        try {
            // 验证
            stopWatch.start("validation")
            validation(arg, res)
            stopWatch.stop()
            if (!res.success) {
                return res
            }

            // 业务处理
            stopWatch.start("processor")
            res = processor(arg, res)
            stopWatch.stop()
            if (!res.success) {
                return res
            }

            // 执行后的操作
            stopWatch.start("result")
            result(arg, res)
            stopWatch.stop()

        } catch (e: FrameworkException) {
            exceptionDetail(arg, res, e)
            // 系统可预见的Error不再打印堆栈信息
            logger.error("process {} framework exception, init params:{}", this.javaClass.simpleName, JsonUtil.encode(arg))
        } catch (e: Exception) {
            exceptionDetail(arg, res, e)
            logger.error("process {} exception, init params:{}", this.javaClass.simpleName, JsonUtil.encode(arg), e)
        } finally {
            logger.info(stopWatch.toString())
        }

        return res
    }
}