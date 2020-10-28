package tech.chaosmin.framework.domain.enums

enum class ErrorCodeEnum(val code: String, val msg: String) {
    SUCCESS("SUCCESS", "SUCCESS"),
    TOKEN_INVALID("TOKEN_0001", "TOKEN_INVALID"),
    NO_PERMISSION("PERMISSION_0001", "NO_PERMISSION"),
    FAILURE("SYS_00001", "FAILURE")
}