package tech.chaosmin.framework.base

import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/23 15:50
 */
open class BaseEntity(var id: Long? = null) {
    var modifyType: ModifyTypeEnum? = null

    var createTime: Date? = null
    var creator: String? = null
    var updateTime: Date? = null
    var updater: String? = null
    var extraInfo: String? = null
    var deleted: Int? = null

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