/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rob.starter.backend.enums.ProjectType
import cn.rtast.rob.starter.backend.tempDir
import cn.rtast.rob.starter.common.ExtraFeature
import cn.rtast.rob.starter.common.Language
import cn.rtast.rob.starter.common.ROneBotTarget
import kotlinx.io.files.Path

private val platformList = listOf(
    "{{MACOS_ARM}}",
    "{{LINUX}}",
    "{{LINUX_ARM}}",
    "{{MINGW}}",
    "{{MACOS}}",
    "{{MACOS_ARM}}",
    "{{JVM}}"
)

private fun getPlatformEntrypoint(platform: String, entrypoint: String): String {
    val platformExecutableMap = mapOf(
        "{{LINUX}}" to "linuxX64 {\n" +
                "        binaries.executable {\n" +
                "            entryPoint = \"$entrypoint\"\n" +
                "        }\n" +
                "    }",
        "{{LINUX_ARM}}" to "linuxArmX64 {\n" +
                "        binaries.executable {\n" +
                "            entryPoint = \"$entrypoint\"\n" +
                "        }\n" +
                "    }",
        "{{MINGW}}" to "mingwX64 {\n" +
                "        binaries.executable {\n" +
                "            entryPoint = \"$entrypoint\"\n" +
                "        }\n" +
                "    }",
        "{{MACOS}}" to "macosX64 {\n" +
                "        binaries.executable {\n" +
                "            entryPoint = \"$entrypoint\"\n" +
                "        }\n" +
                "    }",
        "{{MACOS_ARM}}" to "macosArmX64 {\n" +
                "        binaries.executable {\n" +
                "            entryPoint = \"$entrypoint\"\n" +
                "        }\n" +
                "    }",
        "{{JVM}}" to "jvm()"
    )
    return platformExecutableMap[platform]!!
}

fun generateProject(
    type: ProjectType,
    groupId: String,
    projectName: String,
    kotlinVersion: String,
    robVersion: String,
    uuid: String,
    gradleVersion: String,
    @Suppress("unused")
    extraFeatures: List<ExtraFeature>,
    targets: List<ROneBotTarget>,
    language: Language,
    jvmOnlyLinter: String,
): ByteArray {
    val generatedFilesDir = Path(tempDir, uuid).mkdirs()
    val packageName = "$groupId.${projectName.lowercase()}"
    val entrypoint = "$packageName.main"
    val wrapper = Resources.load("gradle/gradle-wrapper.jar.1")
    val gradleWrapperProperties =
        Resources.loadAsString("gradle/gradle-wrapper.properties").replace("{{GRADLE_VERSION}}", gradleVersion)
    val gradleSettings = Resources.loadAsString("settings.gradle.kts").replace("{{PROJECT_NAME}}", projectName)
    var baseBuildScript = (if (language == Language.Kotlin)
        Resources.loadAsString("buildScript/native.kts") else
        Resources.loadAsString("buildScript/java.kts"))
        .replace("{{GROUP_ID}}", groupId)
        .replace("{{PLATFORM}}", type.platformName)
        .replace("{{ROB_VERSION}}", robVersion)
        .replace("{{EXTRA_FEATURES}}", "")
    val gradlew = Resources.loadAsString("gradlew")
    val gradlewBat = Resources.loadAsString("gradlew.bat")
    val gradleProperties = Resources.loadAsString("gradle.properties")
    val gitignore = Resources.loadAsString("gitignore")
    if (language == Language.Kotlin) {
        targets.forEach {
            baseBuildScript = baseBuildScript
                .replace(it.replaceName, getPlatformEntrypoint(it.replaceName, entrypoint))
        }
        baseBuildScript = baseBuildScript.replace("{{JVM_ONLY_LINTER_VERSION}}", jvmOnlyLinter)
            .replace("{{KOTLIN_VERSION}}", kotlinVersion)
        platformList.forEach {
            baseBuildScript = baseBuildScript.replace(it, "")
        }
        val mainClass = (if (type == ProjectType.OneBot11) {
            if (targets.size == 1 && targets.contains(ROneBotTarget.Jvm))
                Resources.loadAsString("plainJvm.OneBot11.Main.kt")
            else Resources.loadAsString("OneBot11.Main.kt")
        } else {
            if (targets.size == 1 && targets.contains(ROneBotTarget.Jvm))
                Resources.loadAsString("plainJvm.QQBot.Main.kt")
            else Resources.loadAsString("QQBot.Main.kt")
        }).replace("{{APP_PACKAGE}}", packageName)
        Path(generatedFilesDir, "build.gradle.kts").writeText(baseBuildScript)
        val srcPath = Path(generatedFilesDir, "src/commonMain/kotlin/${packageName.replace(".", "/")}").mkdirs()
        Path(srcPath, "Main.kt").writeText(mainClass)
    } else {
        baseBuildScript = baseBuildScript.replace("{{MAIN_CLASS}}", "$packageName.Main")
        Path(generatedFilesDir, "build.gradle.kts").writeText(baseBuildScript)
        val mainClass = (if (type == ProjectType.OneBot11)
            Resources.loadAsString("main.onebot11.java.1")
        else Resources.loadAsString("main.qqbot.java.1")).replace("{{APP_PACKAGE}}", packageName)
        val srcPath = Path(generatedFilesDir, "src/main/kotlin/${packageName.replace(".", "/")}").mkdirs()
        Path(srcPath, "Main.java").writeText(mainClass)
    }
    val gradleWrapperPath = Path(generatedFilesDir, "gradle/wrapper").mkdirs()
    Path(gradleWrapperPath, "gradle-wrapper.properties").writeText(gradleWrapperProperties)
    Path(gradleWrapperPath, "gradle-wrapper.jar").writeBytes(wrapper)
    Path(generatedFilesDir, "settings.gradle.kts").writeText(gradleSettings)
    Path(generatedFilesDir, "gradlew").writeText(gradlew)
    Path(generatedFilesDir, "gradlew.bat").writeText(gradlewBat)
    Path(generatedFilesDir, "gradle.properties").writeText(gradleProperties)
    Path(generatedFilesDir, ".gitignore").writeText(gitignore)
    val generatedZipFile = Path(tempDir, "$projectName.zip")
    zipDirectory(generatedFilesDir, generatedZipFile)
    val targetBytes = generatedZipFile.readBytes()
    generatedFilesDir.deleteRecursively()
    generatedZipFile.delete()
    return targetBytes
}