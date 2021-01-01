package tech.chaosmin.framework.base

import com.baomidou.mybatisplus.annotation.*
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*

open class BaseDO(
    @TableId(value = "id", type = IdType.AUTO) var id: Long?,
    @TableLogic @TableField(value = "is_deleted") var deleted: Int = 0
) : Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    var createTime: Date? = null

    @TableField(fill = FieldFill.INSERT)
    var creator: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateTime: Date? = null

    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updater: String? = null

    var extraInfo: String? = null
}