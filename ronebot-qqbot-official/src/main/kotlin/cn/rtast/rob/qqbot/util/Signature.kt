/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.util

import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.crypto.signers.Ed25519Signer
import java.security.SecureRandom

fun derivePublicKeyFromSecret(secret: String): ByteArray {
    val seed = buildString {
        while (length < 32) {
            append(secret)
        }
    }.substring(0, 32).toByteArray(Charsets.UTF_8)
    val secureRandom = SecureRandom(seed)
    val publicKey = ByteArray(32)
    val privateKey = ByteArray(64)
    secureRandom.nextBytes(privateKey)
    System.arraycopy(privateKey, 32, publicKey, 0, 32)
    return publicKey
}

fun verifySignature(publicKey: ByteArray, signatureHex: String, timestamp: String, body: String): Boolean {
    val signature = hexStringToByteArray(signatureHex)
    val message = (timestamp + body).toByteArray(Charsets.UTF_8)
    val ed25519PublicKeyParams = Ed25519PublicKeyParameters(publicKey, 0)
    val signer = Ed25519Signer()
    signer.init(false, ed25519PublicKeyParams)
    signer.update(message, 0, message.size)
    return signer.verifySignature(signature)
}