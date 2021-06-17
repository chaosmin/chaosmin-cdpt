package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import java.util.*

/**
 * @author Romani min
 * @since 2021/6/17 18:54
 */
class PersonInsured {
    // 被保人序号
    var sequenceNumber: Long? = null

    // 客户角色类型(被保人,附属被保人)
    var customerRoleCode: String? = null

    // 客户性质
    var partyCategory: String? = null

    // 上海平台客户编码
    var platCustomSeqNo: String? = null

    // 与投保人关系
    var polHolderInsuredRelaCode: String? = null

    // 客户姓名
    var customerName: String? = null

    // 性别
    var indiGenderCode: String? = null

    // 年龄
    var age: Long? = null

    // 出生日期
    var dateOfBirth: Date? = null

    // 证件类型
    var idType: String? = null

    // 证件号码
    var idNo: String? = null

    // 证件有效期
    var idValidThru: Date? = null

    // 手机号
    var indiMobile: String? = null

    // 受益方式
    var benefitModeCode: String? = null

    // 关联保障计划组别(关联责任分组的标的组序号)
    var keyCoveragePlanGroup: String? = null

    // 关联家庭保障计划组别(关联家庭分组的组序号)
    var coverageFamilyPlanGroup: String? = null

    // 保障类型
    var guaranteeType: String? = null

    // 职业代码
    var occupationCode: String? = null

    // 职业类别
    var occupationType: String? = null

    // 医保产品职业代码
    var medicalCareOccupationCode: String? = null

    // 学生类型/工作职务
    var studentType: String? = null

    // 是否使用公共保障计划
    var isUsePublicLiability: String? = null

    // 是否有约定附属被保人
    var hasAffilInsured: String? = null

    //约定附属被保人人数
    var affilInsuredNumber: Long? = null

    // 健康状况
    var healthStatus: String? = null

    // 在职标志
    var isOnJob: String? = null

    // 生育状况
    var bearStatus: String? = null

    // 单位性质
    var branchNatureCode: String? = null

    // 婚姻状况
    var maritalStatus: String? = null

    // 社保类型
    var medicalInsuranceCode: String? = null

    // 社保标识
    var socialSecurityFlag: String? = null

    // 联系地址
    var address: String? = null

    // 邮件地址
    var email: String? = null

    // 国籍
    var nationalityCode: String? = null

    // 邮编
    var postCode: String? = null

    // 行业分类(存放代码表中行业小类码值)
    var industryCategory: String? = null

    // 社保卡号
    var socialSecurityNo: String? = null

    // 代理人身份
    var authAgentIdentity: String? = null

    // 关联主被保险人序号
    var refInsuredSeq: Long? = null

    // 附属被保险人与被保险人关系
    var addInsuredInsuredRelaCode: String? = null

    // 学校
    var schoolName: String? = null

    // 班级
    var classes: String? = null

    // 学号
    var studentNo: String? = null

    // 手术类型
    var operationType: String? = null

    // 车/船次号
    var vehicleNo: String? = null

    // 座位号
    var seatNo: String? = null

    // 起始站
    var startStation: String? = null

    // 到达站
    var destination: String? = null

    // 客票号
    var ticketCode: String? = null

    // 留学国家或地区
    var studyAbroadCountryCode: String? = null

    // 留学国家或地区教育机构名称
    var studyAbroadOrgName: String? = null

    //  航班号
    var flightNo: String? = null

    // 乘机日
    var checkInDate: Date? = null

    // 麻醉类型
    var anesthesiaType: String? = null

    // 被保险人单证流水号
    var insuredPrintNo: String? = null

    // 身高
    var height: Double? = null

    // 体重
    var weight: Double? = null

    // 受益人列表
    var beneficiaryList: List<Beneficiary>? = null

    // 告知信息列表
    var customerDeclarationList: List<CustomerDeclaration>? = null
}