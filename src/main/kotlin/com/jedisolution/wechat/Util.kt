package com.jedisolution.wechat

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays

object SignUtil {
    // 与接口配置信息中的Token要一致
    private val token = "weixinmp"

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    fun checkSignature(signature: String, timestamp: String, nonce: String): Boolean {
        val arr = arrayOf(token, timestamp, nonce)
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr)
        var content: StringBuilder? = StringBuilder()
        for (i in arr.indices) {
            content!!.append(arr[i])
        }
        var md: MessageDigest? = null
        var tmpStr: String? = null

        try {
            md = MessageDigest.getInstance("SHA-1")
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            val digest = md!!.digest(content!!.toString().toByteArray())
            tmpStr = digest.toHex()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return if (tmpStr != null) tmpStr == signature.toUpperCase() else false
    }



    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

    fun ByteArray.toHex() : String{
        val result = StringBuffer()

        forEach {
            val octet = it.toInt()
            val firstIndex = (octet and 0xF0).ushr(4)
            val secondIndex = octet and 0x0F
            result.append(HEX_CHARS[firstIndex])
            result.append(HEX_CHARS[secondIndex])
        }

        return result.toString()
    }
}