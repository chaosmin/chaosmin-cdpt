package tech.chaosmin.framework.module.mgmt.domain.auth

data class Action(val httpMethod: String, val url: String) {
    override fun toString(): String {
        return "[$httpMethod] $url"
    }
}