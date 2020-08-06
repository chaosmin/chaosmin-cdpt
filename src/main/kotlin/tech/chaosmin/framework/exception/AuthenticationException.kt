package tech.chaosmin.framework.exception

class AuthenticationException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        val INVALID_TOKEN = AuthenticationException("invalid token")
    }
}