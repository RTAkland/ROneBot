import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
    mingwX64()
    linuxX64()

    compilerOptions {
        freeCompilerArgs = listOf("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines)
                api(libs.kotlin.serialization)
                api(libs.kotlin.stdlib)
                api("com.diglol.encoding:encoding:0.3.0")
                api("co.touchlab:stately-concurrent-collections:2.0.0")
                api("org.jetbrains.kotlinx:kotlinx-io-core:0.6.0")
                api("com.squareup.okio:okio:3.10.2")
            }
        }

        jvmMain {
            dependencies {
                api(libs.slf4j.api)
                api(libs.logback.classic)
                api(libs.java.websocket)
            }
        }

        nativeMain {
            dependencies {
                api("io.ktor:ktor-server-core:3.1.1")
                api("io.ktor:ktor-server-cio:3.1.1")
                api("io.ktor:ktor-server-websockets:3.1.1")
                api("io.ktor:ktor-client-cio:3.1.1")
                api("io.ktor:ktor-client-core:3.1.1")
                api("io.ktor:ktor-client-websockets:3.1.1")
//                api("org.jetbrains.kotlinx:kotlinx-io-core:0.7.0")
            }
        }
    }
}