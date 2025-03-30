import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
    mingwX64()
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()

    compilerOptions {
        freeCompilerArgs.apply {
            add("-Xexpect-actual-classes")
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                api(project(":ronebot-utils:ronebot-file"))
                api(project(":ronebot-utils:ronebot-uuid"))
                api(project(":ronebot-utils:ronebot-bytearray"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}