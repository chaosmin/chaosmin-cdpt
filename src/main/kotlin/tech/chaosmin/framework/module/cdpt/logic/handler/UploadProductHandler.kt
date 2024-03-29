/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.handler

import cn.hutool.poi.excel.ExcelReader
import cn.hutool.poi.excel.ExcelUtil
import cn.hutool.poi.excel.cell.CellUtil
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_COMMISSION_RATIO
import tech.chaosmin.framework.definition.SystemConst.INSURED_NOTICE
import tech.chaosmin.framework.definition.SystemConst.LIABILITY_ZH
import tech.chaosmin.framework.definition.SystemConst.PLAN_ZH
import tech.chaosmin.framework.definition.SystemConst.PRODUCT_ZH
import tech.chaosmin.framework.definition.SystemConst.RATE_TABLE_ZH
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.domain.service.PartnerService
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.mgmt.entity.request.UploadFileReq
import tech.chaosmin.framework.utils.StringUtil.isNumber
import java.math.BigDecimal
import java.text.DecimalFormat
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2020/12/26 10:26
 */
@Component
open class UploadProductHandler : AbstractTemplateOperate<UploadFileReq, ProductEntity>() {
    private val logger = LoggerFactory.getLogger(UploadProductHandler::class.java)

    @Resource
    lateinit var partnerService: PartnerService

    @Resource
    lateinit var productModifyHandler: ProductModifyHandler

    @Resource
    lateinit var productPlanModifyHandler: ProductPlanModifyHandler

    override fun validation(arg: UploadFileReq, res: RestResult<ProductEntity>) {
        if (arg.file == null) {
            paramException("文件")
        }
        if (arg.fileName.isNullOrBlank()) {
            paramException("文件名")
        }
    }

    @Transactional(rollbackFor = [FrameworkException::class, Exception::class])
    override fun processor(arg: UploadFileReq, res: RestResult<ProductEntity>): RestResult<ProductEntity> {
        val product = ProductEntity().apply {
            this.status = StatusEnum.ENABLED
            this.modifyType = ModifyTypeEnum.SAVE
        }
        arg.file!!.inputStream.use { `in` ->
            ExcelUtil.getReader(`in`).use { handle(it, product) }
        }

        val (_, _, data, _, success) = productModifyHandler.operate(product)
        if (success && data != null) {
            product.id = data.id
            product.plans.forEach { plan ->
                plan.productId = product.id
                if (plan.comsRatio == null) {
                    plan.comsRatio = (product.productRatio ?: DEFAULT_COMMISSION_RATIO).toDouble()
                }
                val (_, _, pl, _, sus) = productPlanModifyHandler.operate(plan)
                if (!sus) throw FrameworkException(ErrorCodeEnum.FAILURE.code)
                else plan.id = pl?.id
            }
        } else {
            throw FrameworkException(ErrorCodeEnum.FAILURE.code)
        }
        return res.success(product)
    }

    private fun handle(reader: ExcelReader, product: ProductEntity) {
        reader.sheets.forEach { sheet ->
            when (sheet.sheetName) {
                PRODUCT_ZH -> handleProduct(sheet, product)
                PLAN_ZH -> handlePlan(sheet, product)
                LIABILITY_ZH -> handleLiability(sheet, product)
                RATE_TABLE_ZH -> handleRateTable(sheet, product)
                INSURED_NOTICE -> handleInsuredNotice(sheet, product)
                else -> handleText(sheet, product)
            }
        }
    }

    private fun handleProduct(sheet: Sheet, product: ProductEntity) {
        if (sheet.lastRowNum <= 0) {
            throw FrameworkException(ErrorCodeEnum.PARAM_LACK_DATA.code)
        }
        // 移除第一行Header
        sheet.shiftRows(1, sheet.lastRowNum, -1)
        val row = sheet.first()
        val partnerCode = getRowValue(row, 0) ?: paramException("保司Code")
        val partner = partnerService.listEqPartnerCode(partnerCode)
        if (partner.isEmpty()) {
            throw FrameworkException(ErrorCodeEnum.RESOURCE_NOT_EXIST.code, "保司[$partnerCode]")
        }
        partner.first().run {
            product.partnerId = this.id
            product.partnerCode = this.partnerCode
            product.partnerName = this.partnerName
        }
        product.categoryName = getRowValue(row, 1) ?: paramException("产品一级大类")
        product.categorySubName = getRowValue(row, 2) ?: paramException("产品二级大类")
        product.productCode = getRowValue(row, 3) ?: paramException("产品Code")
        product.productName = getRowValue(row, 4) ?: paramException("产品名称")
        product.waitingDays = getRowValue(row, 5) ?: "1"
        product.productDesc = getRowValue(row, 6)
        product.productRatio = getRowValue(row, 7)
        product.clauseUrl = getRowValue(row, 8)
    }

    private fun handlePlan(sheet: Sheet, product: ProductEntity) {
        if (sheet.lastRowNum <= 0) {
            throw FrameworkException(ErrorCodeEnum.PARAM_LACK_DATA.code)
        }
        // 移除第一行Header
        sheet.shiftRows(1, sheet.lastRowNum, -1)
        sheet.rowIterator().forEach { row ->
            val planCode = getRowValue(row, 1) ?: paramException("计划Code")
            val planName = getRowValue(row, 2) ?: paramException("计划名称")
            val planRatio = getRowValue(row, 3)
            product.addPlan(planCode, planName, planRatio)
        }
    }

    private fun handleLiability(sheet: Sheet, product: ProductEntity) {
        val plans = getHeaderAndRemoveRow(sheet, 1, 2)
        val format = DecimalFormat(",###,##0")
        sheet.rowIterator().forEach { row ->
            val category = getRowValue(row, 0) ?: ""
            val liability = getRowValue(row, 1) ?: paramException("[${row.rowNum}]保险责任")
            (2..row.lastCellNum).forEach {
                // TODO 嵌套优化
                val amount = getRowValue(row, it)
                if (!amount.isNullOrBlank()) {
                    product.getPlan(plans[it])?.run {
                        // 千分位格式化
                        this.addLiability(category, liability, if (amount.isNumber()) format.format(BigDecimal(amount)) else amount)
                    }
                }
            }
        }
    }

    private fun handleRateTable(sheet: Sheet, product: ProductEntity) {
        val plans = getHeaderAndRemoveRow(sheet, 1, 2)
        sheet.rowIterator().forEach { row ->
            val startDay = getRowValue(row, 0) ?: paramException("[${row.rowNum}]天数起")
            val endDay = getRowValue(row, 1) ?: paramException("[${row.rowNum}]天数止")
            val remark = getRowValue(row, 2) ?: ""
            (3..row.lastCellNum).forEach {
                val amount = getRowValue(row, it)
                if (!amount.isNullOrBlank()) {
                    product.getPlan(plans[it])?.run {
                        this.addRateTable(startDay.toInt(), endDay.toInt(), amount, remark)
                    }
                }
            }
        }
    }

    private fun handleText(sheet: Sheet, product: ProductEntity) {
        product.externalText = "<span style=\"color: #ff0000;font-size:16px;\"><strong>特别约定</strong></span><br><br>"
        val exText = sheet.mapNotNull { "<span>${getRowValue(it, 0)}</span>" }.joinToString("<br>")
            .replace("\n", "<br>")
        product.externalText += exText
    }

    private fun handleInsuredNotice(sheet: Sheet, product: ProductEntity) {
        product.insuranceNotice = ""
        val exText = sheet.mapNotNull { "<span>${getRowValue(it, 0)}</span>" }.joinToString("<br>")
            .replace("\n", "<br>")
        product.insuranceNotice += exText
    }

    private fun getHeaderAndRemoveRow(sheet: Sheet, header: Int, remove: Int): Map<Int, String> {
        if (sheet.lastRowNum <= header) {
            throw FrameworkException(ErrorCodeEnum.PARAM_LACK_DATA.code)
        }
        val r = sheet.getRow(header)
        // 获取到列对应的planCode
        val plans = (2 until r.count()).map { it to r.getCell(it).stringCellValue }.toMap()
        // 移除头两行
        sheet.shiftRows(remove, sheet.lastRowNum, -remove)
        return plans
    }

    private fun getCellValue(sheet: Sheet, row: Int, col: Int): String? {
        if (row < 0 || row > sheet.lastRowNum) {
            throw FrameworkException(ErrorCodeEnum.PARAM_OUT_OF_RANGE.code)
        }
        return getRowValue(sheet.getRow(row), col)
    }

    private fun getRowValue(row: Row, col: Int): String? {
        if (col < 0 || col > row.lastCellNum) {
            throw FrameworkException(ErrorCodeEnum.PARAM_OUT_OF_RANGE.code)
        }
        return CellUtil.getCellValue(row.getCell(col))?.toString()
    }

    private fun paramException(paramName: String): String {
        throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, paramName)
    }
}