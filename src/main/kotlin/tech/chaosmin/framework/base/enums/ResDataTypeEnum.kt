package tech.chaosmin.framework.base.enums

import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2020/12/23 10:51
 */
enum class ResDataTypeEnum(private val code: Int, private val desc: String) : KeyValueEnum {
    TYPE_OF_CERTIFICATE(1, "证件类型"),
    GENDER(2, "性别"),
    CUSTOMER_CLASSIFICATION(3, "客户分类"),
    NATURE_OF_WORK(4, "工作单位性质"),
    BUILDING_STRUCTURE(5, "房屋建筑结构"),
    CURRENCY(6, "币种"),
    ORDER_STATUS(7, "订单状态"),
    RELATIONSHIP_WITH_INSURED(8, "与被保人关系"),
    RESPONSE_STATUS(9, "返回状态"),
    VALID_FLAG(10, "有效标志"),
    YES_NO_FLAG(11, "是否标志"),
    RELATED_PRODUCT_TYPE(12, "关联产品类型"),
    GROUP_SINGLE_FLAG(13, "团单标志"),
    RENEWAL_FLAG(14, "续保标志"),
    DOMESTIC_FOREIGN_FLAG(15, "境内外标志"),
    FREQUENCY(16, "频率"),
    TIME_UNIT(17, "时间单位"),
    INSURANCE_METHOD(18, "投保方式"),
    TYPE_OF_APPLICATION_FORM(19, "申请单类型"),
    CLAIM_STATUS(20, "理赔状态"),
    PAYMENT_STATUS(21, "支付状态"),
    RELATIONSHIP_WITH_MAIN_INSURED(22, "与主被保人关系"),
    POLICY_STATUS(23, "保单状态"),
    TYPE_OF_CORRECTION(24, "批改类型"),
    PAYMENT_METHOD(25, "支付方式"),
    NATURE_OF_USAGE(26, "使用性质"),
    OLD_NEW_CAR_FLAG(27, "新旧车标志"),
    TYPE_OF_DRIVING_LICENSE_VEHICLE(28, "行驶证车辆类型"),
    TRANSFER_FLAG(29, "过户标志"),
    TYPE_OF_VEHICLE(30, "车辆类型"),
    EYE_PROTECTION_PLAN(31, "眼睛保障计划"),
    TYPE_OF_DOCUMENT(32, "单证类型"),
    TYPE_OF_GOODS(33, "货物类型"),
    PACKAGE_OF_GOODS(34, "货物包装"),
    TYPES_OF_LEGAL_PERSON(35, "法人类型"),
    REGION(36, "地区"),
    NATIONALITY(37, "国籍"),
    INDUSTRY(38, "行业"),
    OCCUPATION(39, "职业");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}