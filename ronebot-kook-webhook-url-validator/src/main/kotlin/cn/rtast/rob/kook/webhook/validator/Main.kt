/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.validator

import cn.rtast.rob.kook.webhook.validator.util.HttpServer

public fun main(args: Array<String>) {
    val port = args.getOrNull(0)?.toInt() ?: 8082
    val encryptKey = args.getOrElse(1) {
        throw IllegalArgumentException("必须提供一个encrypt密钥, 这个密钥值用于解密Kook服务器下发的信息")
    }
    println("Http服务器已经自动在端口: $port 请在kook开放平台验证地址")
    HttpServer(port, encryptKey).apply { startHttpServer() }
}