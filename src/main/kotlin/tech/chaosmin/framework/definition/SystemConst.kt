package tech.chaosmin.framework.definition

object SystemConst {
    // 系统HANDLER配置
    const val DEFAULT_PRINT_WARNINGS = false
    const val DEFAULT_PROGRESSIVE_WAIT = false
    const val DEFAULT_PROGRESSIVE_STEP = 1.2

    // 常用字符串定义
    const val HTTP_METHOD = "httpMethod"
    const val REQUEST_URL = "url"

    const val PRODUCT_ZH = "产品"
    const val PLAN_ZH = "计划"
    const val LIABILITY_ZH = "产品责任"
    const val RATE_TABLE_ZH = "费率表"

    // 系统默认值
    const val DEFAULT_RETRY_TIME = 3
    const val DEFAULT_SLEEP_TIME = 1000L
    const val DEFAULT_CHARSET_NAME = "UTF-8"
    const val ANONYMOUS = "anonymous"
    const val DEFAULT_CURRENCY = "01"
    const val DEFAULT_COMMISSION_RATIO = "10"

    // 标准字符集
    const val EXCEL_SPLIT_CHAR = '_'

    // 格式化日志输出
    const val INIT_SUCCESSFULLY = ":> The {} was initialized successfully."
    const val INIT_FAILED = ":> The {} initialization failed."
    const val HANDLE_START_LOG = "[{}] >>> start handle [{}]"

    // 用户权限缓存时间为6小时
    const val AUTHORITY_CACHE_EXPIRE_TIME = 6 * 60 * 60L

    // 默认缓存时间为30分钟
    const val DEFAULT_CACHE_EXPIRE_TIME = 30 * 60L
}