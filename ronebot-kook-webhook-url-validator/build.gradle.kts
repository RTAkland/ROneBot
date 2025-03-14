import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    application
    alias(libs.plugins.shadow)
}

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    implementation(project(":ronebot-kook-common"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
}

application {
    mainClass = "cn.rtast.rob.kook.webhook.validator.MainKt"
}

/**
 * 由于是可执行程序所以不发布到maven仓库中
 */
tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf { false }
}