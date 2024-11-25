/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/25
 */


package cn.rtast.rob.plugin

interface BasePlugin {
    /**
     * 插件初始化
     */
    fun onInitialized(api: BasePluginAPI)

    /**
     * 插件被禁用
     */
    fun onShutdown(api: BasePluginAPI)
}