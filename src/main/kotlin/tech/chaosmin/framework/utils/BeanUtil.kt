package tech.chaosmin.framework.utils

/**
 * @author Romani min
 * @since 2021/9/7 15:28
 */
object BeanUtil

inline fun <reified T : Any> newInstance(vararg params: Any): T {
    return T::class.java.getDeclaredConstructor(*params.map { it::class.java }.toTypedArray()).apply { isAccessible = true }.newInstance(*params)
}