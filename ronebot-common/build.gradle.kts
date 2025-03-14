import org.jetbrains.kotlin.gradle.dsl.JvmTarget

kotlin {
    explicitApi()
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

dependencies {
    api(libs.gson)
    api(libs.kotlinx.coroutines)
    api(libs.kotlin.stdlib)
    api(libs.slf4j.api)
    api(libs.logback.classic)
    api(libs.kotlin.reflect)
    api(libs.brigadier)
}
