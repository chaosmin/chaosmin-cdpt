package tech.chaosmin.framework.base.enums

/**
 * 业务唯一标识码生成规则 <p>
 *
 * @author Romani min
 * @since 2021/6/3 15:03
 */
enum class BizNoTypeEnum {
    // 纯数字
    NUMBER,
    // 带日期前缀 yyyyMMdd 如 20210601
    DATE,
    // 带时间前缀 HHmmss 如 135020
    TIME,
    // 带日期+时间前缀 yyyyMMddHHmmss 如 20210601135020
    DATETIME,
    // 带时间戳前缀
    TIMESTAMP
}