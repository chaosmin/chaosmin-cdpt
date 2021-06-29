package tech.chaosmin.framework.module.mgmt.service

import cn.hutool.poi.excel.ExcelUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.base.enums.ResDataTypeEnum
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData
import tech.chaosmin.framework.module.mgmt.service.inner.ResDataService
import tech.chaosmin.framework.utils.EnumClient
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/28 18:44
 */
class ResDataServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var resDataService: ResDataService

    @Test
    fun loadResData() {
        val channelCode = "dadi"
        ClassPathResource("code-value/${channelCode}.xlsx").inputStream.use { stream ->
            ExcelUtil.getReader(stream).sheets.forEach { sheet ->
                val sheetName = sheet.sheetName
                val resDataType = EnumClient.getEnum(ResDataTypeEnum::class.java, sheetName)
                if (resDataType == null) println("暂不支持该码值类型: $sheetName")
                else {
                    val ew = Wrappers.query<ResData>().eq("item_key", resDataType.name).eq("extend1", channelCode)
                    val exList = resDataService.list(ew).mapNotNull { it.itemCode }
                    val list = sheet.mapNotNull { it.getCell(0).toString() to it.getCell(1).toString() }
                    list.filter { !exList.contains(it.first) }.forEach { (itemCode, itemName) ->
                        resDataService.save(ResData().apply {
                            this.itemKey = resDataType.name
                            this.itemCode = itemCode
                            this.itemName = itemName
                            this.extend1 = channelCode
                            this.extend3 = sheetName
                        })
                    }
                }
            }
        }
    }
}