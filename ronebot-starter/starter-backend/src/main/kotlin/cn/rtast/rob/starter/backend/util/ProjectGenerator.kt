/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rob.starter.backend.enums.ExtraFeature
import cn.rtast.rob.starter.backend.enums.ProjectType
import cn.rtast.rob.starter.backend.tempDir
import java.io.File

public fun generateProject(
    type: ProjectType,
    groupId: String,
    projectName: String,
    kotlinVersion: String,
    robVersion: String,
    uuid: String,
    extraFeatures: List<ExtraFeature>
): ByteArray {
    val packageName = "${groupId}.${projectName.lowercase()}"
    val wrapperProp = String(Resources.load("gradle/gradle-wrapper.properties"))
    var buildGradleKts = String(Resources.load(type.buildScriptName))
        .replace("{{GROUP_ID}}", groupId)
        .replace("{{KOTLIN_VERSION}}", kotlinVersion)
        .replace("{{ROB_VERSION}}", robVersion)
        .replace("{{MAIN_CLASS}}", "$packageName.MainKt")
    if (extraFeatures.isEmpty()) {
        buildGradleKts = buildGradleKts.replace("{{EXTRA_FEATURES}}", "")
    } else {
        val features = extraFeatures.joinToString("") { "\n    ${it.replacement.replace("{{ROB_VERSION}}", robVersion)}" }
        buildGradleKts = buildGradleKts.replace("{{EXTRA_FEATURES}}", features)
    }
    val mainKt = String(Resources.load(type.mainClassName))
        .replace("{{APP_PACKAGE}}", packageName)
    val settingsGradleKts = String(Resources.load("settings.gradle.kts"))
        .replace("{{PROJECT_NAME}}", projectName)
    val tempGeneratedDir = File(tempDir, uuid).apply { mkdirs() }
    File(tempGeneratedDir, "gradlew").writeBytes(Resources.load("gradlew"))
    File(tempGeneratedDir, "gradlew.bat").writeBytes(Resources.load("gradlew.bat"))
    File(tempGeneratedDir, ".gitignore").writeBytes(Resources.load("gitignore"))
    File(tempGeneratedDir, "gradle.properties").writeBytes(Resources.load("gradle.properties"))
    File(tempGeneratedDir, "build.gradle.kts").writeText(buildGradleKts)
    File(tempGeneratedDir, "settings.gradle.kts").writeText(settingsGradleKts)
    val gradleDir = File(tempGeneratedDir, "gradle").apply { mkdirs() }
    File(gradleDir, "gradle-wrapper.jar").writeBytes(Resources.load("gradle/gradle-wrapper.jar.1"))
    File(gradleDir, "gradle-wrapper.properties").writeText(wrapperProp)
    val srcDir = File(tempGeneratedDir, "src/main/kotlin/${packageName.replace(".", "/")}")
        .apply { mkdirs() }
    File(srcDir, "Main.kt").writeText(mainKt)
    val generatedZipFile = File(tempGeneratedDir, "$projectName.zip")
    zipDirectory(tempGeneratedDir, generatedZipFile)
    val targetBytes = generatedZipFile.readBytes()
    tempGeneratedDir.deleteRecursively()
    return targetBytes
}