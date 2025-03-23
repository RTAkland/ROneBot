import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    mingwX64()
    linuxX64()

    compilerOptions {
        freeCompilerArgs = listOf("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":ronebot-common"))
            }
        }

        commonTest {
            dependencies {
                implementation(project(":ronebot-onebot-v11"))
                implementation(project(":ronebot-permission"))
            }
        }

        jvmMain {
            dependencies {
                api(libs.java.websocket)
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}