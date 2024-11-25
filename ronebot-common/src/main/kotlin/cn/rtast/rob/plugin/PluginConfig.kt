/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/25
 */


package cn.rtast.rob.plugin

data class PluginConfig(
    /**
     * 入口点
     */
    val entrypoint: String,
    /**
     * 插件ID ***唯一***
     */
    val id: String,
    /**
     * 插件名称
     */
    val name: String = id,
    /**
     * 插件版本
     */
    val version: String,
    /**
     * 插件作者
     */
    val author: String,

    /**
     * 插件描述
     */
    val description: String? = null,
)