package tech.chaosmin.framework.handler

import cn.hutool.poi.excel.ExcelReader
import cn.hutool.poi.excel.ExcelUtil
import cn.hutool.poi.excel.cell.CellUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.apache.poi.ss.usermodel.Sheet
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.dataobject.Partner
import tech.chaosmin.framework.dao.dataobject.ProductCategory
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.const.SystemConst.ANONYMOUS
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.domain.request.UploadFileReq
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.PartnerService
import tech.chaosmin.framework.service.ProductCategoryService
import java.util.*
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
    lateinit var categoryService: ProductCategoryService

    @Resource
    lateinit var modifyProductHandler: ModifyProductHandler

    @Resource
    lateinit var modifyProductPlanHandler: ModifyProductPlanHandler

    override fun validation(arg: UploadFileReq, result: RestResult<ProductEntity>) {
        if (arg.file == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "file")
        }
        if (arg.fileName.isNullOrBlank()) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "fileName")
        }
    }

    @Transactional(rollbackFor = [FrameworkException::class, Exception::class])
    override fun processor(arg: UploadFileReq, result: RestResult<ProductEntity>): RestResult<ProductEntity> {
        val traceId = UUID.randomUUID().toString()
        val fileNameSplit = arg.fileName!!.substringBeforeLast('.').split('_')
        val partnerName = if (fileNameSplit.size == 2) fileNameSplit[0] else ANONYMOUS
        val partner = partnerService.list(QueryWrapper<Partner>().like("name", partnerName)).firstOrNull()
            ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "保险公司[$partnerName]")
        val categoryNames = if (fileNameSplit.size == 2) fileNameSplit[1].split('|') else arrayListOf(ANONYMOUS)
        val categories = categoryService.list(QueryWrapper<ProductCategory>().`in`("category_name", categoryNames))
        logger.info("[pd:$traceId] >>> 处理保司[$partnerName]名下产品大类[$categoryNames]")
        val product = ProductEntity().apply {
            this.modifyType = ModifyTypeEnum.SAVE
            this.partnerId = partner.id
            this.partnerName = partner.name
            this.categoryIds = categories.mapNotNull { it.id }
            this.productCode = traceId
        }
        arg.file!!.inputStream.use { `in` ->
            ExcelUtil.getReader(`in`).use { handle(it, product) }
        }

        val (_, _, data, _, success) = modifyProductHandler.operate(product)
        if (success && data != null) {
            product.plans.forEach { plan ->
                plan.productId = data.id
                plan.primaryCoverage = plan.liabilities.firstOrNull()?.amount
                val (_, _, _, _, sus) = modifyProductPlanHandler.operate(plan)
                if (!sus) throw FrameworkException(ErrorCodeEnum.FAILURE.code)
            }
        } else {
            throw FrameworkException(ErrorCodeEnum.FAILURE.code)
        }
        return result.success(product)
    }

    private fun handle(reader: ExcelReader, product: ProductEntity) {
        try {
            reader.sheets.forEach { sheet ->
                when (sheet.sheetName) {
                    "费率表" -> handleRateTable(sheet, product)
                    "特别约定" -> handleSpecialAgreement(sheet, product)
                    "投保须知" -> handleNotice(sheet, product)
                    else -> handlePlans(sheet, product)
                }
            }
        } catch (e: Exception) {
            throw FrameworkException(ErrorCodeEnum.FAILURE.code, e.message ?: "")
        }
    }

    private fun handleRateTable(sheet: Sheet, product: ProductEntity) {
        logger.info("[pd:${product.productCode}] >>> 处理费率表")
        val r = sheet.first()
        val plans = (2 until r.count()).map { it to r.getCell(it).stringCellValue }.toMap()
        (1..sheet.lastRowNum).forEach { row ->
            val startDay = CellUtil.getCellValue(sheet.getRow(row).getCell(0), true).toString()
            val endDay = CellUtil.getCellValue(sheet.getRow(row).getCell(1), true).toString()
            (2 until r.count()).forEach { col ->
                val amount = CellUtil.getCellValue(sheet.getRow(row).getCell(col), true).toString()
                product.getOrCreatePlan(plans[col]).addRateTable(startDay, endDay, amount)
            }
        }
    }

    private fun handleSpecialAgreement(sheet: Sheet, product: ProductEntity) {
        logger.info("[pd:${product.productCode}] >>> 处理特别约定")
        product.specialAgreement = sheet.mapNotNull { it.first().stringCellValue }
    }

    private fun handleNotice(sheet: Sheet, product: ProductEntity) {
        logger.info("[pd:${product.productCode}] >>> 处理投保须知")
        product.notice = sheet.mapNotNull { it.first().stringCellValue }
    }

    private fun handlePlans(sheet: Sheet, product: ProductEntity) {
        logger.info("[pd:${product.productCode}] >>> 处理产品计划")
        product.productName = sheet.sheetName
        val r = sheet.first()
        val plans = (2 until r.count()).map { it to r.getCell(it).stringCellValue }.toMap()
        (1..sheet.lastRowNum).forEach { row ->
            val category = CellUtil.getCellValue(sheet.getRow(row).getCell(0), true).toString()
            val liability = CellUtil.getCellValue(sheet.getRow(row).getCell(1), true).toString()
            (2 until r.count()).forEach { col ->
                val amount = CellUtil.getCellValue(sheet.getRow(row).getCell(col), true).toString()
                product.getOrCreatePlan(plans[col]).addLiability(category, liability, amount)
            }
        }
    }
}