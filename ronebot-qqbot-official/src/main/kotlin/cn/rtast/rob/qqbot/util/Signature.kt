/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.util

//
//suspend fun handleValidation(payload: String) {
//    val random = SecureRandom()
//    val keyGen = KeyPairGenerator.getInstance("Ed25519", "BC") // Use Bouncy Castle provider
//    keyGen.initialize(Ed25519.SeedSize * 8, random)
//    val keyPair = keyGen.generateKeyPair()
//    val privateKey = keyPair.private
//
//    val msg = StringBuilder(validationPayload.d.eventTs).append(validationPayload.d.plainToken).toString()
//    val signature = ed25519Sign(msg, privateKey.encoded)
//
//    val response = ValidationResponse(
//        plainToken = validationPayload.d.plainToken,
//        signature = signature
//    )
//    val rspBytes = Gson().toJson(response)
//    call.respondText(rspBytes, ContentType.Application.Json)
//}
//
//fun signMessage(privateKeyBytes: ByteArray, message: String): ByteArray {
//    val keyFactory = KeyFactory.getInstance("Ed25519")
//    val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
//    val privateKey = keyFactory.generatePrivate(keySpec)
//    val signature = Signature.getInstance("EdDSA")
//    signature.initSign(privateKey)
//    signature.update(message.toByteArray())
//
//    // 返回签名结果
//    return signature.sign()
//}