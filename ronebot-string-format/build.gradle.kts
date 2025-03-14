import org.jetbrains.kotlin.gradle.dsl.JvmTarget

dependencies {
//    api(project(":ronebot-common"))
}

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}
