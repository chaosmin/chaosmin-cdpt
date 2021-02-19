package tech.chaosmin.framework.module.mgmt.domain.dataobject.ext

import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Role

/**
 * @author Romani min
 * @since 2021/2/19 11:08
 */
class RoleExt : Role() {
    var authorities: List<Authority>? = null
}