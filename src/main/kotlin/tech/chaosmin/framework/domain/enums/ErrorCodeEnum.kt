package tech.chaosmin.framework.domain.enums

enum class ErrorCodeEnum(val code: String, val msg: String) {
    SUCCESS("SUCCESS", "SUCCESS"),

    TOKEN_INVALID("TOKEN_00001", "TOKEN_INVALID"),
    AUTHENTICATION_FAILED("", ""),

    NO_PERMISSION("PERMISSION_00001", "NO_PERMISSION"),

    USER_NOT_FOUND("SYS_10001", "USER_NOT_FOUND"),

    FAILURE("SYS_00001", "FAILURE")
}