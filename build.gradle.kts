@file:OptIn(ExperimentalBCVApi::class)

import cn.rtast.rob.buildSrc.excludeModuleNames
import kotlinx.validation.ExperimentalBCVApi
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

plugins {
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.binary.compatibility.validator)
}

val libVersion: String by project

allprojects {
    group = "cn.rtast.rob"
    version = libVersion

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net")
    }
}

subprojects {
    if (name in excludeModuleNames) return@subprojects
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "maven-publish")
    if (!project.name.contains("starter-backend")) {
        apply(plugin = "org.jetbrains.kotlin.multiplatform")
    } else {
        apply(plugin = "org.jetbrains.kotlin.jvm")
    }

    publishing {
        repositories {
            maven {
                url = uri("https://maven.rtast.cn/releases/")
                credentials {
                    username = "RTAkland"
                    password = System.getenv("PUBLISH_TOKEN")
                }
            }

            publications.withType<MavenPublication> {
                pom {
                    description = "A Kotlin multiplatform library for OneBot11 development"
                    url = "https://github.com/RTAkland/ROneBot"
                    developers {
                        developer {
                            id = "rtakland"
                            name = "RTAkland"
                            email = "me@rtast.cn"
                        }
                    }

                    licenses {
                        license {
                            name = "The Apache License, Version 2.0"
                            url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                        }
                    }

                    scm {
                        url = "https://github.com/RTAkland/ROneBot"
                        connection = "scm:git:git://github.com/RTAkland/ROneBot.git"
                        developerConnection = "scm:git:ssh://git@github.com/RTAkland/ROneBot.git"
                    }
                }
            }
        }
    }

    val wsAddress: String? by extra
    val wsPassword: String? by extra
    val qqGroupId: String? by extra

    tasks.withType<KotlinNativeTest> {
        environment("WS_ADDRESS", wsAddress ?: "")
        environment("WS_PASSWORD", wsPassword ?: "")
        environment("QQ_GROUP_ID", qqGroupId ?: "")
    }

    tasks.withType<KotlinJvmTest> {
        environment("WS_ADDRESS", wsAddress ?: "")
        environment("WS_PASSWORD", wsPassword ?: "")
        environment("QQ_GROUP_ID", qqGroupId ?: "")
    }
}

apiValidation {
    klib {
        enabled = true
    }
    nonPublicMarkers.add("cn.rtast.rob.annotations.InternalROBApi")
}