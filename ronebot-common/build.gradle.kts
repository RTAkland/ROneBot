import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
    mingwX64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines)
                api(libs.kotlin.stdlib)
                api("com.diglol.encoding:encoding:0.3.0")
            }
        }

        val jvmMain by getting {
            dependencies {
                api(libs.slf4j.api)
                api(libs.logback.classic)
                api(libs.brigadier)
                api(libs.gson)
            }
        }

        val mingwX64Main by getting {
            dependencies {
                api(libs.kotlin.serialization)
            }
        }

        val linuxX64Main by getting {
            dependencies {
                api(libs.kotlin.serialization)
            }
        }
    }
}