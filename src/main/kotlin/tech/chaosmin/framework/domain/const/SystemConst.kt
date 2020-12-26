package tech.chaosmin.framework.domain.const

object SystemConst {
    const val DEFAULT_RETRY_TIME = 3
    const val DEFAULT_SLEEP_TIME = 1000L
    const val DEFAULT_PRINT_WARNINGS = false
    const val DEFAULT_PROGRESSIVE_WAIT = false
    const val DEFAULT_PROGRESSIVE_STEP = 1.2

    const val DEFAULT_CHARSET_NAME = "UTF-8"
    const val HTTP_METHOD = "httpMethod"
    const val REQUEST_URL = "url"
    const val ANONYMOUS = "anonymous"

    const val DEFAULT_CURRENCY = "01"
    const val DEFAULT_COMMISSION_RATIO = 0.1

    const val CACHE_NAMESPACE_AUTHORITY = "authority:"

    const val INIT_SUCCESSFULLY = ":> The {} was initialized successfully."
    const val INIT_FAILED = ":> The {} initialization failed."

    // 用户权限缓存时间为6小时
    const val AUTHORITY_CACHE_EXPIRE_TIME = 6 * 60 * 60L

    // 默认缓存时间为30分钟
    const val DEFAULT_CACHE_EXPIRE_TIME = 30 * 60L
}