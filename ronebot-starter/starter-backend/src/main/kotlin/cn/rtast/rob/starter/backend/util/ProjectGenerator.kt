/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rob.starter.backend.enums.ExtraFeature
import cn.rtast.rob.starter.backend.enums.ProjectType
import cn.rtast.rob.starter.backend.tempDir
import cn.rtast.rob.starter.common.ROneBotTarget
import java.io.File

public fun generateProject(
    type: ProjectType,
    groupId: String,
    projectName: String,
    kotlinVersion: String,
    robVersion: String,
    uuid: String,
    gradleVersion: String,
    extraFeatures: List<ExtraFeature>,
    targets: List<ROneBotTarget>
): ByteArray {
    val packageName = "${groupId}.${projectName.lowercase()}"
    val wrapperProp = String(Resources.load("gradle/gradle-wrapper.properties"))
        .replace("{{GRADLE_VERSION}}", gradleVersion)
    var buildGradleKts = String(Resources.load(type.buildScriptName))
        .replace("{{GROUP_ID}}", groupId)
        .replace("{{KOTLIN_VERSION}}", kotlinVersion)
        .replace("{{ROB_VERSION}}", robVersion)
        .replace("{{MAIN_CLASS}}", "$packageName.MainKt")
    if (!targets.contains(ROneBotTarget.Jvm)) {
        buildGradleKts = buildGradleKts.replace("jvm()", "")
    }
    buildGradleKts = if (!targets.contains(ROneBotTarget.LinuxX64)) {
        buildGradleKts.replace(
            "{{LINUX}}", ""
        )
    } else {
        buildGradleKts.replace(
            "{{LINUX}}", "linuxX64 {\n" +
                    "        binaries.executable {\n" +
                    "            entryPoint = \"{{ENTRYPOINT}}\"\n" +
                    "        }\n" +
                    "    }"
        )
    }
    if (!targets.contains(ROneBotTarget.MingwX64)) {
        buildGradleKts = buildGradleKts.replace(
            "{{MINGW}}", ""
        )
    } else {
        buildGradleKts = buildGradleKts.replace(
            "{{MINGW}}", "mingwX64 {\n" +
                    "        binaries.executable {\n" +
                    "            entryPoint = \"{{ENTRYPOINT}}\"\n" +
                    "        }\n" +
                    "    }"
        )
    }
    if (extraFeatures.isEmpty()) {
        buildGradleKts = buildGradleKts.replace("{{EXTRA_FEATURES}}", "")
    } else {
        val features =
            extraFeatures.joinToString("") { "\n                ${it.replacement.replace("{{ROB_VERSION}}", robVersion)}" }
        buildGradleKts =
            buildGradleKts.replace("{{EXTRA_FEATURES}}", features)
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
    File(tempGeneratedDir, "build.gradle.kts").writeText(
        buildGradleKts.replace("{{PLATFORM}}", type.platformName)
            .replace("{{ENTRYPOINT}}", "$packageName.main")
    )
    File(tempGeneratedDir, "settings.gradle.kts").writeText(settingsGradleKts)
    val gradleDir = File(tempGeneratedDir, "gradle").apply { mkdirs() }
    File(gradleDir, "gradle-wrapper.jar").writeBytes(Resources.load("gradle/gradle-wrapper.jar.1"))
    File(gradleDir, "gradle-wrapper.properties").writeText(wrapperProp)
    if (targets.contains(ROneBotTarget.Jvm)) {
        val srcDir = File(tempGeneratedDir, "src/jvmMain/kotlin/${packageName.replace(".", "/")}")
            .apply { mkdirs() }
        File(srcDir, "Main.kt").writeText(mainKt)
    }
    if (targets.contains(ROneBotTarget.MingwX64)) {
        val srcDir = File(tempGeneratedDir, "src/mingwX64Main/kotlin/${packageName.replace(".", "/")}")
            .apply { mkdirs() }
        File(srcDir, "main.kt").writeText(mainKt)
    }
    if (targets.contains(ROneBotTarget.LinuxX64)) {
        val srcDir = File(tempGeneratedDir, "src/linuxX64Main/kotlin/${packageName.replace(".", "/")}")
            .apply { mkdirs() }
        File(srcDir, "main.kt").writeText(mainKt)
    }
    val generatedZipFile = File(tempGeneratedDir, "$projectName.zip")
    zipDirectory(tempGeneratedDir, generatedZipFile)
    val targetBytes = generatedZipFile.readBytes()
    tempGeneratedDir.deleteRecursively()
    return targetBytes
}