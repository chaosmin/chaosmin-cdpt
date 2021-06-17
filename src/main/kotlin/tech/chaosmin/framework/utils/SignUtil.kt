package tech.chaosmin.framework.utils

import org.apache.commons.codec.binary.Base64
import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelReq
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * @author Romani min
 * @since 2021/6/11 10:14
 */
object SignUtil {
    fun sortedParam(req: BaseChannelReq) {
        val params = JsonUtil.convert2Map(JsonUtil.encode(req))
        params.toSortedMap()
    }

    fun base64AndMD5(bodyBytes: ByteArray): String {
        return try {
            val md = MessageDigest.getInstance("MD5")
            md.reset()
            md.update(bodyBytes)
            val base64 = Base64()
            String(base64.encode(md.digest()))
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalArgumentException("Unknown algorithm MD5")
        }
    }
}