/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.util

import io.grpc.ClientInterceptor
import io.grpc.Metadata
import io.grpc.stub.MetadataUtils

private val authMetadataKey = Metadata.Key.of("ticket", Metadata.ASCII_STRING_MARSHALLER)

fun createAuthInterceptor(ticket: String): ClientInterceptor {
    val metadata = Metadata().apply { put(authMetadataKey, ticket) }
    return MetadataUtils.newAttachHeadersInterceptor(metadata)
}
