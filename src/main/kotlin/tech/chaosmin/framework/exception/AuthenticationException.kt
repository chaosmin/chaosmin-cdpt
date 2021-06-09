package tech.chaosmin.framework.exception

import tech.chaosmin.framework.base.enums.ErrorCodeEnum

class AuthenticationException {
    companion object {
        val MISSED_TOKEN = FrameworkException(ErrorCodeEnum.NO_TOKEN.code)
        val INVALID_TOKEN = FrameworkException(ErrorCodeEnum.TOKEN_INVALID.code)
        val EXPIRED_TOKEN = FrameworkException(ErrorCodeEnum.TOKEN_EXPIRED.code)
    }
}