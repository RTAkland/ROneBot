import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    api(project(":ronebot-common"))
    api(project(":ronebot-common-http"))
}