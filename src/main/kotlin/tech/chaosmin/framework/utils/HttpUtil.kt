package tech.chaosmin.framework.utils

import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import cn.hutool.http.Method
import cn.hutool.poi.excel.ExcelWriter
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import tech.chaosmin.framework.base.RestResult
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object HttpUtil {
    fun getHttpServletRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    }

    private fun setHeaders(request: HttpRequest, headers: Map<String, String>): HttpRequest {
        headers.map { (header, value) ->
            request.header(header, value)
        }
        return request
    }

    /**
     * @param method 请求方式
     * @param url 请求地址
     * @param headers 请求头列表
     * @param body 请求体
     * @param timeout 超时时间(毫秒) 默认60s
     */
    fun doRequest(method: Method, url: String, headers: Map<String, String>, body: String, timeout: Int = 60000): Pair<Int, String> {
        val request = setHeaders(HttpUtil.createRequest(method, url), headers)
        request.timeout(timeout).body(body).execute().use {
            return it.status to it.body()
        }
    }

    fun writeExcel(response: HttpServletResponse, fileName: String, writer: ExcelWriter) {
        response.contentType = "application/vnd.ms-excel;charset=utf-8"
        response.setHeader("Content-Disposition", fileName)
        response.setHeader("filename", fileName)
        response.setHeader("Access-Control-Expose-Headers", "filename")
        response.outputStream.use { out ->
            writer.flush(out)
            out.flush()
        }
    }

    fun write(response: HttpServletResponse, data: RestResult<*>) {
        response.contentType = "application/json; charset=utf-8"
        val json = JsonUtil.encode(data)
        response.writer.print(json)
        response.writer.flush()
        response.writer.close()
    }
}