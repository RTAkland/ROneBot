/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 6:22 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.starter.backend.generators

import cn.rtast.rob.starter.backend.tempDir
import cn.rtast.rob.starter.backend.util.*
import cn.rtast.rob.starter.common.Language
import cn.rtast.rob.starter.common.ROneBotPlatform
import kotlinx.io.files.Path
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

    override fun generate(): ByteArray {
        val uuid = Uuid.random().toString()
        val mainFilePath = if (property.language == Language.Java) "entrypoint/$protocolName/$protocolName.java.1"
        else if (property.language == Language.Kotlin && property.isMultiplatform) "entrypoint/$protocolName/$protocolName.kotlin.kmp.k1.1"
        else if (property.language == Language.Kotlin && !property.isMultiplatform) "entrypoint/$protocolName/$protocolName.kotlin.jvm.k1.1"
        else throw IllegalStateException("没有对应的入口文件")
        val generatedFilesDir = Path(tempDir, uuid).mkdirs()
        var buildScriptString = loadAsString("buildScript/common.kts.1")
        var mainEntrypointString = loadAsString(mainFilePath)
        var gradleWrapperString = loadAsString("gradle/gradle-wrapper.properties")
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
        gradleWrapperString = gradleWrapperString.replace("{{GRADLE_VERSION}}", property.gradleVersion)
        gradleSettingsString = gradleSettingsString.replace("{{PROJECT_NAME}}", property.projectName)

        val gradleDir = Path(generatedFilesDir, "gradle")
        Path(gradleDir, "gradle-wrapper.jar").writeBytes(gradleWrapperBytes)
        Path(gradleDir, "gradle-wrapper.properties").writeText(gradleWrapperString)

        Path(generatedFilesDir, ".gitignore").writeText(gitignoreString)
        Path(generatedFilesDir, "gradlew").writeText(gradlewString)
        Path(generatedFilesDir, "gradlew.bat").writeText(gradlewBatString)
        Path(generatedFilesDir, "gradle.properties").writeText(gradlePropertiesString)
        Path(generatedFilesDir, "settings.gradle.kts").writeText(gradleSettingsString)
        Path(generatedFilesDir, "build.gradle.kts").writeText(buildScriptString)

        val mainFileName = if (property.language == Language.Kotlin) "main.kt" else "Main.java"
        Path(generatedFilesDir, "src/${property.language.languageName}/${property.groupId.replace(".", "/")}").mkdirs()
            .apply {
                Path(this, mainFileName).writeText(mainEntrypointString)
            }

        val generatedZipFile = Path(tempDir, "$uuid.zip")
        zipDirectory(generatedFilesDir, generatedZipFile)
        val zipFileBytes = generatedZipFile.readBytes()
        generatedFilesDir.deleteRecursively()
        generatedZipFile.delete()
        return zipFileBytes
    }
}