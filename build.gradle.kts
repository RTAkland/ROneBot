@file:OptIn(ExperimentalBCVApi::class)

import kotlinx.validation.ExperimentalBCVApi

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

//            maven {
//                url = uri("https://maven.rtast.cn/snapshots/")
//                credentials {
//                    username = "RTAkland"
//                    password = System.getenv("PUBLISH_TOKEN")
//                }
//            }
        }
    }
}

apiValidation {
    klib {
        enabled = true
    }
    nonPublicMarkers.add("cn.rtast.rob.annotations.InternalROBApi")
}