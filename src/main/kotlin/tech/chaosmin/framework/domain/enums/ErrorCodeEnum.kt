package tech.chaosmin.framework.domain.enums

enum class ErrorCodeEnum(val code: String, val msg: String) {
    SUCCESS("SUCCESS", "请求成功"),

    NO_TOKEN("TOKEN_00000", "未获取到Token"),
    TOKEN_INVALID("TOKEN_00001", "Token不合法"),
    NO_PERMISSION("PERMISSION_00001", "权限不足"),

    REQUEST_LIMITED("API_00001", "服务器请求达到上限"),

    FAILURE("SYS_00001", "请求失败"),
    USER_NOT_FOUND("SYS_10001", "未找到指定用户"),
    AUTHENTICATION_FAILED("SYS_10002", "登录失败")
}