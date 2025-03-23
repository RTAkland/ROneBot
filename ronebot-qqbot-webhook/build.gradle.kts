import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    withSourcesJar()
    explicitApi()
    jvm {
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }

    sourceSets {
        jvmMain  {
            dependencies {
                api(project(":ronebot-common-http"))
                api(libs.ktor.server.core)
                api(libs.ktor.server.netty)
                api(libs.bcprov)
                api(libs.bcpg)
                api(libs.gson)
            }
        }
    }
}