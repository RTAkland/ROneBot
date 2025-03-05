/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */


package cn.rtast.rob.satori.entity

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.satori.satori.SatoriAction
import com.google.gson.annotations.SerializedName

public data class LoginInfo(
    val op: Int,
    val body: Body
) {
    public data class Body(
        @ExcludeField
        var action: SatoriAction,
        val logins: List<Login>
    )

    public data class Login(
        val user: User,
        val adapter: String,
        val platform: String,
        val status: Int,
        val feature: List<String>,
        @SerializedName("proxy_urls")
        val proxyUrls: List<String>,
    )

    public data class User(
        val id: String,
        val name: String,
        val nick: String,
        val avatar: String,
        @SerializedName("is_bot")
        val isBot: Boolean,
    )
}