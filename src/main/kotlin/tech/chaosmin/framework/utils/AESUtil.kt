package tech.chaosmin.framework.utils

import java.nio.charset.Charset
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @author Romani min
 * @since 2021/7/22 16:19
 */
object AESUtil {
    private fun charToByte(c: Char): Int = "0123456789ABCDEF".indexOf(c)
    const val KEY_LENGTH_BYTE = 32
    const val TAG_LENGTH_BIT = 128


    private fun hexStringToBytes(hexStr: String): ByteArray {
        var hexString = hexStr
        hexString = hexString.toUpperCase()
        val length = hexString.length / 2
        val hexChars = hexString.toCharArray()
        val b = ByteArray(length)
        for (i in 0 until length) {
            val pos = i * 2
            b[i] = (charToByte(hexChars[pos]) shl 4 or charToByte(hexChars[pos + 1]) and 0xff).toByte()
        }
        return b
    }

    fun decryptStr(src: String?): String {
        if (src.isNullOrBlank()) return ""
        val key = "FUjs@17654HGJKKn".toByteArray(charset("utf-8"))
        val secretKey: SecretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec("HGty&6%4ojyUyhgy".toByteArray(Charset.forName("UTF-8")))
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        val hexBytes = hexStringToBytes(src)
        val plainBytes = cipher.doFinal(hexBytes)
        return String(plainBytes, Charset.forName("UTF-8"))
    }

    fun decryptAES256GCM(aesKey: String, associatedData: String?, nonce: String, ciphertext: String): String {
        return try {
            val charset = Charset.forName("utf-8")
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val key = SecretKeySpec(aesKey.toByteArray(charset), "AES")
            val spec = GCMParameterSpec(TAG_LENGTH_BIT, nonce.toByteArray(charset))
            cipher.init(Cipher.DECRYPT_MODE, key, spec)
            cipher.updateAAD(associatedData?.toByteArray(charset))
            String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), charset)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(e)
        } catch (e: NoSuchPaddingException) {
            throw IllegalStateException(e)
        } catch (e: InvalidKeyException) {
            throw IllegalArgumentException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw IllegalArgumentException(e)
        }
    }
}