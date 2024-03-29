package tech.chaosmin.framework.base

import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.utils.JsonUtil
import java.util.*

/**
 * 系统操作中间态基类 <p>
 * <p>
 * @author Romani min
 * @since 2020/12/23 15:50
 */
@Suppress("UNCHECKED_CAST")
open class BaseEntity<out T : BaseEntity<T>>(var id: Long? = null) {
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

    override fun toString(): String {
        return JsonUtil.encode(this)
    }

    // TODO 如何优化以下三个方法, 简化代码写法
    fun save(): T {
        this.modifyType = ModifyTypeEnum.SAVE
        return this as T
    }

    fun update(id: Long? = this.id): T {
        this.modifyType = ModifyTypeEnum.UPDATE
        this.id = id
        return this as T
    }

    fun remove(id: Long? = this.id): T {
        this.modifyType = ModifyTypeEnum.REMOVE
        this.id = id
        return this as T
    }
}