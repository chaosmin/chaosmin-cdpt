package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.obj

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 大地保险保单退保响应体 <p>
 *
 * @author Romani min
 * @since 2021/6/17 21:40
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class DDRPResp