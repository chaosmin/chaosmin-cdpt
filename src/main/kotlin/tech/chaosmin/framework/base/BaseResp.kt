package tech.chaosmin.framework.base

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.util.*

open class BaseResp : Serializable {
    @ApiModelProperty("主键")
    var id: Long? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    var createTime: Date? = null

    @ApiModelProperty("创建人")
    var creator: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    var updateTime: Date? = null

    @ApiModelProperty("更新人")
    var updater: String? = null
}