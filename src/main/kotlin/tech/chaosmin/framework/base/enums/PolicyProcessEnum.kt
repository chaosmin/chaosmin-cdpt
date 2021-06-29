package tech.chaosmin.framework.base.enums

/**
 * 投保流程枚举 <p>
 *
 * @author Romani min
 * @since 2021/6/21 14:59
 */
enum class PolicyProcessEnum {
    // 保费试算
    PREMIUM_TRIAL,

    // 核保
    UNDERWRITING,

    // 承保
    ACCEPTANCE,

    // 保单取消
    POLICY_CANCEL,

    // 保单退保
    POLICY_REFUND
}