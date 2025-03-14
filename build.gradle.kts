plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
}

val libVersion: String by project

subprojects {
    group = "cn.rtast"
    version = libVersion

    apply {
        apply(plugin = "maven-publish")
        if (!project.name.lowercase().contains("frontend")) {
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "java")
        } else {
            apply(plugin = "org.jetbrains.kotlin.multiplatform")
        }
    }

    if (!project.name.lowercase().contains("frontend")) {
        tasks.compileJava {
            sourceCompatibility = "11"
            targetCompatibility = "11"
        }

        java {
            withSourcesJar()
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
                    artifactId = project.name
                    version = libVersion
                }
            }

            repositories {
                maven {
                    url = uri("https://maven.rtast.cn/releases/")
                    credentials {
                        username = "RTAkland"
                        password = System.getenv("PUBLISH_TOKEN")
                    }
                }

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
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net")
    }
}