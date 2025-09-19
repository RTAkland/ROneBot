/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 6:22 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.starter.backend.generators

import cn.rtast.rob.starter.backend.util.Resources
import cn.rtast.rob.starter.common.*
import io.ktor.util.*
import kotlin.uuid.ExperimentalUuidApi

abstract class BaseGeneratorImpl : BaseGenerator {
    abstract val protocolName: String
    abstract val property: GeneratorProperty

    override fun load(path: String): ByteArray = Resources.load(path)
    override fun loadAsString(path: String): String = Resources.loadAsString(path)

    val gradleWrapperBytes = load("gradle/gradle-wrapper.jar.1")
    val gitignoreString = loadAsString("gitignore")
    val gradlewString = loadAsString("gradlew")
    val gradlewBatString = loadAsString("gradlew.bat")
    val gradlePropertiesString = loadAsString("gradle.properties")
    val jvmBuildScriptString = loadAsString("buildScript/jvm.kts.1")
    val multiplatformBuildScriptString = loadAsString("buildScript/multiplatform.kts.1")

    val jvmSettingString = loadAsString("entrypoint/platform/jvm.txt")
    val kmpLinuxArm64SettingString = loadAsString("entrypoint/platform/linuxX64.txt")
    val kmpMacosArmX64SettingString = loadAsString("entrypoint/platform/macosArmX64.txt")
    val kmpLinuxX64SettingString = loadAsString("entrypoint/platform/linuxX64.txt")
    val kmpMingwX64SettingString = loadAsString("entrypoint/platform/mingwX64.txt")

    override fun generate(): List<GeneratedFileResponse> {
        val mainFilePath = if (property.language == Language.Java) "entrypoint/$protocolName/$protocolName.java.1"
        else if (property.language == Language.Kotlin && property.isMultiplatform) "entrypoint/$protocolName/$protocolName.kotlin.kmp.kt.1"
        else if (property.language == Language.Kotlin && !property.isMultiplatform) "entrypoint/$protocolName/$protocolName.kotlin.jvm.kt.1"
        else throw IllegalStateException("没有对应的入口文件")
        var buildScriptString = loadAsString("buildScript/common.kts.1")
        var mainEntrypointString = loadAsString(mainFilePath)
        var gradleWrapperPropertiesString = loadAsString("gradle/gradle-wrapper.properties")
        var gradleSettingsString = loadAsString("settings.gradle.kts")
        if (property.language == Language.Java) {
            buildScriptString = buildScriptString.replace("{{PLATFORM_CONFIG}}", jvmBuildScriptString)
        } else if (property.language == Language.Kotlin && property.isMultiplatform) {
            buildScriptString = buildScriptString.replace("{{PLATFORM_CONFIG}}", multiplatformBuildScriptString)
            val robPlatformConfig = StringBuilder()
            property.platforms.forEach {
                when (it) {
                    ROneBotPlatform.Jvm -> robPlatformConfig.append("\n$jvmSettingString")
                    ROneBotPlatform.LinuxX64 -> robPlatformConfig.append("\n$kmpLinuxX64SettingString")
                    ROneBotPlatform.MingwX64 -> robPlatformConfig.append("\n$kmpMingwX64SettingString")
                    ROneBotPlatform.LinuxArm64 -> robPlatformConfig.append("\n$kmpLinuxArm64SettingString")
                    ROneBotPlatform.MacOSArm64 -> robPlatformConfig.append("\n$kmpMacosArmX64SettingString")
                }
            }
            buildScriptString = buildScriptString.replace("{{PLATFORMS}}", robPlatformConfig.toString())
        } else if (property.language == Language.Kotlin && !property.isMultiplatform) {
            buildScriptString = buildScriptString.replace("{{PLATFORM_CONFIG}}", jvmBuildScriptString)
        } else throw IllegalStateException("没有对应的入口文件")
        buildScriptString = buildScriptString.replace("{{ROB_MODULE}}", protocolName)
        buildScriptString = buildScriptString.replace("{{ROB_VERSION}}", property.robVersion)

        val pluginsString = StringBuilder()
        property.plugins.forEach {
            pluginsString.append(
                it.idString.replace(
                    "{{KOTLIN_VERSION}}",
                    property.kotlinVersion
                )
            )
        }
        buildScriptString = buildScriptString.replace("{{PLUGINS}}", pluginsString.toString())
        buildScriptString = buildScriptString.replace("{{GROUP_ID}}", property.groupId)
        mainEntrypointString = mainEntrypointString.replace("{{PACKAGE_NAME}}", property.packageName)
        gradleWrapperPropertiesString =
            gradleWrapperPropertiesString.replace("{{GRADLE_VERSION}}", property.gradleVersion)
        gradleSettingsString = gradleSettingsString.replace("{{PROJECT_NAME}}", property.projectName)
        val mainFileName = if (property.language == Language.Kotlin) "main.kt" else "Main.java"
        val groupIdPath = property.groupId.replace(".", "/")
        return listOf(
            GeneratedFileResponse(".gitignore", gitignoreString.encodeBase64(), GeneratedFileType.PlainText),
            GeneratedFileResponse("gradlew.bat", gradlewBatString.encodeBase64(), GeneratedFileType.PlainText),
            GeneratedFileResponse("gradlew", gradlewString.encodeBase64(), GeneratedFileType.PlainText),
            GeneratedFileResponse(
                "gradle.properties",
                gradlePropertiesString.encodeBase64(),
                GeneratedFileType.PlainText
            ),
            GeneratedFileResponse(
                "settings.gradle.kts",
                gradleSettingsString.encodeBase64(),
                GeneratedFileType.PlainText
            ),
            GeneratedFileResponse("build.gradle.kts", buildScriptString.encodeBase64(), GeneratedFileType.PlainText),
            GeneratedFileResponse(
                "gradle/gradle-wrapper.jar",
                gradleWrapperBytes.encodeBase64(),
                GeneratedFileType.ByteArray
            ),
            GeneratedFileResponse(
                "gradle/gradle-wrapper.properties", gradleWrapperPropertiesString.encodeBase64(),
                GeneratedFileType.PlainText
            ),
            GeneratedFileResponse(
                "src/${property.language.languageName}/${groupIdPath}/$mainFileName",
                mainEntrypointString.encodeBase64(),
                GeneratedFileType.PlainText
            ),
        )
    }
}