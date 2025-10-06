import cn.rtast.rob.buildSrc.excludeModuleNames
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

plugins {
    alias(libs.plugins.signing)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.suspend.transformer) apply false
    alias(libs.plugins.kdef) apply false
}

val libVersion: String by extra

allprojects {
    group = "cn.rtast.rob"
    version = libVersion

    repositories {
        mavenCentral()
        maven("https://repo.maven.rtast.cn/releases/")
    }
}

subprojects {
    if (name in excludeModuleNames) return@subprojects
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "com.vanniktech.maven.publish")
    apply(plugin = "signing")
    apply(plugin = "org.jetbrains.kotlin.multiplatform")

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    mavenPublishing {
        publishing {
            repositories {
                maven {
                    name = "RTAST-SNAPSHOTS"
                    url = uri("https://repo.maven.rtast.cn/snapshots")
                    credentials {
                        username = "RTAkland"
                        password = System.getenv("RTAST_PUBLISH_PASSWORD")
                    }
                }
                maven {
                    name = "RTAST-RELEASES"
                    url = uri("https://repo.maven.rtast.cn/releases")
                    credentials {
                        username = "RTAkland"
                        password = System.getenv("RTAST_PUBLISH_PASSWORD")
                    }
                }
                mavenLocal()
            }
        }
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
        if (System.getenv("RTAST_PUBLISH_PASSWORD") == null) signAllPublications()
        coordinates("cn.rtast.rob", project.name, libVersion)
        pom {
            name = "ROneBot - ${project.name}"
            description = "A Kotlin multiplatform library for Milky | OneBot11 development"
            url = "https://github.com/RTAkland/ROneBot"
            developers {
                developer {
                    id = "rtakland"
                    name = "RTAkland"
                    email = "me@rtast.cn"
                }
                developer {
                    id = "xiaoman1221"
                    name = "xiaoman1221"
                    email = "xiaoman1221@yhdzz.cn"
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

if (System.getenv("RTAST_PUBLISH_PASSWORD") == null) {
    signing {
        useGpgCmd()
        sign(publishing.publications)
    }
}