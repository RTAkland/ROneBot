/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/25
 */


package cn.rtast.rob.exceptions

/**
 * 插件ID冲突
 */
class ConflictPluginIDException(
    private val pluginId: String,
    override val message: String = "Same plugin id detected! ($pluginId)",
) : Exception(message)