import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":ronebot-common"))
                api(libs.okhttp)
            }
        }
    }
}