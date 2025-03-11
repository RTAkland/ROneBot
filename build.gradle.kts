import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    id("maven-publish")
}

val libVersion: String by project

subprojects {
    group = "cn.rtast"
    version = libVersion

    apply {
        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "maven-publish")
    }

    kotlin {
        explicitApi()
    }

}

allprojects {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net")
    }

    tasks.compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }

    tasks.compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
}