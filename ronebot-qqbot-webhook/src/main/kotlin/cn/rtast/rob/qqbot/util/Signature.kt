/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.util

import cn.rtast.rob.qqbot.api.SignOutbound
import cn.rtast.rob.qqbot.entity.internal.SignInbound
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.signers.Ed25519Signer
import org.bouncycastle.util.encoders.Hex
import java.io.ByteArrayOutputStream

internal fun derivePrivateKeyFromSecret(botSecret: String): Ed25519PrivateKeyParameters {
    val secretKey = botSecret.toByteArray(Charsets.UTF_8)
    val privateKeyBytes = secretKey.copyOf(32)
    return Ed25519PrivateKeyParameters(privateKeyBytes, 0)
}

internal fun signMessage(privateKey: Ed25519PrivateKeyParameters, message: ByteArray): String {
    val signer = Ed25519Signer()
    signer.init(true, privateKey)
    signer.update(message, 0, message.size)
    val signature = signer.generateSignature()
    return Hex.toHexString(signature)
}

internal fun handleValidation(payload: SignInbound, botSecret: String): SignOutbound {
    val privateKey = derivePrivateKeyFromSecret(botSecret)
    val message = ByteArrayOutputStream().apply {
        write(payload.d.eventTs.toByteArray(Charsets.UTF_8))
        write(payload.d.plainToken.toByteArray(Charsets.UTF_8))
    }.toByteArray()
    val signature = signMessage(privateKey, message)
    return SignOutbound(
        plainToken = payload.d.plainToken,
        signature = signature
    )
}