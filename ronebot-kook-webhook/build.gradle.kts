import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    api(project(":ronebot-kook-common"))
    api(libs.ktor.server.core)
    api(libs.ktor.server.netty)
}