/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/8
 */


package cn.rtast.rob.satori.satori

interface ExtAction {
    suspend fun send(api: String, payload: String?): String
}