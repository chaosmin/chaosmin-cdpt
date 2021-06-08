package tech.chaosmin.framework.base

import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import java.util.*

/**
 * 系统操作中间态基类 <br/>
 * <br/>
 * @author Romani min
 * @since 2020/12/23 15:50
 */
open class BaseEntity(var id: Long? = null) {
    // 操作类型 SAVE,UPDATE,REMOVE
    var modifyType: ModifyTypeEnum? = null

    // 创建时间
    var createTime: Date? = null

    // 创建人
    var creator: String? = null

    // 更新时间
    var updateTime: Date? = null

    // 更新人
    var updater: String? = null

    // 扩展信息
    var extraInfo: String? = null

    // 是否逻辑删除
    var deleted: Int? = null

    // TODO 如何优化以下三个方法, 简化代码写法
    fun save() {
        this.modifyType = ModifyTypeEnum.SAVE
    }

    fun update(id: Long) {
        this.modifyType = ModifyTypeEnum.UPDATE
        this.id = id
    }

    fun remove() {
        this.modifyType = ModifyTypeEnum.REMOVE
    }
}