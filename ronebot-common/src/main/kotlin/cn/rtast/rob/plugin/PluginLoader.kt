/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/25
 */


package cn.rtast.rob.plugin

import java.io.File

interface PluginLoader {

    /**
     * 正常的插件map
     */
    val validPlugins: Map<BasePlugin, PluginConfig>

    /**
     * 插件目录
     */
    val pluginDir: File

    /**
     * 加载插件***只需要调用一次***
     */
    fun load()

    /**
     * 初始化插件
     */
    fun initPlugins()
}