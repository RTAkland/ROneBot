/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.util

import io.grpc.Channel
import io.kritor.authentication.AuthenticationServiceGrpcKt
import io.kritor.authentication.authenticateRequest

suspend fun Channel.authenticate(account: String, ticket: String): Boolean {
    val stub = AuthenticationServiceGrpcKt.AuthenticationServiceCoroutineStub(this)
    return runCatching {
        stub.authenticate(authenticateRequest {
            this.account = account
            this.ticket = ticket
        })
    }.isSuccess
}