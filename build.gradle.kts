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
        plugin("org.jetbrains.kotlin.jvm")
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
                artifactId = when (project.name) {
                    "onebot" -> "ROneBot"
                    "kritor" -> "RKritor"
                    "satori" -> "RSatori"
                    else -> "ROneBotCommon"
                }
                version = libVersion
            }
        }

        repositories {
            maven {
                url = uri("https://repo.rtast.cn/api/v4/projects/33/packages/maven")
                credentials {
                    username = "RTAkland"
                    password = System.getenv("GITLAB_TOKEN")
                }
            }
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.compileKotlin {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }

    tasks.compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    base {
        archivesName = rootProject.name + "-${project.name}"
    }
}