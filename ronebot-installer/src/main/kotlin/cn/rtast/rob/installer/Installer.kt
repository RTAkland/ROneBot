/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/20
 */


package cn.rtast.rob.installer

interface Installer {

    /**
     * 拉取最新版本
     */
    suspend fun fetchLatestVersion(): String

    /**
     * 下载
     */
    suspend fun download()

    /**
     * 解压
     */
    suspend fun extract() {}
}