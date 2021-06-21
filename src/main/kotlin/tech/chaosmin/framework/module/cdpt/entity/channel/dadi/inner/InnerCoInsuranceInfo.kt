package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 大地保险联保信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:42
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class InnerCoInsuranceInfo