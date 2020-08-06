package tech.chaosmin.framework.domain.auth

sealed class Authentication {
    abstract val username: String
    abstract val roles: List<String>
}

object AnonymousAuthentication : Authentication() {
    override val username: String = "anonymous"
    override val roles: List<String> = listOf("anonymous")
}

data class LoginAuthentication(
    override val username: String,
    override val roles: List<String>,
    val token: String
) : Authentication()