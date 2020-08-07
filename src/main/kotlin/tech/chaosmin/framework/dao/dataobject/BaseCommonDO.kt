package tech.chaosmin.framework.dao.dataobject

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import java.io.Serializable
import java.util.*

open class BaseCommonDO(
    @TableId(type = IdType.AUTO) var id: Long?,
    @TableLogic val isDeleted: Int
) : Serializable {
    var createTime: Date? = null
    var creator: String? = null
    var updateTime: Date? = null
    var updater: String? = null
    var extraInfo: String? = null
}