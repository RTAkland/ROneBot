/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/25
 */


package cn.rtast.rob.plugin

import cn.rtast.rob.BotInstance
import cn.rtast.rob.exceptions.ConflictPluginIDException
import cn.rtast.rob.util.fromJson
import java.io.File
import java.util.jar.JarFile

class OneBotPluginLoader internal constructor(
    private val botInstances: List<BotInstance>,
) : PluginLoader {

    override val validPlugins = mutableMapOf<BasePlugin, PluginConfig>()

    override val pluginDir = File("./plugins").apply { mkdirs() }

    override fun load() {
        validPlugins.clear()
        val jarFiles = pluginDir.listFiles()?.toList() ?: emptyList()
        val validJarFiles = jarFiles.filter { !it.isDirectory }
            .filter { it.extension == "jar" }
        try {
            validJarFiles.forEach {
                JarFile(it).use { jarFile ->
                    jarFile.getJarEntry("onebot-plugin.json")?.let {
                        val config = jarFile.getInputStream(it)
                            .bufferedReader().use { it.readText() }
                            .fromJson<PluginConfig>()
                        if (validPlugins.values.contains(config)) {
                            throw ConflictPluginIDException(config.id)
                        } else {
                            val pluginInstance = this.createInstanceOf<BasePlugin>(config.entrypoint)
                            validPlugins[pluginInstance!!] = config
                        }
                    }
                }
            }
        } catch (_: Exception) {

        }
    }

    override fun initPlugins() {
        validPlugins.keys.forEach {
            it.onInitialized(OneBotPluginAPI(botInstances))
        }
    }

    fun createInstance(classPath: String): Any {
        return Class.forName(classPath).getDeclaredConstructor().newInstance()
    }

    inline fun <reified T> createInstanceOf(classPath: String): T? {
        val instance = createInstance(classPath)
        return instance as? T
    }
}