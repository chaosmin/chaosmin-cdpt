package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Romani min
 * @since 2021/6/18 11:02
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class PolicyRisk {
    var insuredGroupList: List<InsuredGroup>? = null
    var personInsuredList: List<PersonInsured>? = null
}