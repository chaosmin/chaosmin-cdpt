package tech.chaosmin.framework.domain.response.share.base

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.util.*

open class BaseShareResponseDTO : Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    var createTime: Date? = null

    @ApiModelProperty(value = "创建人")
    var creator: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    var updateTime: Date? = null

    @ApiModelProperty(value = "更新人")
    var updater: String? = null
}