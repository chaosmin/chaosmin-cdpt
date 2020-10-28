package tech.chaosmin.framework.exception.support

enum class ExceptionMsgPath(val code: Array<String?>, val path: Array<String?>) {
    FRM_EXCEPTION_MSG(
        arrayOf("frm_exception_msg", "app_exception_msg"),
        arrayOf("META-INF/errors_framework_zh.properties", "META-INF/errors_app_zh.properties")
    );

    override fun toString(): String {
        return code.indices.joinToString(",") { i -> "${name[i]}:${path[i]}" }
    }
}