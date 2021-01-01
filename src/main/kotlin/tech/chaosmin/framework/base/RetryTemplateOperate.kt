package tech.chaosmin.framework.base

import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.definition.SystemConst
import tech.chaosmin.framework.utils.JsonUtil
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.math.pow

abstract class RetryTemplateOperate<P, R>(
    logicName: String? = "",
    errorCode: String = ErrorCodeEnum.FAILURE.code
) : AbstractTemplateOperate<P, R>(logicName, errorCode) {
    private val log = LoggerFactory.getLogger(RetryTemplateOperate::class.java)

    private var retryTimes = SystemConst.DEFAULT_RETRY_TIME
    private var waitingTime = SystemConst.DEFAULT_SLEEP_TIME
    private var printWarnings = SystemConst.DEFAULT_PRINT_WARNINGS
    private var progressiveWait = SystemConst.DEFAULT_PROGRESSIVE_WAIT
    private var progressiveStep = SystemConst.DEFAULT_PROGRESSIVE_STEP

    override fun operate(arg: P): RestResult<R> {
        var res: RestResult<R> = RestResultExt.successRestResult()
        val startTime = System.currentTimeMillis()
        for (i in 1..retryTimes) {
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
                log.info(
                    "process {} success, has retried {} times, total cost time is {}ms",
                    logicName,
                    i - 1,
                    System.currentTimeMillis() - startTime
                )
                result(arg, res)
                return res
            } catch (e: Exception) {
                val slp =
                    if (progressiveWait) (waitingTime * progressiveStep.pow(i.toDouble())).toLong() else waitingTime
                if (i != retryTimes) {
                    if (printWarnings) {
                        log.warn(
                            "process {} exception, will retry the {}th time, the waiting time is {}ms, init params:{}",
                            logicName,
                            i,
                            slp,
                            JsonUtil.encode(arg),
                            e
                        )
                    }
                    Thread.sleep(slp)
                } else {
                    log.error(
                        "process {} exception, has retried {} times, total cost time is {}ms, init params:{}",
                        logicName,
                        i,
                        System.currentTimeMillis() - startTime,
                        JsonUtil.encode(arg),
                        e
                    )
                    exceptionDetail(arg, res, e)
                }
            }
        }
        return RestResultExt.failureRestResult()
    }

    fun setRetryTimes(retryTimes: Int): RetryTemplateOperate<P, R> {
        this.retryTimes = retryTimes
        return this
    }

    fun setSleepTime(sleepTime: Long): RetryTemplateOperate<P, R> {
        this.waitingTime = sleepTime
        return this
    }

    fun printWarnings(printWarnings: Boolean): RetryTemplateOperate<P, R> {
        this.printWarnings = printWarnings
        return this
    }

    fun progressiveWait(progressiveWait: Boolean): RetryTemplateOperate<P, R> {
        this.progressiveWait = progressiveWait
        return this
    }

    fun submit(arg: P, executorService: ThreadPoolTaskExecutor?): RestResult<R> {
        requireNotNull(executorService) { "please choose executorService!" }
        return executorService.submit(Callable<RestResult<R>> { this.operate(arg) }).get()
    }

    fun submit(arg: P, executorService: ThreadPoolTaskExecutor?, timeoutTime: Int): RestResult<R> {
        requireNotNull(executorService) { "please choose executorService!" }
        return executorService.submit(Callable<RestResult<R>> { this.operate(arg) })
            .get(timeoutTime.toLong(), TimeUnit.SECONDS)
    }
}