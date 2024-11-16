package cn.rtast.rob.qqbot.entity.internal

import com.google.gson.annotations.SerializedName

internal data class SignInbound(
    val d: Body,
    val op: Int
) {
    data class Body(
        @SerializedName("plain_token")
        val plainToken: String,
        @SerializedName("event_ts")
        val eventTs: String,
    )
}