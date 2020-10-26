package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.GenderEnum
import java.util.*

class Person(id: Long? = null) : BaseCommonDO(id) {
    var name: String? = null
    var certiNo: String? = null
    var certiType: String? = null
    var birthDay: Date? = null
    var gender: GenderEnum? = null
    var survive: Boolean? = null
    var dateOfDeath: Date? = null
}