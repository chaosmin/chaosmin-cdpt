package tech.chaosmin.framework.domain.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.web.multipart.MultipartFile
import tech.chaosmin.framework.domain.request.base.BaseReq

/**
 * @author Romani min
 * @since 2020/12/26 10:23
 */
@ApiModel("文件上传接口请求参数")
class UploadFileReq : BaseReq() {
    @JsonIgnore
    @ApiModelProperty(value = "文件")
    var file: MultipartFile? = null

    @ApiModelProperty(value = "文件名")
    var fileName: String? = null
}