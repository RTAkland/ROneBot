/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.kook.common.util

import cn.rtast.rob.util.decodeToByteArray
import cn.rtast.rob.util.decodeToString
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * 解密消息代码翻译自官方的Java示例
 */
public fun decryptMessage(data: String, key: String): String {
    val src: String = data.decodeToString()
    val iv = src.substring(0, 16)
    val newSecret: ByteArray = src.substring(16).decodeToByteArray()
    val finalKeyBuilder = StringBuilder(key)
    while (finalKeyBuilder.length < 32) {
        finalKeyBuilder.append("\u0000")
    }
    val finalKey = finalKeyBuilder.toString()
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(
        Cipher.DECRYPT_MODE,
        SecretKeySpec(finalKey.toByteArray(), "AES"),
        IvParameterSpec(iv.toByteArray(StandardCharsets.UTF_8))
    )
    return String(cipher.doFinal(newSecret))
}
