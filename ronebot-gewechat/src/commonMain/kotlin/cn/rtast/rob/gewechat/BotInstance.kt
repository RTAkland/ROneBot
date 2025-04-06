/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:13
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.gewechat

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.gewechat.entity.DoLoginRequest
import cn.rtast.rob.gewechat.entity._GetQRCodeRequest
import cn.rtast.rob.gewechat.entity._SetCallbackUrlRequest
import cn.rtast.rob.gewechat.event.internal.DoLoginResponse
import cn.rtast.rob.gewechat.event.internal.GetQRCodeResponse
import cn.rtast.rob.gewechat.event.internal._GetTokenResponse
import cn.rtast.rob.gewechat.event.internal._SetCallbackUrlResponse
import cn.rtast.rob.gewechat.exceptions.GetTokenException
import cn.rtast.rob.gewechat.exceptions.LoginFailedException
import cn.rtast.rob.gewechat.exceptions.SetCallbackUrlFailedException
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.getLogger
import cn.rtast.rob.util.toJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

public class BotInstance internal constructor(
    private val hostUrl: String,
    private val port: Int,
    private val callbackUrl: String,
) : BaseBotInstance {

    internal val logger = getLogger("[S]")

    private val token = runBlocking { this@BotInstance.getToken() }

    /**
     * 1. 先获取Token,这一步是内部操作不需要手动调用
     */
    internal suspend fun getToken(): String {
        val response = httpClient.post("$hostUrl/tools/getTokenId") {
            contentType(ContentType.Application.Json)
        }.bodyAsText().fromJson<_GetTokenResponse>()
        if (response.ret != 200) {
            logger.error("获取Token失败 ${response.msg}")
            throw GetTokenException("获取Token失败 ${response.msg}")
        } else {
            logger.info("获取Token成功")
        }
        return response.data
    }

    /**
     * 2. 获取二维码并且扫码登录
     */
    public suspend fun getQRCode(appId: String? = null): GetQRCodeResponse.QRCodeResponse {
        return httpClient.post("$hostUrl/login/getLoginQrCode") {
            contentType(ContentType.Application.Json)
            headers {
                header("X-GEWE-TOKEN", token)
            }
            setBody(_GetQRCodeRequest(appId).toJson())
        }.bodyAsText().fromJson<GetQRCodeResponse>().data
    }

    /**
     * 3. 完成上面两布之后调用此方法进行登录
     */
    public suspend fun doLogin(data: GetQRCodeResponse.QRCodeResponse, captcha: String): DoLoginResponse.LoginResponse {
        val response = httpClient.post("$hostUrl/login/checkLogin") {
            contentType(ContentType.Application.Json)
            header("X-GEWE-TOKEN", token)
            setBody(DoLoginRequest(data.appId, data.uuid, captcha).toJson())
        }.bodyAsText().fromJson<DoLoginResponse>()
        if (response.ret != 200) {
            logger.error("登陆失败 ${response.msg}")
            throw LoginFailedException("登陆失败 ${response.msg}")
        } else {
            logger.info("登陆成功")
        }
        return response.data
    }

    /**
     * 4. 设置回调地址
     */
    public suspend fun setCallback() {
        val response = httpClient.post("$hostUrl/tools/setCallback") {
            contentType(ContentType.Application.Json)
            headers {
                header("X-GEWE-TOKEN", token)
            }
            setBody(_SetCallbackUrlRequest(token, callbackUrl).toJson())
        }.bodyAsText().fromJson<_SetCallbackUrlResponse>()
        if (response.ret != 200) {
            logger.warn("设置回调URL失败 ${response.msg}")
            throw SetCallbackUrlFailedException("设置回调URL失败 ${response.msg}")
        } else {
            logger.info("设置回调地址成功")
        }
    }

    /**
     * 开启HTTP服务器
     */
    override suspend fun createBot(): BaseBotInstance {
        TODO()
    }

    override suspend fun disposeBot() {
    }

    override val isActionInitialized: Boolean = true
}