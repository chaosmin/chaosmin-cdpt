/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * SensitiveRulesEnum.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.base.enums

/**
 * @author Romani min
 * @since 2021/9/8 17:27
 */
enum class SensitiveRulesEnum(val dataField: String, val dataType: String, val rule: RuleEnum) {
    DESENSITIZED_FULLY("任一字段全脱敏", "任意类型", RuleEnum.RULE_DESENSITIZED_FULLY),
    DISSHOW_FIELD("企业密钥|个人密钥|口令数据等", "不显示", RuleEnum.RULE_DISSHOW),
    NAME("姓名", "个人信息", RuleEnum.RULE_NAME),
    PASSWORD("密码", "个人信息", RuleEnum.RULE_PASSWORD),
    CARD_NO("银行卡号", "个人信息", RuleEnum.RULE_CARD_NO),
    CERTI_NO("证件号", "个人信息", RuleEnum.RULE_CERTI_NO),
    PHONE_NO("手机号", "个人信息", RuleEnum.RULE_PHONE_NO),
    EMAIL("邮箱", "个人信息", RuleEnum.RULE_EMAIL),
    HASH("hash值", "hash值", RuleEnum.RULE_HASH);

    enum class RuleEnum(val isShow: Boolean, val paddingStar: Int, val beforeIndex: Int, val afterIndex: Int) {
        RULE_DESENSITIZED_FULLY(true, 6, 0, 0),
        RULE_DESENSITIZED_1_(true, 1, 1, 0),
        RULE_NAME(true, 2, 1, 0),
        RULE_PASSWORD(true, 6, 0, 0),
        RULE_CARD_NO(true, 4, 4, 4),
        RULE_CERTI_NO(true, 16, 1, 1),
        RULE_PHONE_NO(true, 6, 3, 2),
        RULE_EMAIL(true, 3, 2, 1),
        RULE_DISSHOW(false, 0, 0, 0),
        RULE_HASH(true, 0, 0, 0);
    }
}
