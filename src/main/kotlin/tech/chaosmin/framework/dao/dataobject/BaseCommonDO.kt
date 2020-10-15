package tech.chaosmin.framework.dao.dataobject

import com.baomidou.mybatisplus.annotation.*
import java.io.Serializable
import java.util.*

open class BaseCommonDO(
    @TableId(type = IdType.AUTO) var id: Long?,
    @TableLogic val isDeleted: Int
) : Serializable {
    @TableField(fill = FieldFill.INSERT)
    var createTime: Date? = null

    @TableField(fill = FieldFill.INSERT)
    var creator: String? = null

    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateTime: Date? = null

    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updater: String? = null

    var extraInfo: String? = null
}