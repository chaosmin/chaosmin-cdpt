package tech.chaosmin.framework.configuration

import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import tech.chaosmin.framework.BaseTestMain
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/7/4 14:42
 */
internal class BCryptPasswordEncoderBootTest : BaseTestMain() {
    @Resource
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @Test
    fun encode() {
        println(passwordEncoder.encode("uGd10qd9DHW2DewC"))
    }
}