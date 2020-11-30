package tech.chaosmin.framework.utils

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import tech.chaosmin.framework.domain.RestResult
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object HttpUtil {
    fun getHttpServletRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    }

    fun write(response: HttpServletResponse, data: RestResult<*>) {
        response.contentType = "application/json; charset=utf-8"
        val json = JsonUtil.encode(data)
        response.writer.print(json)
        response.writer.flush()
        response.writer.close()
    }
}