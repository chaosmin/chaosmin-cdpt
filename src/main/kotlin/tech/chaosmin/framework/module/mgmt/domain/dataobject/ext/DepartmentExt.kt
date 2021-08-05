package tech.chaosmin.framework.module.mgmt.domain.dataobject.ext

import tech.chaosmin.framework.module.mgmt.domain.dataobject.Department
import tech.chaosmin.framework.module.mgmt.domain.dataobject.LetterHead

/**
 * @author Romani min
 * @since 2020/12/25 16:52
 */
class DepartmentExt : Department() {
    // 机构抬头
    var letterHead: List<LetterHead>? = null

    // 机构人数
    var numberOfUser: Int? = null
}