package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.BasicStatusEnum

/**
 * 产品责任包
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class Package(id: Long? = null) : BaseCommonDO(id) {
    // 代码
    var code: String? = null

    // 名称
    var name: String? = null

    // 状态
    var status: BasicStatusEnum? = null
}