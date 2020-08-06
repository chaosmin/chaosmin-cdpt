package tech.chaosmin.framework.domain.auth

sealed class Action(val type: String) {
    abstract val value: String
}

data class UrlAction(override val value: String) : Action("url")

data class HttpMethodAction(override val value: String) : Action("http_method")