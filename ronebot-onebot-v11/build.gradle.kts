import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {

}

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    api(libs.java.websocket)
    api(project(":ronebot-common"))

    testImplementation(project(":ronebot-permission"))
}