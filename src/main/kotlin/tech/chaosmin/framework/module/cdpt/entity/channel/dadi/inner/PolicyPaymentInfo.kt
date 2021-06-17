package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

/**
 * 大地保险付款信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:41
 */
class PolicyPaymentInfo {
    // 银行账户持有人姓名
    var accountHolderName: String? = null

    // 银行账号
    var accountNo: String? = null

    // 支付方式
    var paymentMethodCode: String? = null

    // 账户性质
    var accountType: String? = null

    // 银行所属省市区
    var regionCode: String? = null

    // 银行名称
    var bankCode: String? = null

    // 开户行名称
    var depositeBankName: String? = null

    // 是否接受我公司短信
    var isReceiveSms: String? = null

    // 手机号
    var tel: String? = null

    // 分期数
    var installmentPeriodCount: Long? = null

    var installmentList: List<Installment>? = null
}