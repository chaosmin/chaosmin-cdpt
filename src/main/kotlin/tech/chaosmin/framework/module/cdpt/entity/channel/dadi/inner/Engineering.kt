package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

/**
 * 大地保险工程信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:58
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Engineering {
    // 施工单位
    var builder: String? = null

    // 施工单位资质
    var builderLevelCode: String? = null

    // 建筑面积
    var buildingArea: Long? = null

    // 施工止期
    var constructionPeriodEnd: String? = null

    // 施工起期
    var constructionPeriodStart: String? = null

    // 建设单位
    var contractor: String? = null

    // 设计单位
    var designer: String? = null

    // 保险费计收方式
    var premiumModeCode: String? = null

    // 工程造价
    var projectCost: BigDecimal? = null

    // 工程名称
    var projectName: String? = null

    // 工程类别
    var ahProjectType: String? = null

    // 监管单位名称
    var supervisor: String? = null
}