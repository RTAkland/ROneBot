/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rob.starter.backend.enums.ProjectType
import cn.rtast.rob.starter.backend.tempDir
import cn.rtast.rob.starter.common.ExtraFeature
import cn.rtast.rob.starter.common.ROneBotTarget
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

fun generateProject(
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
    try {
        val packageName = "${groupId}.${projectName.lowercase()}"
        val wrapperProp = Resources.loadAsString("gradle/gradle-wrapper.properties")
            .replace("{{GRADLE_VERSION}}", gradleVersion)
        var buildGradleKts =
            (if (targets.size == 1 && targets.contains(ROneBotTarget.Jvm)) {
                Resources.loadAsString("buildScript/plainJvm.kts")
            } else Resources.loadAsString(type.buildScriptName))
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
        buildGradleKts = if (!targets.contains(ROneBotTarget.MingwX64)) {
            buildGradleKts.replace(
                "{{MINGW}}", ""
            )
        } else {
            buildGradleKts.replace(
                "{{MINGW}}", "mingwX64 {\n" +
                        "        binaries.executable {\n" +
                        "            entryPoint = \"{{ENTRYPOINT}}\"\n" +
                        "        }\n" +
                        "    }"
            )
        }
        buildGradleKts = if (!targets.contains(ROneBotTarget.LinuxArm64)) {
            buildGradleKts.replace(
                "{{LINUX_ARM}}", ""
            )
        } else {
            buildGradleKts.replace(
                "{{LINUX_ARM}}", "mingwX64 {\n" +
                        "        binaries.executable {\n" +
                        "            entryPoint = \"{{ENTRYPOINT}}\"\n" +
                        "        }\n" +
                        "    }"
            )
        }
        buildGradleKts = if (!targets.contains(ROneBotTarget.MacOSArm64)) {
            buildGradleKts.replace(
                "{{MACOS_ARM}}", ""
            )
        } else {
            buildGradleKts.replace(
                "{{MACOS_ARM}}", "mingwX64 {\n" +
                        "        binaries.executable {\n" +
                        "            entryPoint = \"{{ENTRYPOINT}}\"\n" +
                        "        }\n" +
                        "    }"
            )
        }
        buildGradleKts = if (!targets.contains(ROneBotTarget.MacOSX64)) {
            buildGradleKts.replace(
                "{{MACOS}}", ""
            )
        } else {
            buildGradleKts.replace(
                "{{MACOS}}", "mingwX64 {\n" +
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
                extraFeatures.joinToString("") {
                    "\n                ${
                        it.replacement.replace(
                            "{{ROB_VERSION}}",
                            robVersion
                        )
                    }"
                }
            buildGradleKts =
                buildGradleKts.replace("{{EXTRA_FEATURES}}", features)
        }
        val mainKt = (if (targets.size == 1 && targets.contains(ROneBotTarget.Jvm) && type == ProjectType.OneBot11) {
            Resources.loadAsString("plainJvm.OneBot11.Main.kt")
        } else if (targets.size == 1 && targets.contains(ROneBotTarget.Jvm) && type == ProjectType.QQBot) {
            Resources.loadAsString("plainJvm.QQBot.Main.kt")
        } else {
            Resources.loadAsString(type.mainClassName)
        }).replace("{{APP_PACKAGE}}", packageName)
        val settingsGradleKts = Resources.loadAsString("settings.gradle.kts")
            .replace("{{PROJECT_NAME}}", projectName)
        val tempGeneratedDir = Path(tempDir, uuid).mkdirs()
        Path(tempGeneratedDir, "gradlew").writeText(Resources.loadAsString("gradlew"))
        Path(tempGeneratedDir, "gradlew.bat").writeText(Resources.loadAsString("gradlew.bat"))
        println(Path(tempGeneratedDir, "gradlew.bat").readText())
        Path(tempGeneratedDir, ".gitignore").writeText(Resources.loadAsString("gitignore"))
        Path(tempGeneratedDir, "gradle.properties").writeText(Resources.loadAsString("gradle.properties"))
        Path(tempGeneratedDir, "build.gradle.kts").writeText(
            buildGradleKts.replace("{{PLATFORM}}", type.platformName)
                .replace("{{ENTRYPOINT}}", "$packageName.main")
        )
        Path(tempGeneratedDir, "settings.gradle.kts").writeText(settingsGradleKts)
        val gradleDir = Path(tempGeneratedDir, "gradle").mkdirs()
        Path(gradleDir, "gradle-wrapper.jar").writeBytes(Resources.load("gradle/gradle-wrapper.jar.1"))
        Path(gradleDir, "gradle-wrapper.properties").writeText(wrapperProp)
        if (targets.size == 1 && targets.contains(ROneBotTarget.Jvm)) {
            val srcDir = Path(tempGeneratedDir, "src/main/kotlin/${packageName.replace(".", "/")}")
                .apply { SystemFileSystem.createDirectories(this) }
            Path(srcDir, "Main.kt").writeText(mainKt)
        } else {
            if (targets.contains(ROneBotTarget.Jvm)) {
                val srcDir = Path(tempGeneratedDir, "src/jvmMain/kotlin/${packageName.replace(".", "/")}")
                    .apply { SystemFileSystem.createDirectories(this) }
                Path(srcDir, "Main.kt").writeText(mainKt)
            }
            if (targets.contains(ROneBotTarget.MingwX64)) {
                val srcDir = Path(tempGeneratedDir, "src/mingwX64Main/kotlin/${packageName.replace(".", "/")}")
                    .apply { SystemFileSystem.createDirectories(this) }
                Path(srcDir, "main.kt").writeText(mainKt)
            }
            if (targets.contains(ROneBotTarget.LinuxX64)) {
                val srcDir = Path(tempGeneratedDir, "src/linuxX64Main/kotlin/${packageName.replace(".", "/")}")
                    .apply { SystemFileSystem.createDirectories(this) }
                Path(srcDir, "main.kt").writeText(mainKt)
            }
            if (targets.contains(ROneBotTarget.MacOSX64)) {
                val srcDir = Path(tempGeneratedDir, "src/macosX64/kotlin/${packageName.replace(".", "/")}")
                    .apply { SystemFileSystem.createDirectories(this) }
                Path(srcDir, "main.kt").writeText(mainKt)
            }
            if (targets.contains(ROneBotTarget.MacOSArm64)) {
                val srcDir = Path(tempGeneratedDir, "src/macosArm64/kotlin/${packageName.replace(".", "/")}")
                    .apply { SystemFileSystem.createDirectories(this) }
                Path(srcDir, "main.kt").writeText(mainKt)
            }
            if (targets.contains(ROneBotTarget.MacOSArm64)) {
                val srcDir = Path(tempGeneratedDir, "src/linuxArm64/kotlin/${packageName.replace(".", "/")}")
                    .apply { SystemFileSystem.createDirectories(this) }
                Path(srcDir, "main.kt").writeText(mainKt)
            }
        }
        val generatedZipFile = Path(tempDir, "$projectName.zip")
        zipDirectory(tempGeneratedDir, generatedZipFile)
        val targetBytes = generatedZipFile.readBytes()
        tempGeneratedDir.deleteRecursively()
        generatedZipFile.delete()
        return targetBytes
    } catch (e: Exception) {
        e.printStackTrace()
        return byteArrayOf()
    }
}