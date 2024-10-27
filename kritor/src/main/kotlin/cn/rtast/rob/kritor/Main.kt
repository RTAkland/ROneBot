/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/27
 */


package cn.rtast.rob.kritor

import io.grpc.Channel
import io.grpc.stub.StreamObserver
import io.kritor.authentication.AuthenticateResponse
import io.kritor.authentication.AuthenticationServiceGrpcKt
import io.kritor.authentication.authenticateRequest

suspend fun async(channel: Channel) {
    val observer: StreamObserver<AuthenticateResponse> = object: StreamObserver<AuthenticateResponse> {
        override fun onCompleted() {

        }

        override fun onNext(rsp: AuthenticateResponse?) {
            println(rsp)
        }

        override fun onError(e: Throwable?) {

        }
    }

    AuthenticationServiceGrpcKt.AuthenticationServiceCoroutineStub(channel).authenticate(authenticateRequest {
        account = "114514"
        ticket = "114514"
    })
}

fun main() {

}