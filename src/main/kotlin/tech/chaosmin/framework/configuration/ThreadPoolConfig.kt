package tech.chaosmin.framework.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.util.concurrent.ListenableFuture
import tech.chaosmin.framework.definition.SystemConst
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import javax.validation.constraints.NotNull


/**
 * @author Romani min
 * @since 2021/7/8 16:42
 */
@EnableAsync
@Configuration
open class ThreadPoolConfig : AsyncConfigurer {
    @Primary
    @Bean("taskExecutor")
    open fun taskExecutor(): Executor {
        val taskExecutor = VisibleThreadPoolTaskExecutor()
        //设置线程池参数信息
        taskExecutor.corePoolSize = 10
        taskExecutor.maxPoolSize = 50
        taskExecutor.keepAliveSeconds = 60
        taskExecutor.threadNamePrefix = "${SystemConst.ISS}-executor-"
        taskExecutor.setQueueCapacity(500)
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true)
        taskExecutor.setAwaitTerminationSeconds(60)
        //修改拒绝策略为使用当前线程执行
        taskExecutor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        //初始化线程池
        taskExecutor.initialize()
        return taskExecutor
    }

    class VisibleThreadPoolTaskExecutor : ThreadPoolTaskExecutor() {
        private fun showThreadPoolInfo(prefix: String) {
            val threadPoolExecutor = threadPoolExecutor
            logger.debug(
                "线程名:$threadNamePrefix $prefix," +
                        "总计:${threadPoolExecutor.taskCount}," +
                        "完成:${threadPoolExecutor.completedTaskCount}," +
                        "激活:${threadPoolExecutor.activeCount}," +
                        "队列大小:${threadPoolExecutor.queue.size}"
            )
        }

        override fun execute(@NotNull task: Runnable) {
            showThreadPoolInfo("do execute")
            super.execute(task)
        }

        override fun execute(@NotNull task: Runnable, startTimeout: Long) {
            showThreadPoolInfo("do execute")
            super.execute(task, startTimeout)
        }

        @NotNull
        override fun submit(@NotNull task: Runnable): Future<*> {
            showThreadPoolInfo("do submit")
            return super.submit(task)
        }

        @NotNull
        override fun <T> submit(@NotNull task: Callable<T>): Future<T> {
            showThreadPoolInfo("do submit")
            return super.submit(task)
        }

        @NotNull
        override fun submitListenable(task: Runnable): ListenableFuture<*> {
            showThreadPoolInfo("do submitListenable")
            return super.submitListenable(task)
        }

        @NotNull
        override fun <T> submitListenable(task: Callable<T>): ListenableFuture<T> {
            showThreadPoolInfo("do submitListenable")
            return super.submitListenable(task)
        }
    }
}