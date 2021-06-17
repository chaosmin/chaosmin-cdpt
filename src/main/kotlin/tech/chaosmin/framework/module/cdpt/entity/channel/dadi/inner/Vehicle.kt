package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

/**
 * @author Romani min
 * @since 2021/6/17 18:59
 */
class Vehicle {
    // 序号
    var sequenceNumber: Long? = null

    // 车牌号码
    var licenseNo: String? = null

    // 车辆使用性质
    var vehicleUseNatureCode: String? = null

    // 核定座位数
    var approvalSeatCount: String? = null

    // 车主
    var drivingLicenseOwner: String? = null

    // 发动机号
    var engineNo: String? = null

    // 车架号/VIN码
    var vin: String? = null
}