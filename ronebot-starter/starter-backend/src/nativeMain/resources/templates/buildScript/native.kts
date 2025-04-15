import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    kotlin("multiplatform") version "{{KOTLIN_VERSION}}"
    id("com.gradleup.shadow") version "8.3.0"
}

val appVersion: String by extra

group = "{{GROUP_ID}}"
version = appVersion

repositories {
    mavenCentral()
    maven("https://repo.maven.rtast.cn/releases")
    maven("https://repo.maven.rtast.cn/snapshots")
}

kotlin {
    {{LINUX}}
    {{LINUX_ARM}}
    {{MINGW}}
    {{MACOS}}
    {{MACOS_ARM}}
    {{JVM}}

    sourceSets {
        commonMain {
            dependencies {
                implementation("cn.rtast.rob:{{PLATFORM}}:{{ROB_VERSION}}"){{EXTRA_FEATURES}}
            }
        }
    }
}

kotlin.targets.named<KotlinJvmTarget>("jvm") {
    compilations.named("main") {
        tasks {
            val shadowJar = register<ShadowJar>("jvmShadowJar") {
                group = "build"
                from(output)
                configurations = listOf(runtimeDependencyFiles)
                archiveAppendix.set("jvm")
                archiveClassifier.set("all")
                manifest {
                    attributes("Main-Class" to "{{MAIN_CLASS}}")
                }
                mergeServiceFiles()
            }
            getByName("jvmJar") {
                finalizedBy(shadowJar)
            }
        }
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.WARN
}