import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.time.Year

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    id("maven-publish")
}

val libVersion: String by project

val year = Year.now().value.toString()

tasks.dokkaHtmlMultiModule {
    moduleName = "ROB OneBot11 SDK"
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "© $year RTAkland <a href='https://github.com/RTAkland/ROneBot'>ROneBot</a>"
        customAssets = listOf(
            rootProject.file("assets/logo-icon.svg")
        )
    }
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:2.0.0")
    }
}

subprojects {
    group = "cn.rtast"
    version = libVersion

    apply {
        if (!project.name.contains("starter-frontend")) {
            apply(plugin = "org.jetbrains.kotlin.jvm")
        }
        apply(plugin = "maven-publish")
        apply(plugin = "org.jetbrains.dokka")
    }

    kotlin {
        explicitApi()
    }

    tasks.named<DokkaTaskPartial>("dokkaHtmlPartial") {
        moduleName = "ROneBot - ${project.name}"
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "© $year RTAkland <a href='https://github.com/RTAkland/ROneBot'>ROneBot</a>"
            customAssets = listOf(
                rootProject.file("assets/logo-icon.svg")
            )
        }
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