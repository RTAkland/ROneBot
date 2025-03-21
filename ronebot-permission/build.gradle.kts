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
                api(project(":ronebot-common"))
            }
        }
    }
}