package tech.chaosmin.framework.domain.entity.base

import tech.chaosmin.framework.domain.enums.ModifyTypeEnum

/**
 * @author Romani min
 * @since 2020/12/23 15:50
 */
open class BaseEntity(var id: Long? = null) {
    var modifyType: ModifyTypeEnum? = null

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