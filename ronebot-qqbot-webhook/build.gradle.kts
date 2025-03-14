import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    api(project(":ronebot-common-http"))
    api(libs.ktor.server.core)
    api(libs.ktor.server.netty)
    api(libs.bcprov)
    api(libs.bcpg)
}
