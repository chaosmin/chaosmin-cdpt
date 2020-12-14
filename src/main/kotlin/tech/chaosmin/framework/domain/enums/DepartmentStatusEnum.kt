package tech.chaosmin.framework.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue

/**
 * @author Romani min
 * @since 2020/12/14 14:22
 */
enum class DepartmentStatusEnum(@EnumValue val code: Int) {
    VALID(1), INVALID(2)
}