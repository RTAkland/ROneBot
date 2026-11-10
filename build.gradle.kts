plugins {
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.maven.publish)
}

val libVersion = property("libVersion")!!.toString()

allprojects {
    group = "cn.rtast.rob"
    version = System.getenv("GITHUB_ENV")?.let { "$libVersion-SNAPSHOT" } ?: libVersion

    repositories {
        mavenCentral()
        maven("https://repo.rtast.cn/packages/")
        mavenLocal()
    }
}

val excludeModuleNames = listOf<String>()

subprojects {
    if (name in excludeModuleNames) return@subprojects
    pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
    pluginManager.apply("maven-publish")
    pluginManager.apply("org.jetbrains.kotlin.multiplatform")

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    publishing {
        repositories {
            maven("https://repo.rtast.cn/packages") {
                credentials(HttpHeaderCredentials::class) {
                    name = "Authorization"
                    val token = providers.gradleProperty("PUBLISH_TOKEN")
                        .orElse(providers.environmentVariable("PUBLISH_TOKEN"))
                        .orNull
                    value = "Bearer $token"
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }

        publications.withType<MavenPublication> {
            pom {
                name.set("")
                description.set("")
                url.set("https://repo.rtast.cn/RTAkland/")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("rtakland")
                        name.set("RTAkland")
                    }
                    developer {
                        id = "xiaoman1221"
                        name = "xiaoman1221"
                        email = "xiaoman1221@yhdzz.cn"
                    }
                }
            }
        }
    }
}