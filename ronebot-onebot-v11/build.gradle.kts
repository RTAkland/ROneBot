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
                api(project(":ronebot-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":ronebot-permission"))
            }
        }

        val jvmMain by getting {
            dependencies {
                api(libs.java.websocket)
            }
        }
    }
}