package tech.chaosmin.framework.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue

/**
 * @author Romani min
 * @since 2020/12/14 14:20
 */
enum class UserStatusEnum(@EnumValue val code: Int) {
    INIT(0), VALID(1), INVALID(2), FROZEN(3)
}