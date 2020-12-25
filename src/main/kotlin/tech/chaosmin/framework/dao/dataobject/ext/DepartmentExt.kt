package tech.chaosmin.framework.dao.dataobject.ext

import tech.chaosmin.framework.dao.dataobject.Department

/**
 * @author Romani min
 * @since 2020/12/25 16:52
 */
class DepartmentExt : Department() {
    // 机构人数
    var numberOfUser: Int? = null
}