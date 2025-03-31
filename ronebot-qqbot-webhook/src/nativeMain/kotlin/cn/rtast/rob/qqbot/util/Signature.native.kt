/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2025/03/31
 */

@file:OptIn(ExperimentalStdlibApi::class)

package cn.rtast.rob.qqbot.util

import cn.rtast.rob.ext.bytearray.plus
import cn.rtast.rob.ext.bytearray.toByteArray
import cn.rtast.rob.ext.bytearray.toHexString
import cn.rtast.rob.qqbot.api.SignOutbound
import cn.rtast.rob.qqbot.entity.internal.SignInbound
import io.github.andreypfau.curve25519.ed25519.Ed25519
import io.github.andreypfau.curve25519.ed25519.Ed25519PrivateKey


internal fun derivePrivateKeyFromSecret(botSecret: String): Ed25519PrivateKey {
    val secretKeyBytes = botSecret.toByteArray().copyOf(32)
    return Ed25519.keyFromSeed(secretKeyBytes)
}

internal fun signMessage(privateKey: Ed25519PrivateKey, message: ByteArray): String {
    val signedBytes = privateKey.sign(message)
    return signedBytes.toHexString()
}

internal actual fun handleValidation(payload: SignInbound, botSecret: String): SignOutbound {
    val privateKey = derivePrivateKeyFromSecret(botSecret)
    val message = payload.d.eventTs.toByteArray() + payload.d.plainToken.toByteArray()
    val signature = signMessage(privateKey, message)
    return SignOutbound(
        plainToken = payload.d.plainToken,
        signature = signature
    )
}