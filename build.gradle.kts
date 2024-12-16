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

    repositories {
        mavenCentral()
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
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/RTAkland/ROneBot")
                credentials {
                    username = "RTAkland"
                    password = System.getenv("GITHUB_TOKEN")
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