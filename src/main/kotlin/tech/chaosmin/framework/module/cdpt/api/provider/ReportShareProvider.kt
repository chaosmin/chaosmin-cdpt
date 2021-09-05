package tech.chaosmin.framework.module.cdpt.api.provider

import cn.hutool.core.date.DateUtil
import cn.hutool.poi.excel.ExcelUtil
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.TimeTypeEnum
import tech.chaosmin.framework.module.cdpt.api.ReportShareService
import tech.chaosmin.framework.module.cdpt.entity.report.SltCheckReportEntity
import tech.chaosmin.framework.module.cdpt.entity.report.SltComsReportEntity
import tech.chaosmin.framework.module.cdpt.logic.handler.ReportSltCheckHandler
import tech.chaosmin.framework.module.cdpt.logic.handler.ReportSltComsHandler
import tech.chaosmin.framework.utils.NumberUtil
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

/**
 * @author Romani min
 * @since 2021/6/29 21:32
 */
@RestController
open class ReportShareProvider(
    private val reportSltComsHandler: ReportSltComsHandler,
    private val reportSltCheckHandler: ReportSltCheckHandler
) : ReportShareService {
    override fun sltComsReport(userId: Long, startTime: String, endTime: String, timeType: String): RestResult<SltComsReportEntity> {
        val req = SltComsReportEntity().apply {
            this.userId = userId
            this.timeType = if ("EFFECTIVE_TIME" == timeType) TimeTypeEnum.EFFECTIVE_TIME else TimeTypeEnum.ISSUE_TIME
            this.startTime = DateUtil.beginOfDay(DateUtil.parse(startTime, "yyyy-MM-dd"))
            this.endTime = DateUtil.endOfDay((DateUtil.parse(endTime, "yyyy-MM-dd")))
        }
        val result = reportSltComsHandler.operate(req)
        return RestResultExt.mapper<SltComsReportEntity>(result).apply { this.data = result.data }
    }

    override fun sltComsReportDownload(userId: Long, startTime: String, endTime: String, timeType: String, response: HttpServletResponse) {
        val data = sltComsReport(userId, startTime, endTime, timeType).data
        ExcelUtil.getWriter().use { writer ->
            writer.writeHeadRow(listOf("产品名称", "保险公司", "原价", "结算价", "折扣", "协议佣金比", "结算佣金比", "结算佣金"))
            data?.detail?.forEach {
                writer.writeRow(
                    listOf(
                        it.goodsPlanName,
                        it.partnerName,
                        it.originalPrice,
                        it.settlementPrice,
                        it.discount,
                        it.agreementComsRatio,
                        it.settlementComsRatio,
                        it.settlementComs
                    )
                )
            }
            writer.writeRow(listOf("合计", "", data?.totalOriginalPrice, "", "", "", "", data?.totalSettlementPrice))
            writer.autoSizeColumnAll()
            val fileName = URLEncoder.encode("${data?.userName}个人佣金结算表.xls", "utf-8")
            response.characterEncoding = "UTF-8"
            response.contentType = "application/vnd.ms-excel;charset=utf-8"
            response.setHeader("Content-Disposition", fileName)
            response.setHeader("filename", fileName)
            response.setHeader("Access-Control-Expose-Headers", "filename")
            response.outputStream.use { out ->
                writer.flush(out)
                out.flush()
            }
        }
    }

    override fun sltCheckReport(userId: String, startTime: String, endTime: String, timeType: String): RestResult<SltCheckReportEntity> {
        val req = SltCheckReportEntity().apply {
            this.userId = if (userId == "null") null else userId.toLong()
            this.timeType = if ("EFFECTIVE_TIME" == timeType) TimeTypeEnum.EFFECTIVE_TIME else TimeTypeEnum.ISSUE_TIME
            this.startTime = DateUtil.beginOfDay(DateUtil.parse(startTime, "yyyy-MM-dd"))
            this.endTime = DateUtil.endOfDay((DateUtil.parse(endTime, "yyyy-MM-dd")))
        }
        val result = reportSltCheckHandler.operate(req)
        return RestResultExt.mapper<SltCheckReportEntity>(result).apply { this.data = result.data }
    }

    override fun sltCheckReportDownload(userId: String, startTime: String, endTime: String, timeType: String, response: HttpServletResponse) {
        val data = sltCheckReport(userId, startTime, endTime, timeType).data
        val startTimeStr = DateUtil.format(DateUtil.parse(startTime, "yyyy-MM-dd"), "yyyy年MM月dd日")
        val endTimeStr = DateUtil.format(DateUtil.parse(endTime, "yyyy-MM-dd"), "yyyy年MM月dd日")
        val reportType = if ("EFFECTIVE_TIME" == timeType) "生效日" else "出单日"
        ExcelUtil.getWriter().use { writer ->
            writer.merge(14, "结算清单", true)
            writer.merge(14, "$reportType${startTimeStr}-${endTimeStr}", false)
            writer.writeHeadRow(
                listOf(
                    "No.",
                    "订单号",
                    "产品名称",
                    "人数",
                    "标准单价",
                    "标准保费",
                    "实收单价",
                    "实收保费",
                    "生效时间",
                    "终止时间",
                    "出单时间",
                    "团号",
                    "保险公司",
                    "出单人",
                    "目的地"
                )
            )
            data?.detail?.forEachIndexed { i, e ->
                writer.writeRow(
                    listOf(
                        (i + 1),
                        e.orderNo,
                        e.goodsPlanName,
                        e.insuredSize,
                        e.unitPremium,
                        e.totalPremium,
                        e.actualUnitPremium,
                        e.actualPremium,
                        DateUtil.format(e.effectiveTime, "yyyy-MM-dd"),
                        DateUtil.format(e.expiryTime, "yyyy-MM-dd"),
                        DateUtil.format(e.issueTime, "yyyy-MM-dd HH:mm"),
                        e.groupNo,
                        e.partnerName,
                        e.issuer,
                        e.address
                    )
                )
            }
            writer.writeRow(listOf("合计", "", "", data?.totalInsuredSize, "", data?.totalPremium, "", data?.actualPremium))
            writer.merge(14, "金额大写: ${NumberUtil.toChinese(data?.actualPremium?.toString() ?: "0")}", false)
            writer.writeRow(listOf(""))
            writer.merge(2, "保费分类统计", false)
            writer.writeRow(listOf("保险公司", "标准保费", "实收保费"))
            data?.partnerList?.forEach { p ->
                writer.writeRow(listOf(p.partnerName, p.totalPremium, p.actualPremium))
            }
            // 2021-08-05 14:45:50 删除银行信息
//            writer.writeRow(listOf(""))
//            writer.merge(6, "银行账户信息", false)
//            writer.merge(6, "账户名称：中国太平洋财产保险股份有限公司北京分公司", false)
//            writer.merge(6, "开户银行：中国建设银行北京分行兴融支行", false)
//            writer.merge(6, "银行账号：11050167500000000875", false)
            writer.autoSizeColumnAll()
            val fileName = URLEncoder.encode("结算清单-$reportType${startTimeStr}至${endTimeStr}.xls", "utf-8")
            response.contentType = "application/vnd.ms-excel;charset=utf-8"
            response.setHeader("Content-Disposition", fileName)
            response.setHeader("filename", fileName)
            response.setHeader("Access-Control-Expose-Headers", "filename")
            response.outputStream.use { out ->
                writer.flush(out)
                out.flush()
            }
        }
    }
}