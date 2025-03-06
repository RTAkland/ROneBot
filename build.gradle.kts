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
        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "maven-publish")
        apply(plugin = "org.jetbrains.dokka")
    }

    repositories {
        mavenCentral()
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

    val sourceJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    artifacts {
        archives(sourceJar)
    }

    tasks.jar {
        from("LICENSE") {
            rename { "ROneBot-LICENSE-Apache2.0" }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                artifact(sourceJar)
                artifactId = project.name
                version = libVersion
            }
        }

        repositories {
//            maven {
//                url = uri("https://maven.rtast.cn/releases/")
//                credentials {
//                    username = "RTAkland"
//                    password = System.getenv("PUBLISH_TOKEN")
//                }
//            }

            maven {
                url = uri("https://maven.rtast.cn/snapshots/")
                credentials {
                    username = "RTAkland"
                    password = System.getenv("PUBLISH_TOKEN")
                }
            }
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