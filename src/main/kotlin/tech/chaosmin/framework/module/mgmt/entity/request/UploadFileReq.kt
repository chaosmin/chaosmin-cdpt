package tech.chaosmin.framework.module.mgmt.entity.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.web.multipart.MultipartFile
import tech.chaosmin.framework.base.BaseReq

/**
 * @author Romani min
 * @since 2020/12/26 10:23
 */
@ApiModel("文件上传接口请求参数")
class UploadFileReq : BaseReq() {
    @JsonIgnore
    @ApiModelProperty("文件")
    var file: MultipartFile? = null

    @ApiModelProperty("文件名")
    var fileName: String? = null
}